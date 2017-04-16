package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ObjectExtraction {

    public static List<ImgObj2d> detectObjects(BufferedImage bi) {
        List<ImgObj2d> result = new ArrayList<>();

        int width = bi.getWidth();
        int height = bi.getHeight();
        Dimension dim = new Dimension(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = bi.getRGB(x, y);
                if (!ColorUtils.isWhite(rgb)) {
                    addPoint(result, new Point(x, y), dim);
                }
            }
        }

        return result;
    }

    public static void addPoint(List<ImgObj2d> input, Point p, Dimension dim) {
        boolean isAdded = false;
        for (ImgObj2d imgObj : input) {
            if (imgObj.hasNeighbor(p)) {
                imgObj.addPoint(p);
                isAdded = true;
                return;
            }
        }
        if (!isAdded) {
            ImgObj2d tmp = new ImgObj2d();
            tmp.addPoint(p);
            input.add(tmp);
        }
    }

}
