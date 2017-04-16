package de.inf_schauer.javaCvGui.xml;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import de.inf_schauer.javaCvGui.data.Checkbox;
import de.inf_schauer.javaCvGui.imageProcessing.Conversions;
import de.inf_schauer.javaCvGui.imageProcessing.Detection;
import de.inf_schauer.javaCvGui.imageProcessing.DrawingUtils;
import de.inf_schauer.javaCvGui.imageProcessing.RectUtils;
import de.inf_schauer.javaCvGui.interfaces.IConsole;

public class Batch {
	
	public static void runBatch(BufferedImage img, File src, int dpi, int threshold, int smoothCnt, IConsole console)
	{
		List<Checkbox> checkboxes = null;
		
		checkboxes = Unmarshalling.getCheckboxes(src);
		
		for(Checkbox cb : checkboxes)
		{
			Conversions.convertPointsRectToPixel(dpi, cb.getArea());
			Rectangle r = cb.getArea();
			
			if(RectUtils.validRectangle(r))
			{
				BufferedImage box = img.getSubimage(r.x, r.y, r.width, r.height);
				try {
					ImageIO.write(box, "png", new File("tmp" + File.separator + cb.getName() + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean isCbFilled = Detection.processCheckbox(box, threshold, smoothCnt);
				if(isCbFilled)
				{
					DrawingUtils.drawRect(img, r, Color.GREEN, 2);
				}
				else
				{
					DrawingUtils.drawRect(img, r, Color.RED, 2);
				}
				console.append("#" + isCbFilled + "#: " + cb.getName() + "; " + RectUtils.getString(cb.getArea()));
			}
		}
		
//		Rectangle r = new Rectangle(312,322,21,21);
//		BufferedImage box = img.getSubimage(r.x, r.y, r.width, r.height);
//		try {
//			ImageIO.write(box, "png", new File("tmp" + File.separator + "Test.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("TEst ; Area: " + r + "; res: " + Detection.processCheckbox(box, 200));
	}

}
