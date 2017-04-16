package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageTransformation {

    /**
     *
     * @param bi ...
     * @param dim ...
     * @return ...
     */
    public static BufferedImage resize(BufferedImage bi, Dimension dim) {
        BufferedImage result = new BufferedImage(dim.width, dim.height, bi.getType());
        Graphics2D g2 = result.createGraphics();

        g2.drawImage(bi, 0, 0, dim.width, dim.height, null);
        g2.dispose();

        return result;
    }

    /**
     *
     * @param image ...
     * @param angle ...
     * @return ...
     */
    public static BufferedImage rotate(BufferedImage image, float angle) {
        float radianAngle = (float) Math.toRadians(angle);

        float sin = (float) Math.abs(Math.sin(radianAngle));
        float cos = (float) Math.abs(Math.cos(radianAngle));

        int w = image.getWidth();
        int h = image.getHeight();

        int neww = (int) Math.round(w * cos + h * sin);
        int newh = (int) Math.round(h * cos + w * sin);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();

        //-----------------------MODIFIED--------------------------------------
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        AffineTransform at = AffineTransform.getTranslateInstance((neww - w) / 2, (newh - h) / 2);
        at.rotate(radianAngle, w / 2, h / 2);
        //---------------------------------------------------------------------

        g.drawRenderedImage(image, at);
        g.dispose();

        return result;
    }

}
