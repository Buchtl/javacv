package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.image.BufferedImage;

public class Detection {
	
	/**
	 * 
	 * @param img
	 * @param threshold
	 * @return
	 */
	public static boolean processCheckbox(BufferedImage img, int threshold, int smoothCnt)
	{
		BufferedImage bi = ColorUtils.toGreyscale(img);
		
		for(int i = 0; i < smoothCnt; i++)
		{
			bi = ImProc.smooth(bi,3);
			if(i < (smoothCnt - 1)) ImProc.binarizeAuto(bi);
		}
		
		ImProc.binarizeAuto(bi);
		return (ImProc.countBlackPixel(bi) >= threshold);
	}

}
