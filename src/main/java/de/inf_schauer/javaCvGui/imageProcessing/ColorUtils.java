package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ColorUtils {

    /**
     *
     * @param rgb ...
     * @return ...
     */
    public static boolean isWhite(int rgb) {
        int white = 0xFFFFFF;
        int ref = toPlainRgb(rgb);
        return ref == white;
    }

    /**
     *
     * @param rgb ...
     * @return ...
     */
    public static float[] rgbToHsb(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return Color.RGBtoHSB(r, g, b, null);
    }

    /**
     * Removes alpha
     *
     * @param rgb ...
     * @return ...
     */
    public static int toPlainRgb(int rgb) {
        int result = rgb & 0xFFFFFF;
        return result;
    }

    /**
     *
     * @param x ...
     * @param y ...
     * @param img ...
     * @return ...
     */
    public static int toPlainRgb(BufferedImage img, int x, int y) {
        return toPlainRgb(img.getRGB(x, y));
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static BufferedImage toGreyscale(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        int tmp_color = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tmp_color = bi.getRGB(i, j);
                result.setRGB(i, j, tmp_color);
                //System.out.println(rgbToString(result.getRGB(i, j)));
            }
        }

        return result;
    }

    /**
     *
     * @param bi ...
     */
    public static void toGrayScale(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int avg = getAverageRGB(bi.getRGB(i, j));
                bi.setRGB(i, j, toRGB(avg, avg, avg, -1));
            }
        }
    }

    /**
     *
     * @param rgb ...
     * @return ...
     */
    public static int getAverageRGB(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return (r + g + b) / 3;
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static int getAverageRGB(BufferedImage bi) {
        int result = 0;
        int width = bi.getWidth();
        int height = bi.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result += getAverageRGB(bi.getRGB(i, j));
            }
        }

        return result / (width * height);
    }

    /**
     *
     * @param bi ...
     * @param channel ...
     * @return ...
     */
    public static double meanSquare(BufferedImage bi, int channel) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        double result = 0;

        int channel_shift = channel * 8;
        int tmp_color = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tmp_color = (bi.getRGB(i, j) << channel_shift) & 0xFF;
                result += Math.pow(tmp_color, 2);
            }
        }

        return result / (width * height);
    }

    /**
     *
     * @param bi ...
     * @param channel ...
     * @return ...
     */
    public static double meanSquareDeviation(BufferedImage bi, int channel) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        double result = 0;
        double avg = rgbChannel(getAverageRGB(bi), channel);//Math.pow(getAverageRGB(bi),2);

        int channel_shift = channel * 8;
        int tmp_color = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tmp_color = (bi.getRGB(i, j) << channel_shift) & 0xFF;
                result += Math.pow(tmp_color - avg, 2);
            }
        }

        return result / (width * height);
    }

    /**
     *
     * @param bi ...
     * @return ...
     */
    public static float[] greyscaleHist(BufferedImage bi) {
        int greylevels = 256;
        int width = bi.getWidth();
        int height = bi.getHeight();
        float[] result = new float[greylevels];
        int tmp_greylevel = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tmp_greylevel = bi.getRGB(i, j) & 0xFF;
                result[tmp_greylevel]++;
            }
        }

        for (int k = 0; k < greylevels; k++) {
            result[k] /= (width * height);
        }

        return result;
    }

    public static BufferedImage getHistImage(BufferedImage bi) {
        float barHeightScaleFactor = 800;
        float[] hist = greyscaleHist(bi);
        int barWidth = 2;
        int valDim = 256;
        int width_result = barWidth * valDim + 50;
        int height_result = 800 + 50;//(int)(getMaxFloatArr(hist) * barHeightScaleFactor) + 50;
        Dimension dim = new Dimension(width_result, height_result);
        BufferedImage result = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);

        DrawingUtils.setColor(result, Color.WHITE);

        Point originCart = new Point(10, 10);
        int thickness = 1;
        int unitSizeY = 1;
        DrawingUtils.drawCartesian(result, originCart, valDim, (int) barHeightScaleFactor, barWidth, unitSizeY, thickness, Color.RED);

        for (int i = 0; i < valDim; i++) {
            int start = i * barWidth + 1;
            int barHeight = (int) (hist[i] * barHeightScaleFactor);
            DrawingUtils.drawBar(result, originCart, new Rectangle(start, 0, barWidth, barHeight), Color.BLACK);
        }
        return result;

    }

    public static int rgbChannel(int rgb, int channel) {
        return ((rgb >> (8 * channel)) & 0xFF);
    }

    /**
     *
     * @param r ...
     * @param g ...
     * @param b ...
     * @param alpha ...
     * @return ...
     */
    public static int toRGB(int r, int g, int b, int alpha) {
        return ((alpha << 24) | (r << 16) | (g << 8) | b);
    }

    public static String rgbToString(int rgb) {
        int alpha = (rgb >> 24);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return "r:" + r + ";g:" + g + ";b:" + b + ";alpha:" + alpha;

    }

    /**
     *
     * @param arr ...
     * @return ...
     */
    public static float getMaxFloatArr(float[] arr) {
        float result = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > result) {
                result = arr[i];
            }
        }
        return result;
    }

    /**
     *
     * @param rgb ...
     * @return ...
     */
    public static boolean hasAlpha(int rgb) {
        return (rgb >> 16) == 0;
    }

    /**
     *
     * @param bi ...
     * @param color ...
     */
    public static void initImage(BufferedImage bi, Color color) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                bi.setRGB(x, y, color.getRGB());
            }
        }
    }

}
