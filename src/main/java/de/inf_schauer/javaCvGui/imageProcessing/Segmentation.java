package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Segmentation {
	
	public static List<Rectangle> getHorizontalSegments(BufferedImage bi){
		List<Rectangle> result = new ArrayList<>();
		
		int width = bi.getWidth();
		int height = bi.getHeight();
		int startObj = 0;
		int endObj = 0;
		boolean started = false;
		
		for(int x = 0; x < width; x++){
			
			if(!started && !isColumnEmpty(bi, x)){
				startObj = x;
				started = true;
			}
			
			if(started && (isColumnEmpty(bi, x) || x == width - 1)){
				endObj = x - 1;
				if(x == width - 1){
					endObj--;
				}
				started = false;
				// Objekt Speichern
				Rectangle r = new Rectangle(startObj, 0, endObj - startObj, bi.getHeight());
				result.add(r);
				DrawingUtils.drawRect(bi, r, Color.red, 1);
			}
		}
		
		return result;
	}
	
	public static boolean isColumnEmpty(BufferedImage bi, int col){
		int height = bi.getHeight();
		for(int y = 0; y < height; y++){
			if(!ColorUtils.isWhite(bi.getRGB(col, y))){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isRowEmpty(BufferedImage bi, int row){
		int width = bi.getWidth();
		for(int x = 0; x < width; x++){
			if(!ColorUtils.isWhite(bi.getRGB(x, row))){
				return false;
			}
		}
		return true;
	}

}
