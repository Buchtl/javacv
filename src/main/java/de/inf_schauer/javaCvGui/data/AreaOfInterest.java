package de.inf_schauer.javaCvGui.data;

import java.awt.Point;
import java.awt.Rectangle;

public class AreaOfInterest {
	private int x;
	private int y;
	private int width;
	private int height;
	
	private String name;
	
	
	
	public AreaOfInterest(int x, int y, int width, int height, String name) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
	}
	
	public int getX() {
		return x;
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,width,height);
	}
	
	public org.opencv.core.Point getTopLeftOpenCV()
	{
		return new org.opencv.core.Point(x,y);
	}
	
	public org.opencv.core.Point getBottomLeftOpenCV()
	{
		return new org.opencv.core.Point(x,y + height);
	}
	
	public org.opencv.core.Point getBottomRightOpenCV()
	{
		return new org.opencv.core.Point(x+width,y+height);
	}
	
	public Point getTopLeft()
	{
		return new Point(x, y);
	}
	
	public Point getBottomLeft()
	{
		return new Point(x, y + height);
	}
	
	public Point getBottomRight()
	{
		return new Point(x+width, y+height);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
