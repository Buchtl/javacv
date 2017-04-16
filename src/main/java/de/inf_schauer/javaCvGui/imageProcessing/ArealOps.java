package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ArealOps {
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param bi
	 * @return
	 */
	public static int getAverage(int x, int y, int dim, BufferedImage bi)
	{
		float avg_hue = 0f;
		float avg_sat = 0f;
		float avg_bright = 0f;
		int offset = dim/2;
		int start_x = x - offset;
		//if(start_x < 0) start_x = 0;
		int end_x = x + offset;
		//if(end_x == 0) end_x = 1;
		//if(end_x > bi.getWidth()) end_x = bi.getWidth();
		
		int start_y = y - offset;
		//if(start_y < 0) start_y = 0;
		int end_y = y + offset;
		//if(end_y == 0) end_y = 1;
		//if(end_y > bi.getHeight()) end_y = bi.getHeight();
		
		//Color color = new Color(0);
		for(int i = start_x; i <= end_x; i++)
		{
			for(int j = start_y; j <= end_y; j++)
			{
				float[] hsb = ColorUtils.rgbToHsb(bi.getRGB(i, j));
				avg_hue += hsb[0];
				avg_sat += hsb[1];
				avg_bright += hsb[2];
				//System.out.println("x " + x + " i " + i + "  y " + y + " j " + j);
				//color = new Color(255,94,94);
			}
		}
		
		float pixel_cnt = dim * dim;
		avg_hue /= pixel_cnt;
		avg_sat /= pixel_cnt;
		avg_bright /= pixel_cnt;
		
		//System.out.println("Hue: " + avg_hue + ", Sat: " + avg_sat + ", Bright: " + avg_bright);
		//return color.getRGB();
		return Color.HSBtoRGB(avg_hue, avg_sat, avg_bright);
	}
	
	/**
	 * 
	 * @param bi
	 * @param rgb
	 * @param area (Null if whole image shall be processed)
	 * @return
	 */
	public static int countPixelWithColor(BufferedImage bi, int rgb, Rectangle area)
	{
		int result = 0;
		int colorTarget = ColorUtils.toPlainRgb(rgb);
		
		int x = 0;
		int y = 0;
		int width = bi.getWidth();
		int height = bi.getHeight();
		int endX = bi.getWidth();
		int endY = bi.getHeight();
		
		if(area != null){
			x = area.x;
			y = area.y;
			endX = area.x + area.width;
			endY = area.y + area.height;
		}
		
		if(endX > width) endX = width;
		if(endY > height) endY = height;
		
		for(int j = y; j < endY; j++)
		{
			for(int i = x; i < endX; i++)
			{
				if(ColorUtils.toPlainRgb(bi.getRGB(i, j)) == colorTarget)
				{
					result++;
				}
			}
		}
		
		return result;
	}

}
