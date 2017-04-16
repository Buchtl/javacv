package de.inf_schauer.javaCvGui.main;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.xmlgraphics.image.codec.util.ImageEncodeParam;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.multi.MultipleBarcodeReader;

import de.inf_schauer.javaCvGui.config.ConfigXml;
import de.inf_schauer.javaCvGui.data.AreaOfInterest;
import de.inf_schauer.javaCvGui.data.Leipzig;
import de.inf_schauer.javaCvGui.data.ObjectStack;
import de.inf_schauer.javaCvGui.data.Threshold;
import de.inf_schauer.javaCvGui.imageProcessing.ArealOps;
import de.inf_schauer.javaCvGui.imageProcessing.ColorUtils;
import de.inf_schauer.javaCvGui.imageProcessing.ImProc;
import de.inf_schauer.javaCvGui.imageProcessing.NeighborhoodOps;
import de.inf_schauer.javaCvGui.imageProcessing.ObjectFilter;
import de.inf_schauer.javaCvGui.interfaces.IConsole;
import de.inf_schauer.javaCvGui.interfaces.IObjectStack;
import de.inf_schauer.javaCvGui.utils.Utils;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AppSingleton {

	private static AppSingleton instance;

	private static final String STR_IMG_RESULT = "src/result.png";
	private File img_src = new File("src/form.jpg");
	private File img_template = new File("src/formtempl.jpg");
	private File img_result = new File("src/result.png");
	private File file_pdf = null;

	private int slider = 0;

	private int r1 = 0;
	private int g1 = 0;
	private int b1 = 0;
	private int r2 = 0;
	private int g2 = 0;
	private int b2 = 0;

	private Threshold at = null;

	private IObjectStack<BufferedImage> imageStack;

	private AppSingleton() {
		this.at = new Threshold();
		this.setImageStack(new ObjectStack<BufferedImage>());
	}

	public static AppSingleton getInstance() {
		if (AppSingleton.instance == null) {
			AppSingleton.instance = new AppSingleton();
		}

		return instance;
	}

	/**
	 * 
	 * @return
	 */
	public ConfigXml getConfig() {
		ConfigXml config = null;

		try {
			JAXBContext jc = JAXBContext.newInstance(ConfigXml.class);
			Unmarshaller um = jc.createUnmarshaller();
			ClassLoader cl = getClass().getClassLoader();
			File configFile = new File(cl.getResource("config" + File.separator + "config.xml").getFile());
			config = (ConfigXml) um.unmarshal(configFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return config;
	}

	public int getR1() {
		return r1;
	}

	public void setR1(int r1) {
		this.r1 = r1;
	}

	public int getG1() {
		return g1;
	}

	public void setG1(int g1) {
		this.g1 = g1;
	}

	public int getB1() {
		return b1;
	}

	public void setB1(int b1) {
		this.b1 = b1;
	}

	public int getR2() {
		return r2;
	}

	public void setR2(int r2) {
		this.r2 = r2;
	}

	public int getG2() {
		return g2;
	}

	public void setG2(int g2) {
		this.g2 = g2;
	}

	public int getB2() {
		return b2;
	}

	public void setB2(int b2) {
		this.b2 = b2;
	}

	public int getSlider() {
		return slider;
	}

	public void setSlider(int slider) {
		this.slider = slider;
	}

	public File getImg_src() {
		return img_src;
	}

	public void setImg_src(File img_src) {
		this.img_src = img_src;
	}

	public File getImg_template() {
		return img_template;
	}

	public void setImg_template(File img_template) {
		this.img_template = img_template;
	}

	public Threshold getAt() {
		return at;
	}

	public void setAt(Threshold at) {
		this.at = at;
	}

	public File getImg_result() {
		return img_result;
	}

	public void setImg_result(File img_result) {
		this.img_result = img_result;
	}

	public void doDefault(File selectedImage, TextArea text_console, ImageView currentFrame) {
		System.out.println("doDefault " + selectedImage.getAbsolutePath());
		Mat image = new Mat();
		Mat output = new Mat();
		// image =
		// org.opencv.imgcodecs.Imgcodecs.imread(this.selectedImage.getAbsolutePath());
		image = Imgcodecs.imread(selectedImage.getAbsolutePath(), 0);

		// Imgproc.bilateralFilter(image, output, -1, 0, 10);
		// Imgproc.Canny(image, output, 100, 1);

		AppSingleton app = AppSingleton.getInstance();
		Threshold at = app.getAt();
		at.setSrc(image);
		at.setDst(output);
		at.setAdaptive_method(Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);
		at.setThreshold_type(Imgproc.THRESH_BINARY);
		at.setBlock_size(11);
		at.setParam1(2);

		app.getAt().start();

		List<AreaOfInterest> detectables = new ArrayList<AreaOfInterest>();

		String tessLang = "eng2";
		AreaOfInterest max = new AreaOfInterest(28, 225, 230, 20, "max");
		AreaOfInterest iban = new AreaOfInterest(28, 90, 280, 20, "iban");

		// DTextField erika = new DTextField(14,45,210,20);
		AreaOfInterest erika = new AreaOfInterest(14, 85, 400, 30, "erika");

		// detectables.add(max);
		// detectables.add(iban);
		detectables.add(erika);

		Scalar color = new Scalar(0, 0, 0);

		Iterator<AreaOfInterest> iter = detectables.iterator();
		while (iter.hasNext()) {
			AreaOfInterest tmp = iter.next();
			Imgproc.rectangle(output, tmp.getTopLeftOpenCV(), tmp.getBottomRightOpenCV(), color);
		}

		Image img_result = Utils.mat2Image(output, new File(STR_IMG_RESULT));

		File src_file = new File(STR_IMG_RESULT);// new File("tmp.png");

		int number = 0;
		iter = detectables.iterator();
		while (iter.hasNext()) {
			text_console.appendText(Utils.detect(src_file, iter.next().getRectangle(), number++ + "", tessLang));
			// this.text_console.appendText(Utils.detect(src_file,
			// iban.getRectangle(), "IBAN"));
			// this.text_console.appendText(Utils.detect(src_file,
			// max.getRectangle(), "MAX"));
		}

		currentFrame.setImage(img_result);
	}

	public void doDiff(File selectedImage, TextArea text_console, ImageView currentFrame) {

		Mat output = new Mat();

		// String form_template = "src/formtempl.jpg";
		// String form = "src/form.jpg";
		// String form_template = "src/helmholtzTempl2.jpg";
		// String form = "src/helmholtz2.jpg";

		org.opencv.core.Mat model = Imgcodecs.imread(this.img_src.getAbsolutePath(), 0);
		org.opencv.core.Mat scene = Imgcodecs.imread(this.img_template.getAbsolutePath(), 0);
		output = new org.opencv.core.Mat();
		Core.absdiff(model, scene, output);
		Imgproc.threshold(output, output, 15, 255, Imgproc.THRESH_BINARY_INV);

		List<AreaOfInterest> detectables = new ArrayList<AreaOfInterest>();

		String tessLang = "eng2";

		AreaOfInterest checkbox_erstkarte = new AreaOfInterest(675, 98, 20, 20, "erstkarte");
		AreaOfInterest erika = new AreaOfInterest(30, 85, 400, 27, "erika");
		String text_erika = "blank";
		AreaOfInterest max = new AreaOfInterest(220, 360, 210, 50, "max");
		String text_max = "blank";

		detectables.add(checkbox_erstkarte);
		detectables.add(erika);
		detectables.add(max);

		Scalar color = new Scalar(0, 0, 0);

		Iterator<AreaOfInterest> iter = detectables.iterator();
		// while(iter.hasNext())
		// {
		// AreaOfInterest tmp = iter.next();
		// Imgproc.rectangle(output, tmp.getTopLeftOpenCV(),
		// tmp.getBottomRightOpenCV(), color);
		// }
		//
		Image img_result = Utils.mat2Image(output, new File(STR_IMG_RESULT));

		File src_file = new File(STR_IMG_RESULT);// new File("tmp.png");

		int number = 0;
		iter = detectables.iterator();
		while (iter.hasNext()) {
			AreaOfInterest tmp = iter.next();

			if (tmp.getName().equals("erika")) {
				text_erika = Utils.detect(src_file, tmp.getRectangle(), "", tessLang);
			} else if (tmp.getName().equals("max")) {
				text_max = Utils.detect(src_file, tmp.getRectangle(), "", tessLang);
			}

			text_console.appendText(Utils.detect(src_file, tmp.getRectangle(), number++ + "", tessLang));
		}
		text_console.appendText(Utils.detect(src_file, null, "Whole", tessLang));

		currentFrame.setImage(img_result);

	}

	/**
	 * Filling Form afterwards
	 * 
	 * @param selectedImage
	 * @param text_console
	 * @param currentFrame
	 */
	public void doDiff2(File selectedImage, TextArea text_console, ImageView currentFrame) {

		Mat output = new Mat();

		org.opencv.core.Mat model = Imgcodecs.imread(this.img_src.getAbsolutePath(), 0);
		org.opencv.core.Mat scene = Imgcodecs.imread(this.img_template.getAbsolutePath(), 0);
		output = new org.opencv.core.Mat();
		Core.absdiff(model, scene, output);
		Imgproc.threshold(output, output, 15, 255, Imgproc.THRESH_BINARY_INV);

		List<AreaOfInterest> detectables = new ArrayList<AreaOfInterest>();

		String tessLang = "eng2";

		AreaOfInterest checkbox_erstkarte = new AreaOfInterest(675, 98, 20, 20, "erstkarte");
		AreaOfInterest erika = new AreaOfInterest(30, 85, 400, 27, "erika");
		String text_erika = "blank";
		AreaOfInterest max = new AreaOfInterest(220, 360, 210, 50, "max");
		String text_max = "blank";

		detectables.add(checkbox_erstkarte);
		detectables.add(erika);
		detectables.add(max);

		Scalar color = new Scalar(0, 0, 0);

		Iterator<AreaOfInterest> iter = detectables.iterator();
		while (iter.hasNext()) {
			AreaOfInterest tmp = iter.next();
			Imgproc.rectangle(output, tmp.getTopLeftOpenCV(), tmp.getBottomRightOpenCV(), color);
		}

		Image img_result = Utils.mat2Image(output, new File(STR_IMG_RESULT));

		File src_file = new File(STR_IMG_RESULT);// new File("tmp.png");

		int number = 0;
		iter = detectables.iterator();
		while (iter.hasNext()) {
			AreaOfInterest tmp = iter.next();

			if (tmp.getName().equals("erika")) {
				text_erika = Utils.detect(src_file, tmp.getRectangle(), "", tessLang);
			} else if (tmp.getName().equals("max")) {
				text_max = Utils.detect(src_file, tmp.getRectangle(), "", tessLang);
			}

			text_console.appendText(Utils.detect(src_file, tmp.getRectangle(), number++ + "", tessLang));
		}
		text_console.appendText(Utils.detect(src_file, null, "Whole", tessLang));

		currentFrame.setImage(img_result);

		iter = detectables.iterator();
		org.opencv.core.Mat img_edited = Imgcodecs.imread(this.img_template.getAbsolutePath(),
				Imgcodecs.CV_LOAD_IMAGE_COLOR);
		while (iter.hasNext()) {
			AreaOfInterest tmp = iter.next();

			if (tmp.getName().equals("erika")) {
				Imgproc.putText(img_edited, text_erika.trim(), tmp.getBottomLeftOpenCV(), Core.FONT_HERSHEY_COMPLEX,
						new Double(1), color);
			} else if (tmp.getName().equals("max")) {
				Imgproc.putText(img_edited, text_max.trim(), tmp.getBottomLeftOpenCV(), Core.FONT_HERSHEY_COMPLEX,
						new Double(1), color);
			}

		}
		currentFrame.setImage(Utils.mat2Image(img_edited));

	}

	public boolean isCheckBox(int threshold) {
		boolean result = false;
		BufferedImage img = null;
		AreaOfInterest ai = new AreaOfInterest(675, 98, 20, 20, "ai");
		long cnt = 0;

		try {
			img = ImageIO.read(new File(STR_IMG_RESULT));
			BufferedImage img2 = img.getSubimage(ai.getX(), ai.getY(), ai.getWidth(), ai.getHeight());
			ImageIO.write(img2, "PNG", new File(this.img_src.getParent() + File.separator + "checkbox.png"));

			for (int i = 0; i < img2.getHeight(); i++) {
				for (int j = 0; j < img2.getWidth(); j++) {
					System.out.println("RGB: " + img2.getRGB(i, j));
					if (img2.getRGB(i, j) == -16777216) {
						cnt++;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (cnt > threshold) {
			return true;
		}

		return result;
	}

	public void rotateSelectedImage(double radiant) {
		try {
			rotate(radiant, ImageIO.read(this.img_src));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage rotate(double radiant, BufferedImage img) {
		// 90degree
		AffineTransform transform = new AffineTransform();
		transform.translate(img.getHeight() / 2, img.getWidth() / 2);
		transform.rotate(radiant);
		// transform.rotate(Math.PI/2);
		transform.translate(-img.getWidth() / 2, -img.getHeight() / 2);

		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

		BufferedImage img_r = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());

		op.filter(img, img_r);

		try {
			ImageIO.write(img_r, "png", new File(this.img_src.getParent() + File.separator + "rotated.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return img_r;

	}

	public String readBarcode(Rectangle rect) {
		String result = null;
		BufferedImage img = null;
		try {
			img = ImageIO.read(this.img_src);
			LuminanceSource source = new BufferedImageLuminanceSource(
					img.getSubimage(rect.x, rect.y, rect.width, rect.height));
			BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));

			Reader reader = new MultiFormatReader();
			Result res = reader.decode(bitmap);
			result = res.getText();

		} catch (IOException | NotFoundException | ChecksumException | FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Reads the image specified in img_src and writes it to img_result
	 * 
	 * @return
	 */
	public Image alignDocument() {
		BufferedImage img = null;

		try {
			img = ImageIO.read(this.img_src);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Mat source = Imgcodecs.imread(this.img_src.getAbsolutePath(), 0);
		Mat dest = new Mat();
		MatOfByte buf = new MatOfByte();
		Imgcodecs.imencode("*.png", source, buf);

		Imgproc.threshold(source, dest, this.slider, 255, Imgproc.THRESH_BINARY);

		Imgcodecs.imwrite(this.img_result.getAbsolutePath(), dest);

		return Utils.mat2Image(dest);
	}

	/**
	 * Reads the image specified in img_result and writes it back
	 * 
	 * @param h1
	 * @param s1
	 * @param v1
	 * @param h2
	 * @param s2
	 * @param v2
	 */
	public void filter(int h1, int s1, int v1, int h2, int s2, int v2) {
		Mat img = Imgcodecs.imread(this.img_result.getAbsolutePath());
		Mat dst = new Mat();

		Scalar hsv_l = new Scalar(h1, s1, v1);
		Scalar hsv_r = new Scalar(h2, s2, v2);

		// HSV!!!^
		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2HSV);
		Core.inRange(img, hsv_l, hsv_r, dst);
		// Mat dst2 = new Mat();
		// Core.invert(dst, dst);

		Imgcodecs.imwrite(this.img_result.getAbsolutePath(), dst);

		// ArrayList<Mat> chan = new ArrayList<Mat>();;
		//
		// Core.split(img, chan);
		//
		// Imgcodecs.imwrite(this.img_result.getAbsolutePath(), chan.get(1));
	}

	/**
	 * Reads the Image specified in img_result and writes it back
	 */
	public void blur() {
		Mat img = Imgcodecs.imread(this.img_result.getAbsolutePath());
		Mat dst = new Mat();

		Imgproc.GaussianBlur(img, dst, new Size(5, 5), 0);
		// Imgproc.bilateralFilter(img, dst, 20, 5, 5);

		Imgcodecs.imwrite(this.img_result.getAbsolutePath(), dst);
	}

	public void sharpening() {
		Mat img = Imgcodecs.imread(this.img_result.getAbsolutePath());
		Mat dst = new Mat();

		Size s = new Size(0, 0);

		// Imgproc.GaussianBlur(img, dst, s, 3);
		// Core.addWeighted(dst, 1.5, dst, -0.5, 0, dst);
		Imgproc.bilateralFilter(img, dst, 9, 75, 75);

		Imgcodecs.imwrite(this.img_result.getAbsolutePath(), dst);
	}

	/**
	 * 
	 * @param bi
	 * @return
	 */
	public Point getFirstPixelCoord(BufferedImage bi) {
		Point result = null;

		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {
				// better hsv hsi ...
				// System.out.println(bi.getRGB(j, i)&0x00ffffff);
				int alpha_mask = 0x00FFFFFF;

				if ((bi.getRGB(j, i) & alpha_mask) != 0xFFFFFF) {
					result = new Point(j, i);
					break;
				}
			}
			if (result != null) {
				break;
			}
		}

		return result;
	}

	/**
	 * 
	 * @param bi
	 * @return
	 */
	public Point getFirstPixelCoordHsv(BufferedImage bi) {
		Point result = null;
		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {

				int alpha_mask = 0x00FFFFFF;

				int rgb = bi.getRGB(j, i) & alpha_mask;

				int r = rgb & 0xFF0000;
				int g = rgb & 0xFF00;
				int b = rgb & 0xFF;

				float[] hsb = Color.RGBtoHSB(r, g, b, null);
				float hue = hsb[0];
				float saturation = hsb[1];
				float brightness = hsb[2];
				System.out.println("Brightness: " + brightness);
				if (brightness <= 65000) // Integer.MAX_VALUE
				{
					result = new Point(j, i);
					break;
				}
			}
			if (result != null) {
				break;
			}
		}

		return result;
	}

	/**
	 * 
	 * @param bi
	 * @return
	 */
	public BufferedImage amplify(BufferedImage bi) {
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
		float min_saturation = 0.2f;
		float min_brightness = 0.2f;

		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {

				float[] hsb = ColorUtils.rgbToHsb(bi.getRGB(j, i));
				float hue = hsb[0];
				float saturation = hsb[1];
				float brightness = hsb[2];

				// Color color = Color.getHSBColor(0.25f, 1, 1); // Green
				Color color;
				if ((saturation > min_saturation) && (brightness > min_brightness)) {
					color = Color.getHSBColor(hue, 1f, 1f);
				} else {
					color = new Color(1f, 1f, 1f);
				}
				result.setRGB(j, i, color.getRGB());
			}
		}

		return result;
	}

	/**
	 * 
	 * @param bi
	 * @param v
	 * @return
	 */
	public BufferedImage binarize(BufferedImage bi, int threshold, IConsole console) {
		console.append("Binarize: threshold begin = " + threshold);
		return ImProc.binarize(bi, 40, 90);
		// BufferedImage result = new BufferedImage(bi.getWidth(),
		// bi.getHeight(), BufferedImage.TYPE_INT_RGB);
		//
		// console.append("Binarize: threshold begin = " + threshold );
		//
		// for(int i = 0; i < bi.getHeight(); i++)
		// {
		// for(int j = 0; j < bi.getWidth(); j++)
		// {
		//
		// float[] hsb = ColorUtils.rgbToHsb(bi.getRGB(j, i));
		// //float hue = hsb[0];
		// float saturation = hsb[1];
		// //float brightness = hsb[2];
		//
		// float sat = saturation * 100;
		//
		// if( sat > threshold)
		// {
		// result.setRGB(j, i, Color.BLACK.getRGB());
		// }
		// else
		// {
		// result.setRGB(j, i, Color.WHITE.getRGB());
		// }
		// }
		// }
		// return result;
	}

	public void normalize(BufferedImage bi, IConsole console) {
		String output = "first Pixel: " + getFirstPixelCoordHsv(bi);
		// BufferedImage img = binarize(bi);
		// try {
		// ImageIO.write(img, "png", new File("hallo.jpg"));
		// } catch (IOException e) {
		// console.append(e.getMessage());
		// }
		console.append(output);
	}

	/**
	 * 
	 * @param bi
	 * @param output
	 * @param rgb
	 * @param replacement
	 * @param negative
	 * @return
	 */
	public BufferedImage filterRgb(BufferedImage bi, BufferedImage output, int rgb, int replacement, boolean negative) {
		rgb = ColorUtils.toPlainRgb(rgb);
		replacement = ColorUtils.toPlainRgb(replacement);
		BufferedImage result = output;

		if (result == null) {
			result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		}

		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {

				int orig_rgb = ColorUtils.toPlainRgb(bi.getRGB(j, i));

				if (negative) {
					if (orig_rgb != rgb) {
						result.setRGB(j, i, replacement);
					} else {
						result.setRGB(j, i, orig_rgb);
					}
				} else {
					if (orig_rgb == rgb) {
						result.setRGB(j, i, replacement);
					} else {
						result.setRGB(j, i, orig_rgb);
					}
				}
			}
		}

		return result;
	}

	/**
	 * If negative is true, everything in range will be set white. If negative
	 * is false, everything outside the range will be set white.
	 * 
	 * @param bi
	 * @param h_min
	 * @param s_min
	 * @param v_min
	 * @param h_max
	 * @param s_max
	 * @param v_max
	 * @return
	 */
	public BufferedImage filterHSV(BufferedImage bi, int h_min, int s_min, int v_min, int h_max, int s_max, int v_max,
			boolean negative) {

		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < bi.getHeight(); i++) {
			for (int j = 0; j < bi.getWidth(); j++) {

				int orig_rgb = bi.getRGB(j, i);

				float[] hsb = ColorUtils.rgbToHsb(orig_rgb);
				float hue = hsb[0];
				float saturation = hsb[1];
				float brightness = hsb[2];

				float h1 = 0;
				float h2 = 0;
				float s1 = 0;
				float s2 = 0;
				float v1 = 0;
				float v2 = 0;

				h1 = ((float) h_min / 255);
				s1 = (float) s_min / 255;
				v1 = (float) v_min / 255;
				h2 = ((float) h_max / 255);
				s2 = (float) s_max / 255;
				v2 = (float) v_max / 255;

				boolean inH = ((hue >= h1) && (hue <= h2));
				boolean inS = ((saturation >= s1) && (saturation <= s2));
				boolean inV = ((brightness >= v1) && (brightness <= v2));
//				if(h2 < h1){
//					inH = ((hue >= h2) && (hue <= h1));
//				}
//				if(v2 < v1){
//					inV = ((brightness >= v2) && (brightness <= v1));
//				}
				

				if (negative) {
					if (inH && inS && inV) {
						result.setRGB(j, i, 0xFFFFFF);
					} else {
						result.setRGB(j, i, orig_rgb);
					}
				} else {
					if ((inH && inS && inV)){// || brightness <= (0.5)) {
						result.setRGB(j, i, orig_rgb);
					} else {
						result.setRGB(j, i, 0xFFFFFF);
					}
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * @param console
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getPdfImage(File pdf, int ppi, IConsole console) throws IOException {
		BufferedImage img_result = null;

		PDDocument pdfDoc = PDDocument.load(pdf);
		ImageIO.scanForPlugins();
		PDFRenderer pdfRenderer = new PDFRenderer(pdfDoc);
		img_result = pdfRenderer.renderImageWithDPI(0, ppi);
		pdfDoc.close();
		return img_result;
		// try {
		// PDFParser parser = new PDFParser(new
		// RandomAccessBufferedFileInputStream(this.file_pdf));
		// parser.parse();
		// COSDocument cosDoc = parser.getDocument();
		//
		// PDDocument pdDoc = new PDDocument(cosDoc);
		// console.append("getPdfImage: " + pdDoc.getNumberOfPages());
		//
		// PDPage page = pdDoc.getPage(0);
		//
		// PDResources rs = page.getResources();
		//
		// PDXObject o = null;
		// COSName name = null;
		//
		// List<PDImageXObject> pdImages = new ArrayList<PDImageXObject>();
		//
		// Iterator<COSName> iterCos = rs.getXObjectNames().iterator();
		// while(iterCos.hasNext())
		// {
		// name = iterCos.next();
		//
		// if(rs.isImageXObject(name))
		// {
		// o = rs.getXObject(name);
		// PDImageXObject pdImg = (PDImageXObject) rs.getXObject(name);
		//
		// console.append(":: " + name.getName() + " .. ");
		// pdImages.add((PDImageXObject) rs.getXObject(name));
		//
		// File file = new File(this.file_pdf.getName());
		// file.mkdir();
		//
		// ImageIO.write(pdImg.getImage(), "png", new File(file.getPath() +
		// File.separator + "Nr_" + pdImages.size() + ".png"));
		// //img_result = pdImg.getImage();
		// }
		// }
		//
		// PDImageXObject pdImg = (PDImageXObject) rs.getXObject(name);
		// img_result = pdImg.getImage();
		//
		// pdDoc.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// console.append("getPdfImage: left");
		// return img_result;
	}

	public File getFilePdf() {
		return file_pdf;
	}

	public void setFilePdf(File file_pdf) {
		this.file_pdf = file_pdf;
	}

	/**
	 * 
	 * @param bi
	 * @return
	 */
	public BufferedImage filterPixelCluster(BufferedImage bi) {
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
		int width = bi.getWidth();// 8;
		int height = bi.getHeight();// 8;

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				// if(i%2 != 1)//Test
				if (NeighborhoodOps.checkArea(bi, new Rectangle(i, j, 10, 10), 95)) {
					result.setRGB(i, j, Color.BLACK.getRGB());

				} else {
					result.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		}
		return result;
	}

	public IObjectStack<BufferedImage> getImageStack() {
		return imageStack;
	}

	public void setImageStack(IObjectStack<BufferedImage> imageStack) {
		this.imageStack = imageStack;
	}

}
