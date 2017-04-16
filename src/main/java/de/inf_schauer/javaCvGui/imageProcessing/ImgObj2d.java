package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Set;

import de.inf_schauer.javaCvGui.interfaces.I_ImgObj2d;

public class ImgObj2d implements I_ImgObj2d {

    LinkedList<Point> pixels;
    private boolean isNew;
    int minX;
    int minY;
    int maxX;
    int maxY;
    boolean isImageOutdated;
    int lastMargin;
    Color colorImgText;
    Color colorImgBg;
    BufferedImage img;

    public ImgObj2d() {
        isImageOutdated = true;
        pixels = new LinkedList<>();
        isNew = true;
    }

//	public ImgObj2d(int width, int height) {
//		this.field = new boolean[width][height];
//	}
//	public ImgObj2d(Dimension dim) {
//		this.field = new boolean[dim.width][dim.height];
//	}
    @Override
    public Point getCenter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addPoint(Point p) {
        pixels.add(p);
        if (isNew) {
            isNew = false;
            minX = p.x;
            maxX = p.x;
            minY = p.y;
            maxY = p.y;
        }
        if (p.x < minX) {
            minX = p.x;
        }
        if (p.y < minY) {
            minY = p.y;
        }
        if (p.x > maxX) {
            maxX = p.x;
        }
        if (p.y > maxY) {
            maxY = p.y;
        }
        isImageOutdated = true;
    }

    @Override
    public boolean contains(Point p) {
        return pixels.contains(p);
    }

    @Override
    public Dimension getDimension() {
        // +1 because of 0
        int w = Math.abs(maxX - minX) + 1;
        int h = Math.abs(minY - maxY) + 1;
        return new Dimension(w, h);
    }

    @Override
    public int Size() {
        return pixels.size();
    }

    @Override
    public boolean hasNeighbor(Point p) {
//		int xStart = fitX(p.x - 1);
//		int xEnd = fitX(p.x + 1);
//		
//		int yStart = fitY(p.y - 1);
//		int yEnd = fitY(p.y + 1);
//		
//		for (int i = xStart; i < xEnd; i++) {
//			for (int j = yStart; j < yEnd; j++) {
//				if (field[i][j]) {
//					return true;
//				}
//			}
//		}
        return false;
    }

//	private int fitX(int x) {
//		if (x < 0) {
//			return 0;
//		} else if (x >= field[0].length - 1) {
//			return field[0].length;
//		}
//		return x;
//	}
//	private int fitY(int y) {
//		if (y < 0) {
//			return 0;
//		} else if (y >= field.length - 1) {
//			return field.length;
//		}
//		return y;
//	}
    @Override
    public boolean isContiguous(I_ImgObj2d imgObj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<Point> getPoints() {
        // TODO Auto-generated method stub
        return null;
    }

    public static boolean areNeighbors(Point p1, Point p2) {
        int dx = Math.abs(p1.x - p2.x);
        int dy = Math.abs(p1.y - p2.y);

        return dx < 2 && dy < 2;
    }

    public static Dimension getDistanceVector(Point p1, Point p2) {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        return new Dimension(dx, dy);
    }

    public static double getDistance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        double result = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return result;
    }

    @Override
    public void drawObject(BufferedImage bi, Color color) {
        int w = bi.getWidth();
        int h = bi.getHeight();

        for (Point p : pixels) {
            bi.setRGB(p.x, p.y, color.getRGB());
        }
    }

    @Override
    public Rectangle getAreaRect() {
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public int getMinX() {
        return minX;
    }

    @Override
    public int getMaxX() {
        // TODO Auto-generated method stub
        return maxX;
    }

    @Override
    public int getMinY() {
        // TODO Auto-generated method stub
        return minY;
    }

    @Override
    public int getMaxY() {
        // TODO Auto-generated method stub
        return maxY;
    }

    @Override
    public BufferedImage getSimpleImage(Color color, Color bgColor, int margin) {
        if (margin != lastMargin) {
            lastMargin = margin;
            isImageOutdated = true;
        }
        if (pixels.isEmpty()) {
            return null;
        }
        if (this.img == null || isImageOutdated || !color.equals(this.colorImgText) || !(bgColor.equals(colorImgBg))) {
            this.colorImgText = color;
            this.colorImgBg = bgColor;
            Dimension dim = getDimension();
            this.img = new BufferedImage(dim.width + margin * 2, dim.height + margin * 2, BufferedImage.TYPE_3BYTE_BGR);
            ColorUtils.initImage(this.img, bgColor);
            for (Point p : pixels) {
                Point p2 = resetPoint(p);
                this.img.setRGB(p2.x + margin, p2.y + margin, color.getRGB());
            }
            isImageOutdated = false;
        }

        return this.img;
    }

    private Point resetPoint(Point p) {
        return new Point(p.x - minX, p.y - minY);
    }

}
