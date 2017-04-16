package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Point;
import java.util.List;

public class MathOps {

    /**
     *
     * @param points ...
     * @return ...
     */
    public static double sumX(List<Point> points) {
        double result = 0;

        for (Point p : points) {
            result += p.getX();
        }

        return result;
    }

    /**
     *
     * @param points ...
     * @return ...
     */
    public static double sumY(List<Point> points) {
        double result = 0;

        for (Point p : points) {
            result += p.getY();
        }

        return result;
    }

    /**
     *
     * @param points ...
     * @return ...
     */
    public static double sumXY(List<Point> points) {
        double result = 0;

        for (Point p : points) {
            result += p.getX() * p.getY();
        }

        return result;
    }

    /**
     *
     * @param points ...
     * @return ...
     */
    public static double sumXX(List<Point> points) {
        double result = 0;

        for (Point p : points) {
            result += p.getX() * p.getX();
        }

        return result;
    }

    /**
     *
     * @param points ...
     * @return ...
     */
    public static Line<Double> regLineDouble(List<Point> points) {
        double sumX = MathOps.sumX(points);
        double sumXX = MathOps.sumXX(points);
        double sumY = MathOps.sumY(points);
        double sumXY = MathOps.sumXY(points);
        double n = points.size();

        // regression line
        double a = (sumXY - sumX * sumY / n) / (sumXX - sumX * sumX / n);
        double t = (sumY - a * sumX) / n;

        return new Line<>(a, t, new CalcOpsDouble());
    }

    /**
     *
     * @param adjacent ...
     * @param opposite ...
     * @return ...
     */
    public static double calcAngleRad(double opposite, double adjacent) {
        return Math.atan(opposite / adjacent);
    }

}
