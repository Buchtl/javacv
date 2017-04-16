package de.inf_schauer.javaCvGui.gui;

import java.awt.Dimension;
import java.awt.Rectangle;

public class SelectionUtils {

	public static void calcSelection(Rectangle selection, int x_end, int y_end)
	{
		int x = selection.x;
		int y = selection.y;
		
		int width = x_end - x;// - x_end;
		int height = y_end - y;// - y_end;
		
		selection.setSize(new Dimension(width, height));
	}
	
	public static void normalizeRect(Rectangle r)
	{
		int x = r.x;
		int y = r.y;
		int w = r.width;
		int h = r.height;
		
		if(w < 0)
		{
			w = Math.abs(w);
			x = x - w;
		}
		if(h < 0)
		{
			h = Math.abs(h);
			y = y - h;
		}
		
		r.setBounds(x, y, w, h);
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int min(int a, int b)
	{
		if(a < b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}
}
