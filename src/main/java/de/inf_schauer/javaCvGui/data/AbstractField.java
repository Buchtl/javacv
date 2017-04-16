package de.inf_schauer.javaCvGui.data;

import java.awt.Rectangle;

public class AbstractField {

    private Rectangle pos;

    public AbstractField(Rectangle pos) {
        super();
        this.pos = pos;
    }

    public Rectangle getPos() {
        return pos;
    }

    public void setPos(Rectangle pos) {
        this.pos = pos;
    }

}
