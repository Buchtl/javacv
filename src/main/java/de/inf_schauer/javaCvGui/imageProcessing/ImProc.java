package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImProc {

    public static final String MINX = "minX";
    public static final String MAXX = "maxX";
    public static final String MINY = "minY";
    public static final String MAXY = "maxY";

    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    public static int HORIZONTAL = 1;
    public static int VERTICAL = -1;
    public static int SQUARED_ANGLE = 0;

    /**
     *
     * @param bi ...
     * @param minS ...
     * @param maxV ...
     * @return ...
     */
    public static BufferedImage binarize(BufferedImage bi, int minS, int maxV) {
        BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {

                float[] hsb = ColorUtils.rgbToHsb(bi.getRGB(j, i));
                // float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                float sat = saturation * 100;
                float v = brightness * 100;

                if ((sat > minS) || (v < maxV)) {
                    result.setRGB(j, i, Color.BLACK.getRGB());
                } else {
                    result.setRGB(j, i, Color.WHITE.getRGB());
                }
            }
        }

        return result;
    }

    /**
     *
     * @param bi ...
     * @param minS ...
     * @param maxV ...
     * @return ...
     */
    public static BufferedImage binarize2(BufferedImage bi, int minS, int maxV) {
        BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {

                float[] hsb = ColorUtils.rgbToHsb(bi.getRGB(j, i));
                // float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                float sat = saturation * 100;
                float v = brightness * 100;

                if ((sat >= minS) && (v <= maxV)) {
                    result.setRGB(j, i, Color.BLACK.getRGB());
                } else {
                    result.setRGB(j, i, Color.WHITE.getRGB());
                }
            }
        }

        return result;
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static BufferedImage createBinarizedAuto(BufferedImage bi) {
        BufferedImage output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());

        int avg = ColorUtils.getAverageRGB(bi);

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int avg_local = ColorUtils.getAverageRGB(bi.getRGB(j, i));

                if (avg_local >= avg) {
                    output.setRGB(j, i, Color.WHITE.getRGB());
                } else {
                    output.setRGB(j, i, Color.BLACK.getRGB());
                }
            }
        }
        return output;
    }

    /**
     *
     * @param bi ...
     */
    public static void binarizeAuto(BufferedImage bi) {
        int avg = ColorUtils.getAverageRGB(bi);

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                int avg_local = ColorUtils.getAverageRGB(bi.getRGB(j, i));

                if (avg_local >= avg) {
                    bi.setRGB(j, i, Color.WHITE.getRGB());
                } else {
                    bi.setRGB(j, i, Color.BLACK.getRGB());
                }
            }
        }
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static int countBlackPixel(BufferedImage bi) {
        return countBlackPixel(bi, null);
    }

    /**
     *
     * @param bi ...
     * @param area ...
     * @return ...
     */
    public static int countBlackPixel(BufferedImage bi, Rectangle area) {
        int result = 0;
        int start_x = 0;
        int start_y = 0;
        int end_x = bi.getWidth();
        int end_y = bi.getHeight();

        if (area != null) {
            start_x = area.x;
            start_y = area.y;
            end_x = start_x + area.width;
            end_y = start_y + area.height;
        }

        for (int i = start_x; i < end_x; i++) {
            for (int j = start_y; j < end_y; j++) {
                if (ColorUtils.toPlainRgb(bi, i, j) == ColorUtils.toPlainRgb(Color.BLACK.getRGB())) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     *
     * @param bi ...
     * @param dim ...
     * @return ...
     */
    public static BufferedImage smooth(BufferedImage bi, int dim) {
        BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);

        int x_begin = dim / 2;
        int x_end = bi.getWidth() - dim / 2;
        int y_begin = dim / 2;
        int y_end = bi.getHeight() - dim / 2;

        int b = dim / 2;
        int h = dim / 2;
        int b_left = bi.getWidth() - x_begin;
        int h_bottom = bi.getHeight() - y_begin;

        for (int i = 0; i < b; i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                result.setRGB(i, j, bi.getRGB(i, j));
            }
        }

        for (int i = b_left; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                result.setRGB(i, j, bi.getRGB(i, j));
            }
        }

        for (int j = 0; j < h; j++) {
            for (int i = 0; i < bi.getWidth(); i++) {
                result.setRGB(i, j, bi.getRGB(i, j));
            }
        }

        for (int j = h_bottom; j < bi.getHeight(); j++) {
            for (int i = 0; i < bi.getWidth(); i++) {
                result.setRGB(i, j, bi.getRGB(i, j));
            }
        }

        for (int i = x_begin; i < x_end; i++) {
            for (int j = y_begin; j < y_end; j++) {
                result.setRGB(i, j, ArealOps.getAverage(i, j, dim, bi));
            }
        }

        return result;
    }

    /**
     *
     * @param bi ...
     * @param subRegion ...
     * @param soll ...
     * @return  ...
     */
    public static BufferedImage align(BufferedImage bi, Rectangle subRegion, Point soll) {
        BufferedImage subImg = binarize(bi.getSubimage(subRegion.x, subRegion.y, subRegion.width, subRegion.height), 40,
                90);
        Point tl = getTopLeft(subImg, 0);
        Point translation = PointUtils.sub(soll, tl);
        AffineTransform at = new AffineTransform();
        at.translate(translation.getX(), translation.getY());
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        return op.filter(bi, null);
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static double alignmentRadian(BufferedImage bi) {
        BufferedImage tmpImg = ImUtils.copyImage(bi);

        Point tl = ImProc.getTopLeft(tmpImg, 0);

        Point tr = ImProc.getTopRight(tmpImg, 0);

        Point vec = PointUtils.sub(tr, tl);
        double xv = vec.x;
        double yv = vec.y;
        double hyp = Math.sqrt(xv * xv + yv * yv);
        double radian = Math.asin(vec.x / hyp) - Math.PI / 2;

        if (tl.y > tr.y) {
            radian *= -1;
        }
        return radian;
    }

    /**
     *
     * @param bi ...
     * @param threshold ...
     * @return ...
     */
    public static int getAlignment(BufferedImage bi, int threshold) {
        Map<String, Point> points = getMinMaxXY(bi);

        int width = points.get(MAXX).x - points.get(MINX).x;
        int height = points.get(MAXY).y - points.get(MINY).y;

        if (Math.abs((width - height)) < threshold) {
            return SQUARED_ANGLE;
        } else if (width > height + threshold) {
            return HORIZONTAL;
        } else {
            return VERTICAL;
        }
    }

    /**
     *
     * @param bi ...
     * @param offsetTop ...
     * @param offsetBottom ...
     * @return ...
     */
    public static Rectangle extractId(BufferedImage bi, int offsetTop, int offsetBottom) {
        Point pStart = getMaxY(bi);
        for (int y = pStart.y - 1; y > 0; y--) {
            boolean isLineBlank = true;
            for (int x = 0; x < bi.getWidth(); x++) {
                if (bi.getRGB(x, y) == Color.BLACK.getRGB()) {
                    isLineBlank = false;
                    break;
                }
            }

            if (isLineBlank) {
                return new Rectangle(0, y - offsetTop, bi.getWidth(), pStart.y - y + offsetBottom + offsetTop);
            }
        }

        return null;
    }

    /**
     *
     * @param bi ...
     * @return  ...
     */
    public static BufferedImage trimRegion(BufferedImage bi) {
        Map<String, Point> xtPoints = getMinMaxXY(bi);

        Point pTop = xtPoints.get(MINY);
        Point pLeft = xtPoints.get(MINX);
        Point pRight = xtPoints.get(MAXX);
        Point pBottom = xtPoints.get(MAXY);
        int width = pRight.x - pLeft.x + 1;
        int height = pBottom.y - pTop.y + 1;
        //System.out.println("get: " + pLeft.x + " " + pTop.y + " " + width + " " + height);
        return bi.getSubimage(pLeft.x, pTop.y, width, height);
    }

    /**
     *
     * @param bi ...
     * @param side ...
     * @return ...
     */
    public static BufferedImage getSide(BufferedImage bi, String side) {
        switch (side) {
            case TOP:
                return bi.getSubimage(0, 0, bi.getWidth(), bi.getHeight() / 2);
            case BOTTOM:
                return bi.getSubimage(0, bi.getHeight() / 2, bi.getWidth(), bi.getHeight() / 2);
            case LEFT:
                return bi.getSubimage(0, 0, bi.getWidth() / 2, bi.getHeight());
            case RIGHT:
                return bi.getSubimage(bi.getWidth() / 2, 0, bi.getWidth() / 2, bi.getHeight());
            default:
                return bi;

        }

    }

    /**
     *
     * @param bi ...
     * @param threshold unclean edges?
     * @return ...
     */
    public static Point getTopLeft(BufferedImage bi, int threshold) {
        Map<String, Point> edgePoints = getMinMaxXY(bi);
        Point minX = edgePoints.get(MINX);
        Point maxX = edgePoints.get(MAXX);
        Point minY = edgePoints.get(MINY);
        // Point maxY = edgePoints.get("maxY");

        if ((minY.x - minX.x) <= ((maxX.x - minX.x) / 2)) {// minY.x < maxX.x &&
            // minY.x >= minX.x
            System.out.println(MINY);
            return minY;
        } else {
            System.out.println(MINX);
            return minX;
        }
    }

    /**
     *
     * @param bi ...
     * @param threshold unclean edges?
     * @return ...
     */
    public static Point getTopRight(BufferedImage bi, int threshold) {
        Map<String, Point> edgePoints = getMinMaxXY(bi);
        Point minX = edgePoints.get(MINX);
        Point maxX = edgePoints.get(MAXX);
        Point minY = edgePoints.get(MINY);
        // Point maxY = edgePoints.get("maxY");

        if ((minY.x - minX.x) <= ((maxX.x - minX.x) / 2)) {// minY.x < maxX.x &&
            // minY.x >= minX.x
            return maxX;
        } else {
            return minY;
        }
    }

    /**
     *
     * @param img ...
     * @param radian ...
     * @param right ...
     * @return  ...
     */
    public static BufferedImage rotate(BufferedImage img, double radian, boolean right) {
        if (!right) {
            radian *= -1;
        }
        System.out.println("rotate right = " + right);
        AffineTransform tx = new AffineTransform();
        tx.rotate(radian, img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return normalizeImg(op.filter(img, null));
        // return normalizeImg(op.filter(img, null));
    }

    private static BufferedImage normalizeImg(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) output.getGraphics();
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, output.getWidth(), output.getHeight());

        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {
                if (ColorUtils.hasAlpha(input.getRGB(j, i))) {
                    output.setRGB(j, i, Color.WHITE.getRGB());
                } else {
                    output.setRGB(j, i, input.getRGB(j, i));
                }

            }
        }

        return output;
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static Map<String, Point> getMinMaxXY(BufferedImage bi) {
        Map<String, Point> result = new HashMap<>();
        Point minX = null;
        Point maxX = new Point(0, 0);
        Point minY = null;
        Point maxY = new Point(0, 0);

        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                if (bi.getRGB(x, y) == Color.BLACK.getRGB()) {
                    // First Pixel at all
                    if (minX == null) {
                        minX = new Point(x, y);
                    }
                    if (minY == null) {
                        minY = new Point(x, y);
                    }
                    if (x < minX.x) {
                        minX.setLocation(x, y);
                    }
                    if (x > maxX.x) {
                        maxX.setLocation(x, y);
                    }
                    if (y > maxY.y) {
                        maxY.setLocation(x, y);
                    }
                }
            }
        }
        result.put(MINX, minX);
        result.put(MAXX, maxX);
        result.put(MINY, minY);
        result.put(MAXY, maxY);
        return result;
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static Point getMaxY(BufferedImage bi) {
        for (int y = bi.getHeight() - 1; y > 0; y--) {
            for (int x = 0; x < bi.getWidth(); x++) {
                if (bi.getRGB(x, y) == Color.BLACK.getRGB()) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    /**
     *
     * @param bi ...
     */
    public static void firstPointsFromLeft(BufferedImage bi) {
        List<Point> points;
        points = getFirstPointsFromLeft(bi);
        List<Point> filteredPoints = filterPointsByMeanX(points, 20);

        for (Point p : filteredPoints) {
            bi.setRGB(p.x, p.y, Color.BLACK.getRGB());
        }
    }

    /**
     * return true if correct alligned
     *
     * @param bi ...
     * @return ...
     */
    public static boolean upturnNeeded(BufferedImage bi) {

        filterPixels(bi, new Dimension(15, 15), 6);

        List<Point> points;
        points = getFirstPointsFromLeft(bi);
        BufferedImage output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        DrawingUtils.setColor(output, Color.WHITE);

        List<Point> filteredPoints = filterPointsByMeanX(points, 20);

        // regression line
        Line<Double> line = MathOps.regLineDouble(filteredPoints);

        for (Point p : filteredPoints) {
            output.setRGB(p.x, p.y, Color.BLACK.getRGB());
        }

        int yNext = 0;
        for (int j = 0; j < output.getWidth(); j++) {
            Double yd = line.getY((double) j);
            int x = j;
            int y = yd.intValue();

            if (y < output.getHeight() && y >= 0 && x >= 0 && x < output.getWidth()) {
                if (yNext < 0) {
                    yNext = 0;
                }
                if (yNext > output.getHeight()) {
                    yNext = output.getHeight();
                }

                if (yNext < y) {
                    for (int k = yNext; k < y; k++) {
                        output.setRGB(x, k, Color.RED.getRGB());
                    }
                } else {
                    for (int k = y; k < yNext; k++) {
                        output.setRGB(x, k, Color.RED.getRGB());
                    }
                }
            }
            yNext = y;
        }

        try {
            ImageIO.write(output, "png", new File("upturnNeeded.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return line.getSlope() < 0;
        // return output;
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static boolean isHorizontal(BufferedImage bi) {
        List<Point> points = getFirstPointsFromLeft(bi);
        points = filterPointsByMeanX(points, 20);

        BufferedImage output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        DrawingUtils.setColor(output, Color.WHITE);
        for (Point p : points) {
            output.setRGB(p.x, p.y, Color.BLACK.getRGB());
        }
        filterPixels(output, new Dimension(3, 20), 17);
        points = getFirstPointsFromLeft(output);

        Line<Double> line = MathOps.regLineDouble(points);
        int yNext = 0;
        for (int j = 0; j < output.getWidth(); j++) {
            Double yd = line.getY((double) j);
            int x = j;
            int y = yd.intValue();

            if (y < output.getHeight() && y >= 0 && x >= 0 && x < output.getWidth()) {
                if (yNext < 0) {
                    yNext = 0;
                }
                if (yNext > output.getHeight()) {
                    yNext = output.getHeight();
                }

                if (yNext < y) {
                    for (int k = yNext; k < y; k++) {
                        output.setRGB(x, k, Color.RED.getRGB());
                    }
                } else {
                    for (int k = y; k < yNext; k++) {
                        output.setRGB(x, k, Color.RED.getRGB());
                    }
                }
            }
            yNext = y;
        }

        try {

            ImageIO.write(output, "png", new File("isHorizontal.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Line<Double> line = MathOps.calcDoubleLine(points);
        return points.size() > 0;
    }

    /**
     *
     * @param bi ...
     */
    public static void drawRegLine(BufferedImage bi) {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                if (bi.getRGB(j, i) == Color.BLACK.getRGB()) {
                    points.add(new Point(j, i));
                }
            }
        }

        // points = filterPointsByMeanX(points,20);
        for (Point p : points) {
            bi.setRGB(p.x, p.y, Color.BLACK.getRGB());
        }
        // filterPixels(bi, new Dimension(3,20), 17);

        Line<Double> line = MathOps.regLineDouble(points);

        int yOld = 0;

        for (int j = 0; j < bi.getWidth(); j++) {
            Double yd = line.getY((double) j);
            int x = j;
            int y = yd.intValue();

            if (y < bi.getHeight() && y >= 0 && x >= 0 && x < bi.getWidth()) {
                if (yOld < 0) {
                    yOld = 0;
                }
                if (yOld > bi.getHeight()) {
                    yOld = bi.getHeight();
                }

                bi.setRGB(x, y, Color.RED.getRGB());

                if (x > 0) {
                    if (yOld < y) {
                        for (int k = yOld; k < y; k++) {
                            bi.setRGB(x, k, Color.RED.getRGB());
                        }
                    } else {
                        for (int k = y; k < yOld; k++) {
                            bi.setRGB(x, k, Color.RED.getRGB());
                        }
                    }
                }
            }
            yOld = y;
        }
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static double getRotation(BufferedImage bi) {
        List<Point> points = getFirstPointsFromLeft(bi);
        // points = filterPointsByMeanX(points,20);

        BufferedImage output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        DrawingUtils.setColor(output, Color.WHITE);

        for (Point p : points) {
            output.setRGB(p.x, p.y, Color.BLACK.getRGB());
        }

        filterPixels(output, new Dimension(3, 20), 17);

        points = getFirstPointsFromLeft(output);

        Line<Double> line = MathOps.regLineDouble(points);

        try {
            ImageIO.write(output, "png", new File("rotation.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (points.size() > 0) {
            if (line.getSlope() > 0) {
                // rotate left
                System.out.println("##### RotateLeft");
                return (Math.atan(line.getSlope()) + Math.PI / 2) * -1;// 4
            } else {
                return Math.atan(line.getSlope()) + Math.PI / 2;
            }
        } else {
            return 0;
        }
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static double alignmentTop(BufferedImage bi) {
        BufferedImage img = null;

        img = ImProc.binarize(bi, 40, 90);

        Point tl = ImProc.getTopLeft(img, 0);

        Point tr = ImProc.getTopRight(img, 0);

        Point vec = PointUtils.sub(tr, tl);
        double xv = vec.x;
        double yv = vec.y;
        double hyp = Math.sqrt(xv * xv + yv * yv);
        double radian = Math.asin(vec.x / hyp) - Math.PI / 2;

        if (tl.y < tr.y) {
            return radian;
        } else {
            return -radian;
        }
    }

    /**
     *
     * @param points ...
     * @param threshold ...
     * @return ...
     */
    public static List<Point> filterPointsByMeanX(List<Point> points, double threshold) {
        List<Point> result = new ArrayList<>();
        double sumX = MathOps.sumX(points);
        double mean = sumX / points.size();

        for (Point p : points) {
            if (Math.abs(mean - p.x) < 20) {
                result.add(p);
            }
        }
        return result;
    }

    public static List<Point> getFirstPointsFromLeft(BufferedImage bi) {
        return getFirstPointsFromLeft(bi, 0);
    }

    public static List<Point> getFirstPointsFromLeft(BufferedImage bi, int offset) {
        List<Point> points = new ArrayList<>();

        if (offset < 0) {
            offset = 0;
        }
        if (offset >= bi.getWidth()) {
            return null;
        }

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = offset; j < bi.getWidth(); j++) {
                if (bi.getRGB(j, i) == Color.BLACK.getRGB()) {
                    points.add(new Point(j, i));
                    break;
                }
            }
        }
        return points;
    }

    public static List<Point> getFirstPointsFromLeftTopDown(BufferedImage bi) {
        return getFirstPointsFromLeftTopDown(bi, 0);
    }

    public static List<Point> getFirstPointsFromLeftTopDown(BufferedImage bi, int offset) {
        List<Point> points = new ArrayList<>();

        if (offset < 0) {
            offset = 0;
        }
        if (offset >= bi.getWidth()) {
            return null;
        }

        for (int j = offset; j < bi.getWidth(); j++) {
            for (int i = 0; i < bi.getHeight(); i++) {
                if (bi.getRGB(j, i) == Color.BLACK.getRGB()) {
                    points.add(new Point(j, i));
                    break;
                }
            }
        }
        return points;
    }

    /**
     *
     * @param bi ...
     * @param r ...
     * @param threshold ...
     */
    private static void filterPixels(BufferedImage bi, Dimension dim, int threshold) {
        int w = dim.width;
        int h = dim.height;
        int ow = w / 2;
        int oh = h / 2;

        for (int i = oh; i < bi.getHeight() - oh; i++) {
            for (int j = ow; j < bi.getWidth() - ow; j++) {
                if (bi.getRGB(j, i) == Color.BLACK.getRGB()) {
                    if (!NeighborhoodOps.checkArea(bi, new Rectangle(j - ow, i - ow, w, h), threshold)) {
                        bi.setRGB(j, i, Color.WHITE.getRGB());
                    }
                }
            }
        }
    }

    /**
     * If negative is true, everything in range will be set white. If negative is false, everything outside the range will be set white.
     *
     * @param bi ...
     * @param h_min ...
     * @param s_min ...
     * @param v_min ...
     * @param h_max ...
     * @param s_max ...
     * @param v_max ...
     * @param negative ...
     */
    public static void filterHSV(BufferedImage bi, int h_min, int s_min, int v_min, int h_max, int s_max, int v_max,
            boolean negative) {

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {

                int orig_rgb = bi.getRGB(j, i);

                float[] hsb = ColorUtils.rgbToHsb(orig_rgb);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                float h1 = 0;
                float h2 = 0;
                float s1 = 0;
                float s2 = 0;
                float v1 = 0;
                float v2 = 0;

                h1 = ((float) h_min / 255);
                s1 = (float) s_min / 255;
                v1 = (float) v_min / 255;
                h2 = ((float) h_max / 255);
                s2 = (float) s_max / 255;
                v2 = (float) v_max / 255;

                boolean inH = ((hue >= h1) && (hue <= h2));
                boolean inS = ((saturation >= s1) && (saturation <= s2));
                boolean inV = ((brightness >= v1) && (brightness <= v2));

                if (negative) {
                    if (inH && inS && inV) {
                        bi.setRGB(j, i, 0xFFFFFF);
                    } else {
                        bi.setRGB(j, i, orig_rgb);
                    }
                } else {
                    if (inH && inS && inV) {
                        bi.setRGB(j, i, orig_rgb);
                    } else {
                        bi.setRGB(j, i, 0xFFFFFF);
                    }
                }
            }
        }
    }

    /**
     * If negative is true, everything in range will be set white. If negative is false, everything outside the range will be set white.
     *
     * @param bi ...
     * @param h_min ...
     * @param s_min ...
     * @param v_min ...
     * @param h_max ...
     * @param s_max ...
     * @param v_max ...
     * @param remove ...
     */
    public static void filterHSVColor(BufferedImage bi, int h_min, int s_min, int v_min, int h_max, int s_max,
            int v_max, boolean remove) {

        float h1 = 0;
        float h2 = 0;
        float s1 = 0;
        float s2 = 0;
        float v1 = 0;
        float v2 = 0;

        h1 = ((float) h_min / 255);
        s1 = (float) s_min / 255;
        v1 = (float) v_min / 255;
        h2 = ((float) h_max / 255);
        s2 = (float) s_max / 255;
        v2 = (float) v_max / 255;

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {

                int orig_rgb = bi.getRGB(j, i);

                float[] hsb = ColorUtils.rgbToHsb(orig_rgb);
                float hue = hsb[0];
                float saturation = hsb[1];
                float brightness = hsb[2];

                boolean inH = ((hue >= h1) && (hue <= h2));
                boolean inS = saturation >= s1;
                boolean inV = brightness >= v1;

                if (remove) {
                    if (!inH && inS && inV) {
                        bi.setRGB(j, i, Color.WHITE.getRGB());
                    } else {
                        bi.setRGB(j, i, orig_rgb);
                    }
                } else {
                    {
                        if (!inH && inS && inV) {
                            bi.setRGB(j, i, orig_rgb);
                        } else {
                            bi.setRGB(j, i, Color.WHITE.getRGB());
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static Point calcCenterOfMass(BufferedImage bi) {
        int sumX = 0;
        int sumY = 0;
        int pixelCnt = 0;

        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                if (bi.getRGB(j, i) == Color.BLACK.getRGB()) {
                    sumX += j;
                    sumY += i;
                    pixelCnt++;
                }
            }
        }

        return new Point(sumX / pixelCnt, sumY / pixelCnt);

    }

    /**
     *
     * @param bi ...
     * @param start ...
     * @param output ...
     * @param color ...
     */
    public static void getCoherentPoints(BufferedImage bi, Point start, List<Point> output, Color color) {
        output.add(start);
        int startX = start.x - 1;
        int startY = start.y - 1;
        int endX = start.x + 3;
        int endY = start.y + 3;

        if (startX < 0) {
            startX = 0;
        }
        if (endX > bi.getWidth()) {
            endX = bi.getWidth();
        }
        if (startY < 0) {
            startY = 0;
        }
        if (endY > bi.getHeight()) {
            endY = bi.getHeight();
        }

        // System.out.println("xs: " + startX + "; xe: " + endX + "; ys: " +
        // startY + "; ye: " + endY);
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                if (x != start.x && y != start.y && bi.getRGB(x, y) == color.getRGB()) {
                    Point p = new Point(x, y);
                    if (!output.contains(p)) {
                        getCoherentPoints(bi, p, output, color);
                    }
                }
            }
        }
    }

    /**
     *
     * @param bi ...
     * @param w ...
     * @param h ...
     * @param pixels ...
     * @param color ...
     */
    public static void coherentPixels(BufferedImage bi, int w, int h, Point[] pixels, Color color) {
        boolean proceed = true;
        int additions = 0;

        int colorIs = 0;
        int colorExpected = color.getRGB();

        int offsetStart = 0;

        while (proceed) {
            for (int i = offsetStart; i < pixels.length; i++) {
                if (pixels[i] != null) {
                    Point p = pixels[i];
                    int xs = p.x - 1;
                    int ys = p.y - 1;
                    int xe = xs + 3;
                    int ye = ys + 3;

                    if (xs < 0) {
                        xs = 0;
                    }
                    if (ys < 0) {
                        ys = 0;
                    }
                    if (xe > bi.getWidth()) {
                        xe = bi.getWidth();
                    }
                    if (ye > bi.getHeight()) {
                        ye = bi.getHeight();
                    }

                    int offset = additions + 1;

                    for (int x = xs; x < xe; x++) {
                        for (int y = ys; y < ye; y++) {
                            colorIs = bi.getRGB(x, y);
                            Point tmpP = new Point(x, y);
                            if (colorIs == colorExpected && !containsPoint(pixels, tmpP)) {
                                pixels[i + offset] = tmpP;
                                offset++;
                                additions++;
                            }
                        }
                    }
                } else {
                    break;
                }
            }
            if (additions == 0) {
                proceed = false;
                additions = 0;
            } else {
                proceed = true;
                additions = 0;
            }
        }

    }

    /**
     *
     * @param points ...
     * @param p ...
     * @return ...
     */
    private static boolean containsPoint(Point[] points, Point p) {
        for (Point pTmp : points) {
            if (pTmp == null) {
                return false;
            }
            if (pTmp.equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param bi ...
     * @param minLength ...
     * @return ...
     */
    public static BufferedImage extractEdges(BufferedImage bi, int minLength) {
        BufferedImage output = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());

        // vertical
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 1; y < bi.getHeight(); y++) {
                int c1 = ColorUtils.toPlainRgb(bi.getRGB(x, y));
                int c2 = ColorUtils.toPlainRgb(bi.getRGB(x, y - 1));

                int diff = c1 - c2;

                if (diff != 0) {
                    output.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    if (y == 1) {
                        output.setRGB(x, y - 1, Color.WHITE.getRGB());
                    }
                    output.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        // horizontal
//		for(int x = 1; x < bi.getWidth(); x++){
//			for(int y = 0; y < bi.getHeight(); y++){
//				output.setRGB(x, y, Color.WHITE.getRGB() - bi.getRGB(x, y) - bi.getRGB(x - 1, y));
//			}
//		}
        for (int x = 1; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                int c1 = bi.getRGB(x, y);
                int c2 = bi.getRGB(x - 1, y);

                int diff = c1 - c2;

                if (diff != 0) {
                    output.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        return output;
    }

    /**
     *
     * @param bi ...
     * @param maxVal ...
     * @param c ...
     */
    public static void setColorForMaxHsvValue(BufferedImage bi, int maxVal, Color c) {
        float value = (float) maxVal / 255;
        int w = bi.getWidth();
        int h = bi.getHeight();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                float origValue = ColorUtils.rgbToHsb(bi.getRGB(x, y))[2];
                if (origValue <= value) {
                    bi.setRGB(x, y, c.getRGB());
                }
            }
        }
    }

}
