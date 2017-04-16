package de.inf_schauer.javaCvGui.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Set;

public interface I_ImgObj2d {

    public Point getCenter();

    public void addPoint(Point p);

    public boolean contains(Point p);

    public Dimension getDimension();

    public Rectangle getAreaRect();

    public int Size();

    public boolean hasNeighbor(Point p);

    public boolean isContiguous(I_ImgObj2d imgObj);

    public Set<Point> getPoints();

    public void drawObject(BufferedImage bi, Color color);

    public int getMinX();

    public int getMaxX();

    public int getMinY();

    public int getMaxY();

    public BufferedImage getSimpleImage(Color color, Color bgColor, int margin);

}
