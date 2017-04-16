package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Rectangle;

public class RectUtils {

    public static boolean validRectangle(Rectangle rect) {
        boolean validHeight = rect.height > 0;
        boolean validWidth = rect.width > 0;

        return validHeight && validWidth;
    }

    public static String getString(Rectangle r) {
        return "x: " + r.x + "; y: " + r.y + "; w: " + r.width + "; h: " + r.height;
    }

    /**
     *
     * @param r ...
     * @param width ...
     * @param height ...
     */
    public static void alignSelection(Rectangle r, int width, int height) {
        //int right_border = container.width;//r.width;
        //int bottom_border = container.height;//r.height;

        int x = r.x;
        int y = r.y;
        int w = r.width;
        int h = r.height;

        if (w < 0) {
            x = x - w;
            w = Math.abs(w);

            if ((w + x) > width) {
                w = width - x;
            }
        }
        if (h < 0) {
            y = y - h;
            h = Math.abs(h);

            if ((h + y) > width) {
                h = height - y;
            }
        }

        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x > width) {
            x = width;
        }
        if (y > height) {
            y = height;
        }

        r.setBounds(x, y, w, h);

    }

}
