package de.inf_schauer.javaCvGui.gui;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class SelectableImageView {

    private ImageView iv = null;
    private Group imageGroup = null;
    private Rectangle r = null;

    public SelectableImageView(ImageView iv) {
        this.iv = iv;
        this.imageGroup = new Group();
        this.imageGroup.getChildren().add(iv);
    }

    public Group getGroup() {
        return this.imageGroup;
    }

    public void drawSelectionRect(java.awt.Rectangle selection) {
        clear();

        int x = selection.x;
        int y = selection.y;
        int w = selection.width;
        int h = selection.height;

        if (w < 0) {
            w = Math.abs(w);
            x = x - w;
        }
        if (h < 0) {
            h = Math.abs(h);
            y = y - h;
        }

        if ((w != 0) && (h != 0)) {
            r = new Rectangle(x, y, w, h);
            alignSelection(r);
            r.setStroke(Color.BLUE);
            r.setStrokeWidth(1);
            r.setStrokeLineCap(StrokeLineCap.ROUND);
            r.setFill(Color.LIGHTBLUE.deriveColor(0, 1.2, 1, 0.6));
            this.imageGroup.getChildren().add(r);
        }
    }

    public void clear() {
        //Rectangle r = new Rectangle(0,0,0,0);
        this.imageGroup.getChildren().remove(r);
    }

    public ImageView getImageView() {
        return iv;
    }

    public void setImageView(ImageView iv) {
        this.iv = iv;
    }

    public void alignSelection(Rectangle r) {
        Double w = this.iv.getImage().getWidth();
        Double h = iv.getImage().getHeight();
        Double right_border = r.getX() + r.getWidth();
        Double bottom_border = r.getY() + r.getHeight();

        if (right_border > w) {
            r.setWidth(w - r.getX());
        }
        if (bottom_border > h) {
            r.setHeight(h - r.getY());
        }

    }

}
