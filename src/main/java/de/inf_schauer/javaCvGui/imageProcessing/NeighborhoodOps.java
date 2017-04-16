package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class NeighborhoodOps {

    /**
     *
     * @param img (Binarized)
     * @param area ...
     * @param Threshold ...
     * @return true if PixelCnt above threshold
     */
    public static boolean checkArea(BufferedImage img, Rectangle area, int Threshold) {
        int img_width = img.getWidth();
        int img_height = img.getHeight();
        int endX = area.x + area.width;
        int endY = area.y + area.height;

        int blackPixelCnt = 0;
        int currentRgb = 0;

        if (endX > img_width) {
            endX = img_width;
        }
        if (endY > img_height) {
            endY = img_height;
        }

        for (int i = area.x; i < endX; i++) {
            for (int j = area.y; j < endY; j++) {

                currentRgb = ColorUtils.toPlainRgb(img.getRGB(i, j));

                if (currentRgb != 0xFFFFFF) {
                    blackPixelCnt++;
                }

            }
        }
        return blackPixelCnt >= Threshold;
    }

}
