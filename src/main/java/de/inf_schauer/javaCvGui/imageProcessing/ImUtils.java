package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImUtils {

	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static BufferedImage copyImage(BufferedImage input){
		
		BufferedImage result = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
		
		for(int i = 0; i < input.getHeight(); i++){
			for(int j = 0; j < input.getWidth(); j++){
				result.setRGB(j, i, input.getRGB(j, i));
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param bi
	 * @param path
	 */
	public static void writeImage(BufferedImage bi, String path){
		try {
			ImageIO.write(bi, "png", new File(path));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
