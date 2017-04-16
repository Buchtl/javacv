package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Rectangle;

public class Conversions {

    public static final int INCH_IN_MILLIMETERS = 254;
    public static final int POINT_DIVISOR = 72;

    /**
     *
     * @param dpi ...
     * @param rect ...
     */
    public static void convertPointsRectToPixel(int dpi, Rectangle rect) {
        int x = pointToPixel(dpi, rect.x);
        int y = pointToPixel(dpi, rect.y);
        int w = pointToPixel(dpi, rect.width);
        int h = pointToPixel(dpi, rect.width);
        rect.setBounds(x, y, w, h);
    }

    /**
     *
     * @param ppi ...
     * @param mm ...
     * @return
     */
    public static int ppiToPixel(int ppi, int mm) {
        return ppi * mm / INCH_IN_MILLIMETERS;
    }

    /**
     *
     * @param ppi
     * @param pts
     * @return
     */
    public static int pointToPixel(int ppi, int pts) {
        int mm = pointsToMM(pts);
        return ppiToPixel(ppi, mm);
    }

    /**
     *
     * @param inch
     * @return
     */
    public static int inchToMM(int inch) {
        return inch * INCH_IN_MILLIMETERS;
    }

    /**
     *
     * @param pts ...
     * @return ...
     */
    public static float pointsToInch(float pts) {
        return pts / POINT_DIVISOR;
    }

    /**
     *
     * @param pts ...
     * @return ...
     */
    public static int pointsToMM(int pts) {
        return pts * INCH_IN_MILLIMETERS / POINT_DIVISOR;
    }

}
