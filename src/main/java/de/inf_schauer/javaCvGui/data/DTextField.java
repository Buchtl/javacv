package de.inf_schauer.javaCvGui.data;

import java.awt.Rectangle;

public class DTextField extends AbstractField {
	
	String text;
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public DTextField(Rectangle pos) {
		super(pos);
		this.text = "";
		// TODO Auto-generated constructor stub
	}
	
	public DTextField(Rectangle pos, String text)
	{
		super(pos);
		this.text = text;
	}

}
