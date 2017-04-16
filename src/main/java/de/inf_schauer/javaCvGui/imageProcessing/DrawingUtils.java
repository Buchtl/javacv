package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

public class DrawingUtils {

    public static void drawRect(BufferedImage img, Rectangle r, Color color, int thickness) {
        Graphics g = img.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        Stroke old = g2.getStroke();
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(color);
        g2.drawRect(r.x, r.y, r.width, r.height);
        g2.dispose();
        g2.setStroke(old);
        g.dispose();
    }

    /**
     *
     * @param img ...
     * @param r ...
     * @param color ...
     */
    public static void drawRect(BufferedImage img, Rectangle r, Color color) {
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.dispose();
    }

    /**
     *
     * @param img
     * @param r
     * @param color
     */
    public static void drawBar(BufferedImage img, Rectangle r, Color color) {
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        int y = img.getHeight() - r.height;
        g2.fillRect(r.x, y, r.width, r.height);
        g2.dispose();
    }

    /**
     *
     * @param img ...
     * @param offsetBottomLeft ...
     * @param r ...
     * @param color ...
     */
    public static void drawBar(BufferedImage img, Point offsetBottomLeft, Rectangle r, Color color) {
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        int y = (img.getHeight() - r.height) - offsetBottomLeft.y;
        int x = r.x + offsetBottomLeft.x;
        g2.fillRect(x, y, r.width, r.height);
        g2.dispose();
    }

    /**
     *
     * @param img ...
     * @param offsetBottomLeft...
     * @param unitsX...
     * @param unitsY...
     * @param unitSizeX...
     * @param unitSizeY...
     * @param thickness...
     * @param color...
     */

    public static void drawCartesian(
            BufferedImage img,
            Point offsetBottomLeft,
            int unitsX,
            int unitsY,
            int unitSizeX,
            int unitSizeY,
            int thickness,
            Color color) {
        Graphics2D g2 = img.createGraphics();
        int width = unitsX * unitSizeX;
        int height = unitsY * unitSizeY;

        int y_top = (img.getHeight() - height) - offsetBottomLeft.y;
        int y_bottom = y_top + height;
        int x = offsetBottomLeft.x;

        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(color);
        g2.drawLine(x, y_top, x, y_bottom);
        g2.drawLine(x, y_bottom, x + width, y_bottom);

        g2.dispose();
    }

    /**
     *
     * @param bi ...
     * @param color ...
     */
    public static void setColor(BufferedImage bi, Color color) {
        int width = bi.getWidth();
        int height = bi.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bi.setRGB(i, j, color.getRGB());
            }
        }
    }

    /**
     *
     * @param bi ...
     * @param rect ...
     * @param c ...
     */
    public static void fillRect(BufferedImage bi, Rectangle rect, Color c) {
        //System.out.println("Fill for " + rect);
        int width = rect.x + rect.width;
        if ((rect.x + rect.width) > bi.getWidth()) {
            width = bi.getWidth() - rect.x;
        }
        int height = rect.y + rect.height;
        if (height > bi.getHeight()) {
            height = bi.getHeight();
        }

        for (int i = rect.x; i < width; i++) {
            for (int j = rect.y; j < height; j++) {
                bi.setRGB(i, j, c.getRGB());
            }
        }
    }

}
