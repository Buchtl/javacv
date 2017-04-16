package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Point;

public class PointUtils {

    /**
     * Substract p1 by p2
     *
     * @param p1 ...
     * @param p2 ...
     * @return ...
     */
    public static Point sub(Point p1, Point p2) {

        return new Point(p1.x - p2.x, p1.y - p2.y);
    }

}
