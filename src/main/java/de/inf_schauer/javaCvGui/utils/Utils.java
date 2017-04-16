package de.inf_schauer.javaCvGui.utils;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javafx.scene.image.Image;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Utils {
	
	/**
	 * 
	 * @param frame
	 * @return
	 */
	public static Image mat2Image(Mat frame)
	{
		MatOfByte buffer = new MatOfByte();
		
		Imgcodecs.imencode(".png", frame, buffer);
//		try {
//			File img = new File("src/result.png");
//			FileOutputStream fos = new FileOutputStream(img);
//			fos.write(buffer.toArray());
//			fos.close();
//			System.out.println("Path " + img.getAbsolutePath());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
	/**
	 * 
	 * @param frame
	 * @return
	 */
	public static Image mat2Image(Mat frame, File dest)
	{
		MatOfByte buffer = new MatOfByte();
		
		Imgcodecs.imencode(".png", frame, buffer);
		try {
			FileOutputStream fos = new FileOutputStream(dest);
			fos.write(buffer.toArray());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Image(new ByteArrayInputStream(buffer.toArray()));
	}
	
//	/**
//	 * 
//	 * @param frame
//	 * @return
//	 */
//	public static void mat2Image(Mat frame, File dest)
//	{
//		MatOfByte buffer = new MatOfByte();
//		
//		Imgcodecs.imencode(".png", frame, buffer);
//		try {
//			FileOutputStream fos = new FileOutputStream(dest);
//			fos.write(buffer.toArray());
//			fos.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 
	 * @param image
	 * @return
	 */
	public static Mat bufferedImage2Mat(BufferedImage img)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		byte[] pixels = baos.toByteArray();
		
		int height = img.getHeight();
		int width = img.getWidth();

		Mat mat = new Mat(height, width, CvType.CV_8UC3);
		mat.put(0, 0, pixels);
		
		return mat;
	}
	
	public static String detect(File src_file, Rectangle rect, String name, String lang)
	{
		Tesseract tess = new Tesseract();
		
		boolean isTotalScan = false;
		
		if(rect == null)
		{
			isTotalScan = true;
		}
		
		if(lang != null)
		{
			tess.setLanguage(lang);
		}
		
		StringBuffer result = new StringBuffer();
		try {
			if(isTotalScan)
			{
				result.append(name + "" + tess.doOCR(ImageIO.read(src_file)));
			}
			else
			{
				result.append(name + "" + tess.doOCR(ImageIO.read(src_file), rect));
			}
		} catch (TesseractException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public static String pointToString(java.awt.Point p){
		return "x:" + p.x + ",y:" + p.y;
	}
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String dimToString(Dimension d){
		return "w:" + d.width + ",h:" + d.height;
	}

}
