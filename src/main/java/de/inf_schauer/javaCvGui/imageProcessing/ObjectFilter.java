package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ObjectFilter {

    /**
     *
     * @param bi (binarized)
     * @param horizontal ...
     * @param min_length ...
     */
    public static void filterLines(BufferedImage bi, boolean horizontal, int min_length) {
        int start = 0;
        int pixelCnt = 0;
        int width = bi.getWidth();
        int height = bi.getHeight();

        int white = ColorUtils.toPlainRgb(Color.WHITE.getRGB());

        if (horizontal) {

        }

        // Y Reihe
        for (int j = 0; j < height; j++) {
            // X reihe
            for (int i = 0; i < width; i++) {
                // System.out.println("Colorz: " + ColorUtils.toPlainRgb(i, j,
                // bi) + " white? " + white);
                if (ColorUtils.toPlainRgb(bi, i, j) != white) {
                    pixelCnt++;
                } else {
                    if (pixelCnt >= min_length) {
                        // print line white
                        start = i - pixelCnt;
                        int detected_length = i - start;
                        for (int k = start; k < i; k++) {
                            bi.setRGB(k, j, Color.WHITE.getRGB());
                        }

                        int threshold = 1;
                        processAdjacentHLines(bi, start, j - 1, "up", detected_length, threshold);
                        processAdjacentHLines(bi, start, j + 1, "down", detected_length, threshold);
                    }
                    pixelCnt = 0;
                }
            }
            pixelCnt = 0;
        }
    }

    /**
     *
     * @param bi ...
     * @param x ...
     * @param y ...
     * @param direction ...
     * @param referred_length ...
     * @param threshold ...
     */
    public static void processAdjacentHLines(BufferedImage bi, int x, int y, String direction, int referred_length,
            int threshold) {
        if (y > 0 && y < bi.getHeight()) {
            int gapCnt = 0;

            int white = ColorUtils.toPlainRgb(Color.WHITE.getRGB());

            for (int i = x; i < referred_length; i++) {
                if (ColorUtils.toPlainRgb(bi, i, y) == white) {
                    gapCnt++;
                }
            }

            if (gapCnt <= threshold) {
                for (int i = x; i < referred_length; i++) {
                    bi.setRGB(i, y, white);
                }
                if (direction.equals("up")) {
                    y--;
                } else {
                    y++;
                }

                processAdjacentHLines(bi, x, y, direction, referred_length, threshold);
            }
        }

    }

    /**
     *
     * @param img ...
     * @param min_length ...
     */
    public static void replaceHLines(BufferedImage img, int min_length) {
        BufferedImage bi = ImProc.createBinarizedAuto(img);

        List<Rectangle> lines = new ArrayList<>();

        int pixelCnt = 0;
        int width = bi.getWidth();
        int height = bi.getHeight();

        int white = ColorUtils.toPlainRgb(Color.WHITE.getRGB());

        // Y Reihe
        for (int j = 0; j < height; j++) {
            // X reihe
            for (int i = 0; i < width; i++) {
                if (ColorUtils.toPlainRgb(bi, i, j) != white) {
                    pixelCnt++;
                } else {
                    if (pixelCnt >= min_length) {
                        int start_x = i - pixelCnt;
                        int detected_length = i - start_x;

                        Rectangle line = new Rectangle(start_x, j, detected_length, 1);

                        int threshold = 1;

                        replaceAdjacentHLines(bi, j - 1, line, "up", threshold);
                        replaceAdjacentHLines(bi, j + 1, line, "down", threshold);

                        lines.add(line);
                    }
                    pixelCnt = 0;
                }
            }
            pixelCnt = 0;
        }

        for (Rectangle tmpRect : lines) {
            DrawingUtils.fillRect(img, tmpRect, Color.WHITE);
        }
    }

    /**
     *
     * @param bi ...
     * @param y ...
     * @param line ...
     * @param direction ...
     * @param threshold ...
     */
    public static void replaceAdjacentHLines(BufferedImage bi, int y, Rectangle line, String direction, int threshold) {
        if ((y > 0) && (y < bi.getHeight())) {
            int blackPixelCnt = 0;

            //int white = ColorUtils.toPlainRgb(Color.WHITE.getRGB());
            for (int i = line.x; i < line.width; i++) {
                if (ColorUtils.toPlainRgb(bi, i, y) == ColorUtils.toPlainRgb(Color.BLACK.getRGB())) {
                    blackPixelCnt++;
                }
            }

            if (blackPixelCnt >= (line.width - threshold)) {
                if (direction.equals("up")) {
                    line.setBounds(line.x, y, line.width, line.height + 1);
                    replaceAdjacentHLines(bi, y - 1, line, direction, threshold);
                } else {
                    line.setBounds(line.x, line.y, line.width, line.height + 1);
                    replaceAdjacentHLines(bi, y + 1, line, direction, threshold);
                }
            }
        }

    }

}
