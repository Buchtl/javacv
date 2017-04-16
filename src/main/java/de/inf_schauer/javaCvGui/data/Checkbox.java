package de.inf_schauer.javaCvGui.data;

import java.awt.Rectangle;

public class Checkbox {
	
	private Rectangle area;
	private String name;
	
	public Checkbox(Rectangle area, String name)
	{
		this.area = area;
		this.name = name;
	}
	
	public Rectangle getArea() {
		return area;
	}
	public void setArea(Rectangle area) {
		this.area = area;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
