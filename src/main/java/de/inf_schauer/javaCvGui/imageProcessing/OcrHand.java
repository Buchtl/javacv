/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.inf_schauer.javaCvGui.data.CharLine;
import de.inf_schauer.javaCvGui.interfaces.I_ImgObj2d;
import de.inf_schauer.javaCvGui.interfaces.I_Ocr;
import java.io.File;
import java.util.ArrayList;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OcrHand implements I_Ocr {

    private static final Logger LOG = Logger.getLogger(OcrHand.class.getName());

    private int distance;
    Tesseract tess;

    public OcrHand(int distance) {
        this.distance = distance;
        this.tess = new Tesseract();
        this.tess.setTessVariable("tessedit_char_whitelist", "qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM");
        // tess.setLanguage("eng2");
        // tess.setLanguage(this.tf_tess.getText());
    }

    @Override
    public String detect(BufferedImage bi) {
        StringBuilder result = new StringBuilder();

        CharLine line = new CharLine();
        // bi = ImProc.smooth(bi, 3);
        // bi = ImProc.smooth(bi, 3);
        // bi = ImProc.binarize(bi, 40, 90);
        List<Point> start = ImProc.getFirstPointsFromLeft(bi);

        while (!start.isEmpty()) {
            // console.append("dB: new Start: " + start.get(0).toString());
            I_ImgObj2d element = GraphProc.breadthFirst(bi, start.get(0), getDistance());
            line.add(element);
            element.drawObject(bi, Color.WHITE);
            start = ImProc.getFirstPointsFromLeft(bi);
        }

        List<List<I_ImgObj2d>> words = line.getWords();

        for (List<I_ImgObj2d> word : words) {
            if (result.length() > 0) {
                result.append(";");
            }
            int i = 0;
            //String[] letters = new String[word.size()];
            int[] letter = new int[word.size()];
            for (I_ImgObj2d l : word) {
                Rectangle r = l.getAreaRect();
                //letters[i] = doOcr(bi.getSubimage(r.x - 2, r.y - 2, r.width + 2, r.height + 2));
                //letters[i] = doOcr(letter.getSimpleImage(Color.BLACK, Color.WHITE, 4));
                //ImUtils.writeImage(bi.getSubimage(r.x - 2, r.y - 2, r.width + 2, r.height + 2), "tmp/000-" + Math.random() + ".png");
                //LOG.log(Level.INFO, "###### detected: {0} : {1}; RECT {2}", new Object[] {i, letters[i], RectUtils.getString(r)});

                double random = Math.random();
                BufferedImage edgeImg = ImProc.smooth(l.getSimpleImage(Color.BLACK, Color.WHITE, 4), 3);
                edgeImg = ImProc.binarize(edgeImg, 40, 90);
//                ImUtils.writeImage(edgeImg, "tmp" + File.separator + i + "_-Special-_" + random + ".png");
                edgeImg = ImProc.extractEdges(edgeImg, 30);

                List<I_ImgObj2d> tmpLetterParts = new ArrayList<>();

                List<Point> startL = ImProc.getFirstPointsFromLeft(edgeImg);
                while (!startL.isEmpty()) {
                    // console.append("dB: new Start: " + start.get(0).toString());
                    I_ImgObj2d element = GraphProc.breadthFirst(edgeImg, startL.get(0), getDistance());
                    tmpLetterParts.add(element);
                    element.drawObject(edgeImg, Color.WHITE);
                    startL = ImProc.getFirstPointsFromLeft(edgeImg);
                }
                letter[i] = tmpLetterParts.size();
                LOG.log(Level.INFO, "letter {0} has {1} elements", new Object[]{i, tmpLetterParts.size()});
                for (I_ImgObj2d ttt : tmpLetterParts) {
                    ImUtils.writeImage(ttt.getSimpleImage(Color.BLACK, Color.WHITE, 0), "tmp" + File.separator + i + "___" + random + ".png");
                    Boolean isM = isM(words.get(0).get(0));
                    LOG.info("##### ism: " + isM);
                }
                i++;
            }
            boolean isOpa;
            boolean isOnkel;

            switch (word.size()) {
                case 3:
//				boolean secondM = letters[1].equals("m")|| letters[1].equals("M");
//				boolean secondH = letters[1].equals("H");
//				boolean secondW = letters[1].equals("w")|| letters[1].equals("W");
//				if (secondM || secondH || secondW) {
//					result.append("Oma");
//				} else {
//					result.append("Opa");
//				}
                    if (2 == letter[0] && letter[1] == 2) {
                        result.append("Opa");
                    } else {
                        result.append("Oma");
                    }
                    break;
                case 5:
                    //boolean firstO = letters[0].equals("o") || letters[0].equals("O");
                    isOnkel = letter[0] == 2;
                    if (isOnkel) {
                        result.append("Onkel");
                    } else {
                        result.append("Tante");
                    }
                    break;
                case 6:
                    result.append("Bruder");
                    break;
                case 9:
                    isOpa = letter[2] == 2;
                    if (isOpa) {
                        result.append("Opa");
                    } else {
                        result.append("Schwester");
                    }
//                    boolean firstG = letters[0].equals("g") || letters[0].equals("G");
//                    boolean thirdO = letters[2].equals("o") || letters[2].equals("O") || letters[2].equals("0");
//                    boolean fifthV = letters[4].equals("v") || letters[4].equals("V");
//                    boolean fifthM = letters[4].equals("m") || letters[4].equals("M");
//                    boolean fifthH = letters[4].equals("H");
//                    boolean fifthW = letters[4].equals("w") || letters[4].equals("W");
//                    if (firstG || thirdO) {
//                        if (fifthV) {
//                            result.append("Opa");
//                        } else if (fifthM || fifthH || fifthW) {
//                            result.append("Oma");
//                        } else {// if (fourthM || fourthH || fourthW) {
//                            result.append("Opa");
//                        }
//                    } else {
//                        result.append("Schwester");
//                    }
                    break;
                case 10:
                    //GROßMUTTER
                    isOpa = (letter[3] == 1 || letter[6] == 2) && !isM(word.get(4));
                    if (isOpa) {
                        result.append("Opa");
                    } else {
                        result.append("Oma");
                    }
                    break;
                case 11:
                    result.append("Oma");
                    break;
                default:
                    break;
            }

        }

        return result.toString();

    }

    public boolean isM(I_ImgObj2d letter) {
        BufferedImage bi = letter.getSimpleImage(Color.black, Color.WHITE, 0);
        int w = bi.getWidth();
        int h = bi.getHeight();
        int clearTopW = w / 8;
        int topMargin = h / 8;
        int xTopStart = w / 2 - clearTopW / 2;
        int xTopEnd = xTopStart + clearTopW;
        //ImUtils.writeImage(bi, "tmp" + File.separator + "m.png");

        for (int y = 0; y < topMargin; y++) {
            for (int x = xTopStart; x < xTopEnd; x++) {
                if (!ColorUtils.isWhite(bi.getRGB(x, y))) {
                    return false;
                }
            }
        }
//        int blackWhiteTransitions = 0;
//        for (int y = xBottomEnd - bottomMargin; y < xBottomEnd; y++) {
//            for (int x = 0; x < w; x++) {
//                if (!ColorUtils.isWhite(bi.getRGB(x, y))) {
//                    if (x < (w - 1)) {
//                        if (ColorUtils.isWhite(bi.getRGB(x + 1, y))) {
//                            blackWhiteTransitions++;
//                        }
//                    }
//                }
//            }
//        }
//        boolean result = blackWhiteTransitions > 1;
        BufferedImage biTop = letter.getSimpleImage(Color.black, Color.WHITE, 1).getSubimage(0, 0, w, h - h / 3);
        biTop = ImProc.extractEdges(biTop, 30);
        BufferedImage tmpI = ImUtils.copyImage(biTop);
        List<I_ImgObj2d> parts = dissectimage(biTop);
        ImUtils.writeImage(tmpI, "tmp" + File.separator + "biTop.png");
        ImUtils.writeImage(bi, "tmp" + File.separator + "biTop2.png");
        boolean result = parts.size() > 1;
        return result;
    }

    private List<I_ImgObj2d> dissectimage(BufferedImage bi) {
        List<I_ImgObj2d> parts = new ArrayList<>();
        List<Point> startL = ImProc.getFirstPointsFromLeft(bi);
        while (!startL.isEmpty()) {
            // console.append("dB: new Start: " + start.get(0).toString());
            I_ImgObj2d element = GraphProc.breadthFirst(bi, startL.get(0), getDistance());
            parts.add(element);
            element.drawObject(bi, Color.WHITE);
            startL = ImProc.getFirstPointsFromLeft(bi);
        }
        return parts;
    }

    @Override
    public String detectNumber(BufferedImage bi) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//	/**
//	 * 
//	 * @param input
//	 * @param word
//	 * @param pos
//	 * @return
//	 */
//	private boolean imageWordEquals(String input, List<I_ImgObj2d> word, int pos) {
//		String l = "";
//		try {
//			l = tess.doOCR(word.get(pos).getSimpleImage(Color.BLACK, Color.WHITE, 4)).trim();
//		} catch (TesseractException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		LOG.log(Level.INFO, "###### detected: {0} ?= input {1}, pos {2}", new Object[] { l, input, pos });
//		return input.equals(l);
//	}
    private String doOcr(BufferedImage bi) {
        String l = "";
        try {
            l = tess.doOCR(bi).trim();
        } catch (TesseractException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return l;
    }

    /**
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

}
