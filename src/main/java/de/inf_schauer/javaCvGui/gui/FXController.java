package de.inf_schauer.javaCvGui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import de.inf_schauer.javaCvGui.data.CharLine;
import de.inf_schauer.javaCvGui.data.ObjectStack;
import de.inf_schauer.javaCvGui.imageProcessing.ColorUtils;
import de.inf_schauer.javaCvGui.imageProcessing.Detection;
import de.inf_schauer.javaCvGui.imageProcessing.DrawingUtils;
import de.inf_schauer.javaCvGui.imageProcessing.GraphProc;
import de.inf_schauer.javaCvGui.imageProcessing.ImProc;
import de.inf_schauer.javaCvGui.imageProcessing.ImUtils;
import de.inf_schauer.javaCvGui.imageProcessing.ImageTransformation;
import de.inf_schauer.javaCvGui.imageProcessing.ImgObj2d;
import de.inf_schauer.javaCvGui.imageProcessing.MathOps;
import de.inf_schauer.javaCvGui.imageProcessing.ObjectExtraction;
import de.inf_schauer.javaCvGui.imageProcessing.ObjectFilter;
import de.inf_schauer.javaCvGui.imageProcessing.OcrHand;
import de.inf_schauer.javaCvGui.imageProcessing.PointUtils;
import de.inf_schauer.javaCvGui.imageProcessing.RectUtils;
import de.inf_schauer.javaCvGui.imageProcessing.Segmentation;
import de.inf_schauer.javaCvGui.interfaces.IConsole;
import de.inf_schauer.javaCvGui.interfaces.IObjectStack;
import de.inf_schauer.javaCvGui.interfaces.I_ImgObj2d;
import de.inf_schauer.javaCvGui.interfaces.I_Ocr;
import de.inf_schauer.javaCvGui.main.AppSingleton;
import de.inf_schauer.javaCvGui.utils.Utils;
import de.inf_schauer.javaCvGui.xml.Batch;
import net.sourceforge.tess4j.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FXController {

    private AppSingleton app = null;

    @FXML
    Parent root;

    @FXML
    private Button button;

    @FXML
    private Button but_objExtraction;

    @FXML
    private Button but_doOcrHand;

    @FXML
    private Button but_setColorBelowValue;

    @FXML
    private Button but_segmentation;

    @FXML
    Button but_saveImage;

    @FXML
    private Button but_resetImageStack;

    @FXML
    private Button but_back;

    @FXML
    private Button but_breadth;

    @FXML
    private Button but_forward;

    @FXML
    private Button but_alignProdWeight;

    @FXML
    private Button but_edgeExtraction;

    @FXML
    private Button but_centersOfMass;

    @FXML
    private Button but_alignProd;

    @FXML
    private Button but_binarizeManual;

    @FXML
    private Button but_filterHSVColor;

    @FXML
    private Button but_resize;

    @FXML
    private Button but_centerOfMass;

    @FXML
    private Button but_filterHSVColorNeg;

    @FXML
    private Button but_coherence;

    @FXML
    private Button but_toGrey;

    @FXML
    private Button but_alignReg;

    @FXML
    private Button but_regLine;

    @FXML
    private Button but_batch;

    @FXML
    private Button but_upturn;

    @FXML
    private Button but_translate;

    @FXML
    private Button but_replaceHLines;

    @FXML
    private Button but_checkBox;

    @FXML
    private Button but_filterHLine;

    @FXML
    private Button but_getTopLeft;

    @FXML
    private Button but_subImage;

    @FXML
    private Button but_filterCluster;

    @FXML
    private Button but_binarizeAuto;

    @FXML
    private Button but_meanSquareDiv;

    @FXML
    private Button but_amplify;

    @FXML
    private Button but_showTempl;

    @FXML
    private Button but_diff;

    @FXML
    private Button but_justOcr;

    @FXML
    private Button but_countPixel;

    @FXML
    private Button but_choosePdf;

    @FXML
    private Button but_justify;

    @FXML
    private Button but_filterHsv;

    @FXML
    private Button but_smooth;

    @FXML
    private Button but_filterHsvNeg;

    @FXML
    private Button but_ampliry;

    @FXML
    private Button but_firstPixel;

    @FXML
    private Button but_binarize;

    @FXML
    private Button but_resetResult;

    @FXML
    private Button but_rotate;

    @FXML
    private Button but_blur;

    @FXML
    private Button but_sharp;

    @FXML
    private Button but_adjust;

    @FXML
    private Button but_readBarcode;

    @FXML
    private Button but_showSelected;

    @FXML
    private Button but_but_showTempl;

    @FXML
    private Button but_showResult;

    @FXML
    private Button but_choosepic;

    @FXML
    private Button but_chooseTempl;

    @FXML
    private Button but_doDefault;

    @FXML
    private Button but_doDiff;

    @FXML
    private Button but_doDiff2;

    @FXML
    private Button but_justDoOcr;

    @FXML
    private Button but_ocrNum;

    @FXML
    private Button but_ocrLetter;

    @FXML
    private Button but_filter;

    @FXML
    private Button but_procCheckbox;

    @FXML
    private Label label_x;

    @FXML
    private Label label_y;

    @FXML
    private ImageView currentFrame;

    @FXML
    private Slider slider;

    @FXML
    private Slider slider_v;

    @FXML
    private Slider slider_h1;
    @FXML
    private Slider slider_s1;
    @FXML
    private Slider slider_v1;

    @FXML
    private Slider slider_h2;
    @FXML
    private Slider slider_s2;
    @FXML
    private Slider slider_v2;

    @FXML
    TextField tf_breadth;

    @FXML
    private TextField tf_r1;

    @FXML
    private TextField tf_g1;

    @FXML
    private TextField tf_b1;

    @FXML
    private TextField tf_r2;

    @FXML
    private TextField tf_g2;

    @FXML
    private TextField tf_b2;

    @FXML
    private TextField tf_v;

    @FXML
    private TextField tf_tess;

    @FXML
    private TextField text_action;

    @FXML
    private TextField text_binarizeMin;

    @FXML
    private TextField text_binarizeMax;

    @FXML
    private TextArea text_console;

    @FXML
    private Label label_slider;

    @FXML
    private Label label_selX;

    @FXML
    private Label label_selY;

    @FXML
    private Label label_selW;

    @FXML
    private Label label_selH;

    @FXML
    private ScrollPane scrollPane_currentFrame;

    private static final Logger logger = Logger.getLogger(FXController.class);

    private ScheduledExecutorService timer;

    private boolean isCameraActive = false;

    private VideoCapture capture = new VideoCapture();

    private File selectedImage = null;
    private File selectedImageTempl = null;
    private File selectedPdf = null;

    private Rectangle selection = null;
    private SelectableImageView siv = null;

    private IObjectStack<BufferedImage> imageStack;

    IConsole console;

    @FXML
    public void initialize() {
        this.app = AppSingleton.getInstance();

        this.imageStack = new ObjectStack<BufferedImage>();
        this.console = new Console(this.text_console);
        this.selection = new Rectangle(0, 0, 0, 0);
        this.selectedImage = this.app.getImg_src();
        // this.setImage(new Image(this.app.getImg_src().toURI().toString()));
        this.addImage(new Image(this.app.getImg_src().toURI().toString()));
        this.siv = new SelectableImageView(this.currentFrame);
        this.scrollPane_currentFrame.setContent(siv.getGroup());

        this.tf_r1.setText("0");
        this.tf_g1.setText("0");
        this.tf_b1.setText("0");
        this.tf_r2.setText("0");
        this.tf_g2.setText("0");
        this.tf_b2.setText("0");

        this.tf_v.setText("0");

        this.slider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                AppSingleton app = AppSingleton.getInstance();
                Double d = new Double(slider.getValue());
                label_slider.setText("" + d.intValue());
                app.setSlider(d.intValue());
                app.getAt().setMax_val(d.intValue());
            }

        });

        this.slider_h1.valueProperty().addListener(new RgbSliderChangeListener(this.tf_r1, this.slider_h1));
        this.slider_s1.valueProperty().addListener(new RgbSliderChangeListener(this.tf_g1, this.slider_s1));
        this.slider_v1.valueProperty().addListener(new RgbSliderChangeListener(this.tf_b1, this.slider_v1));
        this.slider_h2.valueProperty().addListener(new RgbSliderChangeListener(this.tf_r2, this.slider_h2));
        this.slider_s2.valueProperty().addListener(new RgbSliderChangeListener(this.tf_g2, this.slider_s2));
        this.slider_v2.valueProperty().addListener(new RgbSliderChangeListener(this.tf_b2, this.slider_v2));

        this.slider_v.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                AppSingleton app = AppSingleton.getInstance();
                Double d = new Double(slider_v.getValue());
                tf_v.setText("" + d.intValue());
                app.setSlider(d.intValue());
                app.getAt().setMax_val(d.intValue());
            }

        });

    }

    @FXML
    protected void imageForward() {
        BufferedImage bi = imageStack.goForward();
        if (bi != null) {
            this.setImage(bi);
        }
    }

    @FXML
    protected void imageBack() {
        BufferedImage bi = imageStack.goBack();
        if (bi != null) {
            this.setImage(bi);
        }
    }

    @FXML
    protected void dragStarted(MouseEvent event) {
        Double x = event.getX();
        Double y = event.getY();

        this.selection = new Rectangle(x.intValue(), y.intValue(), 0, 0);
    }

    @FXML
    protected void extractObjects() {
        List<ImgObj2d> extracted = ObjectExtraction.detectObjects(getImage());
        console.append("Detected: " + extracted.size() + " objects");
    }

    @FXML
    protected void doSegmentation(ActionEvent evt) {
        BufferedImage bi = getImage();
        Segmentation.getHorizontalSegments(bi);
        addImage(bi);
    }

    @FXML
    protected void doBreadth() {
        int distance = 1;
        CharLine line = new CharLine();
        try {
            distance = Integer.parseInt(tf_breadth.getText());
        } catch (NumberFormatException e) {
            console.append("##" + e.getMessage());
            distance = 1;
        }

        BufferedImage bi = getImage();
        List<Point> start = ImProc.getFirstPointsFromLeft(bi);
        while (!start.isEmpty()) {
            console.append("dB: new Start: " + start.get(0).toString());
            I_ImgObj2d element = GraphProc.breadthFirst(getImage(), start.get(0), distance);
            line.add(element);
            element.drawObject(bi, Color.WHITE);
            addImage(bi);
            start = ImProc.getFirstPointsFromLeft(bi);
        }

        console.append("doBreadth detected " + line.size() + " objects");
        console.append("WordCnt: " + line.getWords().size());

        List<List<I_ImgObj2d>> words = line.getWords();
        boolean sw = true;
        int pos = 1;
        for (List<I_ImgObj2d> word : words) {
            console.append("Word " + pos++ + " size " + word.size());
            Color c;
            if (sw) {
                c = Color.GREEN;
                sw = false;
            } else {
                c = Color.ORANGE;
                sw = true;
            }

            for (I_ImgObj2d ch : word) {
                ch.drawObject(bi, c);
                Dimension dimCh = ch.getDimension();
                Rectangle rectChar = new Rectangle(ch.getMinX(), ch.getMinY(), dimCh.width, dimCh.height);
                DrawingUtils.drawRect(bi, rectChar, Color.RED, 2);
            }
        }

        // for(I_ImgObj2d o : line.getLine()){
        // addImage(o.getSimpleImage(Color.BLACK, Color.WHITE));
        // }
        addImage(bi);
    }

    @FXML
    protected void coherence() {
        List<Point> object = new ArrayList<>();

        BufferedImage bi = ImUtils.copyImage(getImage());
        bi = ImProc.binarize(bi, 40, 90);

        Point[] pixels = new Point[bi.getWidth() * bi.getHeight()];

        Point start = ImProc.getTopLeft(bi, 0);
        pixels[0] = start;
        object.add(start);
        console.append("Coherent start: " + start);

        ImProc.coherentPixels(bi, bi.getWidth(), bi.getHeight(), pixels, Color.BLACK);
        // ImProc.getCoherentPoints(bi, start, object, Color.BLACK);

        console.append("Object size = " + object.size());

        for (Point p : pixels) {
            if (p != null) {
                bi.setRGB(p.x, p.y, Color.WHITE.getRGB());
            } else {
                break;
            }
        }

        addImage(bi);
    }

    @FXML
    protected void updateImagePos(MouseEvent event) {
        Double x = event.getX();
        Double y = event.getY();

        this.label_x.setText("" + x);
        this.label_y.setText("" + y);
    }

    @FXML
    protected void mouseDragged(MouseEvent event) {
        Double x = event.getX();
        Double y = event.getY();
        SelectionUtils.calcSelection(selection, x.intValue(), y.intValue());
        updateSelectionLabel(selection);
        this.siv.drawSelectionRect(selection);
    }

    @FXML
    protected void dragFinished(MouseEvent event) {
        SelectionUtils.normalizeRect(selection);

        // int w = this.getImage().getWidth();
        // int h = this.getImage().getHeight();
        // RectUtils.alignSelection(selection, w,h);
        updateSelectionLabel(selection);

        if (!RectUtils.validRectangle(this.selection)) {
            this.clearSelection();
            this.siv.clear();
        }
    }

    @FXML
    protected void rightClick(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            this.siv.clear();
        }
    }

    @FXML
    protected void startCamera(ActionEvent event) {
        if (!this.isCameraActive) {
            this.capture.open(0);

            if (this.capture.isOpened()) {
                this.isCameraActive = true;

                Runnable frameGrabber = new Runnable() {
                    public void run() {
                        Image imageToShow = grabFrame();
                        currentFrame.setImage(imageToShow);
                        System.out.println("Captured");
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 1000, TimeUnit.MILLISECONDS);

                this.button.setText("Stop Camera");
            } else {
                System.err.println("Impossible to open camera");
            }
        } else {
            this.isCameraActive = false;
            this.button.setText("Start Camera");

            this.timer.shutdown();
            try {
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            this.capture.release();
            this.currentFrame.setImage(null);
        }

    }

    private Image grabFrame() {
        Image imageToShow = null;

        Mat frame = new Mat();

        if (this.capture.isOpened()) {
            try {
                this.capture.read(frame);
            } catch (Exception e) {
                System.err.println("capture.read failed: " + e.getMessage());
            }
            if (!frame.empty()) {
                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                imageToShow = Utils.mat2Image(frame);
            } else {
                System.out.println("Frame empty!!!");
            }
        }
        return imageToShow;
    }

    public void sliderAction() {
        // TODO
    }

    @FXML
    protected void selectImageFile(ActionEvent evt) {
        FileChooser fc = new FileChooser();

        if (this.selectedImage != null) {
            fc.setInitialDirectory(selectedImage.getParentFile());
        }

        fc.setTitle("Choose Image");
        fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        this.selectedImage = fc.showOpenDialog(null);
        // System.out.println("Source: " +
        // this.selectedImage.getAbsolutePath());

        AppSingleton.getInstance().setImg_src(this.selectedImage);

        Image img = new Image(this.selectedImage.toURI().toString());
        addImage(img);
        // this.currentFrame.setImage(img);

        System.out.println("Souruce: " + this.selectedImage.toURI().toString());
    }

    @FXML
    protected void saveImage(ActionEvent evt) {
        FileChooser fc = new FileChooser();
        File outputFile = null;
        fc.setTitle("Save Image");
        fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png"));

        outputFile = fc.showSaveDialog(null);
        // File outputFile = new File();
        try {
            ImageIO.write(getImage(), "png", outputFile);
        } catch (IOException e) {
            console.append(e.getMessage());
        }
    }

    @FXML
    protected void selectImageTemplFile(ActionEvent evt) {
        FileChooser fc = new FileChooser();

        if (this.selectedImage != null) {
            fc.setInitialDirectory(selectedImage);
        }

        fc.setTitle("Choose Image");
        fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        this.selectedImageTempl = fc.showOpenDialog(null);

        AppSingleton.getInstance().setImg_template(this.selectedImageTempl);

        Logger.getLogger(this.getClass().getName()).info("Souruce: " + this.selectedImage.toURI().toString());
    }

    @FXML
    protected void doDefault() {
        AppSingleton app = AppSingleton.getInstance();
        app.doDefault(this.selectedImage, this.text_console, this.currentFrame);
    }

    @FXML
    protected void doDiff() {
        AppSingleton app = AppSingleton.getInstance();
        app.doDiff(this.selectedImage, this.text_console, this.currentFrame);
    }

    @FXML
    protected void doDiff2() {
        AppSingleton app = AppSingleton.getInstance();
        app.doDiff2(this.selectedImage, this.text_console, this.currentFrame);
    }

    public void doOcrHand() {
        Rectangle r = this.selection;
        console.append("ocrHand: " + RectUtils.getString(r));
        BufferedImage bi = getImage().getSubimage(r.x, r.y, r.width, r.height);
        I_Ocr ocr = new OcrHand(1);
        String val = ocr.detect(bi);
        console.append(val);
    }

    public void doJustOcr() {
        this.text_action.setText("just OCR");
        Tesseract tess = new Tesseract();
        // tess.setLanguage("eng2");
        tess.setLanguage(this.tf_tess.getText());
        // tess.setTessVariable("tessedit_char_whitelist", "0123456789,.");
        // tess.setTessVariable("tessedit_char_whitelist",
        // "qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM");
        this.text_console.appendText("Detected:\n----------\n");
        try {
            BufferedImage image = SwingFXUtils.fromFXImage(this.currentFrame.getImage(), null);
            // String txt = tess.doOCR(ImageIO.read(this.selectedImage));
            if (this.selection != null) {
                image = image.getSubimage(this.selection.x, this.selection.y, this.selection.width,
                        this.selection.height);
                String txt = tess.doOCR(image);
                this.text_console.appendText(txt.trim());
            } else {
                String txt = tess.doOCR(image);
                this.text_console.appendText(txt.trim());
            }

        } catch (TesseractException e/* | IOException e */) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.text_console.appendText("\n----------\n");
    }

    public void doOcrNum() {
        this.text_action.setText("just OCR");
        console.append("doOcrNum for " + RectUtils.getString(selection));
        Tesseract tess = new Tesseract();
        tess.setLanguage(this.tf_tess.getText());
        tess.setTessVariable("tessedit_char_whitelist", "0123456789,.");
        // tess.setTessVariable("tessedit_char_whitelist",
        // "qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM");
        try {
            BufferedImage image = SwingFXUtils.fromFXImage(this.currentFrame.getImage(), null);
            if (this.selection != null) {
                image = image.getSubimage(this.selection.x, this.selection.y, this.selection.width,
                        this.selection.height);
                String txt = tess.doOCR(image);
                this.text_console.appendText(txt);
            } else {
                String txt = tess.doOCR(image);
                this.text_console.appendText(txt);
            }

        } catch (TesseractException e/* | IOException e */) {
            // TODO Auto-generated catch block
            console.append(e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                console.append(ste.toString());
            }

            e.printStackTrace();
        }
    }

    public void doOcrLetter() {
        this.text_action.setText("just OCR");
        Tesseract tess = new Tesseract();
        tess.setLanguage(this.tf_tess.getText());
        tess.setTessVariable("tessedit_char_whitelist", "qwertzuiopüasdfghjklöäyxcvbnmQWERTZUIOPÜASDFGHJKLÖÄYXCVBNM");
        try {
            BufferedImage image = SwingFXUtils.fromFXImage(this.currentFrame.getImage(), null);
            if (this.selection != null) {
                image = image.getSubimage(this.selection.x, this.selection.y, this.selection.width,
                        this.selection.height);
                String txt = tess.doOCR(image);
                this.text_console.appendText(txt);
            } else {
                String txt = tess.doOCR(image);
                this.text_console.appendText(txt);
            }

        } catch (TesseractException e/* | IOException e */) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void showSelectedImage(ActionEvent evt) {
        this.siv.clear();
        Image img = new Image(this.app.getImg_src().toURI().toString());
        // try {
        // ImageIO.write(ImageIO.read(this.app.getImg_src()), "png",
        // app.getImg_result());
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        this.addImage(img);
        // this.currentFrame.setImage(img);
    }

    @FXML
    protected void showSelectedTempl(ActionEvent evt) {
        Image img = new Image(AppSingleton.getInstance().getImg_template().toURI().toString());
        this.currentFrame.setImage(img);
    }

    @FXML
    protected void showResultImage(ActionEvent evt) {
        Image img = null;

        img = new Image("file:" + app.getImg_result().getAbsolutePath());

        this.currentFrame.setImage(img);
    }

    @FXML
    protected void resetResult(ActionEvent evt) {
        try {
            ImageIO.write(ImageIO.read(this.app.getImg_src()), "png", app.getImg_result());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void processCheckbox() {
        if (AppSingleton.getInstance().isCheckBox(50)) {
            this.text_console.appendText("\nCheckbox Filled");
        } else {
            this.text_console.appendText("\nCheckbox Blank\n");
        }
    }

    @FXML
    protected void readBarcode() {
        String result = AppSingleton.getInstance().readBarcode(this.selection);
        this.text_console.appendText("\nBarcode: " + result + "\n");
    }

    @FXML
    protected void rotate(ActionEvent evt) {
//		AppSingleton app = AppSingleton.getInstance();
//		app.rotateSelectedImage(Math.PI / 2);
//		app.setImg_src(new File(app.getImg_src().getParent() + File.separator + "rotated.png"));
//		this.currentFrame.setImage(new Image("file:" + app.getImg_src().getAbsolutePath()));
        BufferedImage bi = ImageTransformation.rotate(getImage(), 90);
        addImage(bi);
    }

    @FXML
    protected void adjust(ActionEvent evt) {
        this.currentFrame.setImage(AppSingleton.getInstance().alignDocument());
    }

    @FXML
    protected void doFilter(ActionEvent evt) {
        int h1 = Integer.parseInt(this.tf_r1.getText());
        int s1 = Integer.parseInt(this.tf_g1.getText());
        int v1 = Integer.parseInt(this.tf_b1.getText());
        int h2 = Integer.parseInt(this.tf_r2.getText());
        int s2 = Integer.parseInt(this.tf_g2.getText());
        int v2 = Integer.parseInt(this.tf_b2.getText());

        AppSingleton app = AppSingleton.getInstance();
        app.filter(h1, s1, v1, h2, s2, v2);
        this.currentFrame.setImage(new Image("file:" + app.getImg_result().getAbsolutePath()));
    }

    @FXML
    protected void doBlur(ActionEvent evt) {
        // Image image = this.currentFrame.getImage();
        // Double height = image.getHeight();
        // Double width = image.getWidth();
        // BufferedImage img = new BufferedImage(width.intValue(),
        // height.intValue(), BufferedImage.TYPE_4BYTE_ABGR);
        // SwingFXUtils.fromFXImage(image, img);
        this.app.blur();
        setImage(new Image("file:" + app.getImg_result().getAbsolutePath()));

    }

    @FXML
    protected void doSharp(ActionEvent evt) {
        this.app.sharpening();
        setImage(new Image("file:" + app.getImg_result().getAbsolutePath()));
    }

    @FXML
    protected void doJustify(ActionEvent evt) {
        BufferedImage img = SwingFXUtils.fromFXImage(this.currentFrame.getImage(), null);

        Point p = this.app.getFirstPixelCoord(img);

        if (p != null) {
            this.text_console.appendText("Coord: x:" + p.x + "; y:" + p.y + "\n");
        }
    }

    @FXML
    protected void firstPixel() {
        BufferedImage img = SwingFXUtils.fromFXImage(this.currentFrame.getImage(), null);

        Point p = this.app.getFirstPixelCoord(img);

        if (p != null) {
            this.text_console.appendText("Coord: x:" + p.x + "; y:" + p.y + "\n");
        }
    }

    @FXML
    protected void filterHsv() {
        Double h_min = slider_h1.getValue();
        Double s_min = slider_s1.getValue();
        Double v_min = slider_v1.getValue();
        Double h_max = slider_h2.getValue();
        Double s_max = slider_s2.getValue();
        Double v_max = slider_v2.getValue();

        this.console.append("values: " + " h_min " + h_min.intValue() + ", h_max " + h_max.intValue() + ", s_min "
                + s_min.intValue() + ", s_max " + s_max.intValue() + ", v_min " + v_min.intValue() + ", v_max "
                + v_max.intValue());

        BufferedImage img = this.app.filterHSV(getImage(), h_min.intValue(), s_min.intValue(), v_min.intValue(),
                h_max.intValue(), s_max.intValue(), v_max.intValue(), false);

        try {
            ImageIO.write(img, "png", new File("hallo.jpg"));
            addImage(img);
        } catch (IOException e) {
            this.console.append(e.getMessage());
        }
    }

    @FXML
    protected void filterHsvNegative() {
        Double h_min = slider_h1.getValue();
        Double s_min = slider_s1.getValue();
        Double v_min = slider_v1.getValue();
        Double h_max = slider_h2.getValue();
        Double s_max = slider_s2.getValue();
        Double v_max = slider_v2.getValue();

        this.console.append("values: " + " h_min " + h_min.intValue() + ", h_max " + h_max.intValue() + ", s_min "
                + s_min.intValue() + ", s_max " + s_max.intValue() + ", v_min " + v_min.intValue() + ", v_max "
                + v_max.intValue());

        BufferedImage img = this.app.filterHSV(getImage(), h_min.intValue(), s_min.intValue(), v_min.intValue(),
                h_max.intValue(), s_max.intValue(), v_max.intValue(), true);

        try {
            ImageIO.write(img, "png", new File("hallo.jpg"));
            addImage(img);
        } catch (IOException e) {
            this.console.append(e.getMessage());
        }
    }

    @FXML
    protected void filterCluster() {
        this.addImage(app.filterPixelCluster(this.getImage()));
    }

    @FXML
    protected void amplify() {
        addImage(this.app.amplify(getImage()));
    }

    @FXML
    protected void binarize() {
        Double intensity = slider_v.getValue();

        this.console.append("Pressed binarize for value: " + intensity.floatValue());

        addImage(this.app.binarize(getImage(), intensity.intValue(), console));
    }

    @FXML
    protected void binarizeManual() {
        int min = Integer.parseInt(this.text_binarizeMin.getText());
        int max = Integer.parseInt(this.text_binarizeMax.getText());

        addImage(ImProc.binarize2(getImage(), min, max));
    }

    @FXML
    protected void binarizeAuto() {
        BufferedImage img = getImage();
        ImProc.binarizeAuto(img);
        addImage(img);
    }

    @FXML
    protected void smooth() {
        addImage(ImProc.smooth(getImage(), 3));
    }

    @FXML
    protected void filterHLine() {
        String text = this.tf_v.getText();
        if (text.length() < 1) {
            text = "0";
        }
        int minLen = Integer.parseInt(this.tf_v.getText());

        BufferedImage img = this.getImage();

        console.append("Filter lines with minimal length: " + minLen);
        ObjectFilter.filterLines(img, true, minLen);

        this.addImage(img);
    }

    @FXML
    protected void replaceHLines() {
        String text = this.tf_v.getText();
        if (text.length() < 1) {
            text = "0";
        }
        int minLen = Integer.parseInt(this.tf_v.getText());

        BufferedImage img = this.getImage();

        console.append("Filter lines with minimal length: " + minLen);
        ObjectFilter.replaceHLines(img, minLen);

        this.addImage(img);
    }

    @FXML
    protected void countPixel() {
        Rectangle rect = null;
        if (this.selection != null) {
            if ((this.selection.width != 0) && (this.selection.height != 0)) {
                rect = this.selection;
                ;
            }

        }
        int cnt = ImProc.countBlackPixel(getImage(), rect);
        if (rect != null) {
            this.console.append("Selected rect: " + this.rectToString(rect));
        }
        this.console.append("Selected Pixels for rect: " + cnt);
    }

    @FXML
    protected void toGrey() {
        BufferedImage img = getImage();
        ColorUtils.toGrayScale(img);
        // this.console.append("Average of image: " +
        // ColorUtils.getAverageRGB(img));
        addImage(img);
    }

    @FXML
    protected void choosePdf() {
        FileChooser fc = new FileChooser();

        if (this.selectedPdf != null) {
            fc.setInitialDirectory(this.selectedPdf.getParentFile());
        }

        fc.setTitle("Choose Pdf");
        fc.getExtensionFilters().addAll(new ExtensionFilter("Pdf Files", "*.pdf"));

        this.selectedPdf = fc.showOpenDialog(null);

        this.app.setFilePdf(this.selectedPdf);
        int ppi;
        if (tf_v.getText().equals("")) {
            ppi = 100;
        } else {
            ppi = Integer.parseInt(tf_v.getText());
        }
        try {
            BufferedImage img = this.app.getPdfImage(selectedPdf, ppi, console);

            if (img != null) {
                addImage(img);
            }

        } catch (IOException e) {
            console.append(e.getMessage());
        }
    }

    @FXML
    protected void batch() {
    }

    @FXML
    protected void checkBox() {
        BufferedImage img = getImage();
        BufferedImage box = img.getSubimage(selection.x, selection.y, selection.width, selection.height);

        int threshold = app.getConfig().getThreshold(); // 220
        int smoothCnt = app.getConfig().getSmoothCnt();
        this.console.append("CB th: " + threshold);

        if (Detection.processCheckbox(box, threshold, smoothCnt)) {
            DrawingUtils.drawRect(img, selection, Color.GREEN, 2);
        } else {
            DrawingUtils.drawRect(img, selection, Color.RED, 2);
        }
        addImage(img);
    }

    @FXML
    protected void resize() {
        BufferedImage bi = getImage();
        Dimension dim = new Dimension(bi.getWidth() / 4, bi.getHeight() / 4);
        console.append("Resizing to " + dim);

        bi = ImageTransformation.resize(bi, dim);
        addImage(bi);
    }

    @FXML
    protected void topLeft() {
        BufferedImage img = null;
        int x = this.selection.x;
        int y = this.selection.y;
        int w = this.selection.width;
        int h = this.selection.height;

        if ((y <= 0) || (x <= 0) || (w <= 0) || (h <= 0)) {
            img = ImProc.binarize(getImage(), 40, 90);
        } else {
            img = ImProc.binarize(getImage(), 40, 90).getSubimage(x, y, w, h);
        }

        Point tl = ImProc.getTopLeft(img, 0);
        console.append("TLeft: " + tl);
    }

    @FXML
    protected void upturn() {

        Boolean flip = ImProc.upturnNeeded(ImProc.binarize(getImage(), 40, 90));
        console.append("flip needed = " + flip);
        if (flip) {
            addImage(ImProc.rotate(getImage(), Math.PI, true));
        }

    }

    @FXML
    protected void align() {
        BufferedImage img = null;
        int x = this.selection.x;
        int y = this.selection.y;
        int w = this.selection.width;
        int h = this.selection.height;

        if ((y <= 0) || (x <= 0) || (w <= 0) || (h <= 0)) {
            img = ImProc.binarize(getImage(), 40, 90);
        } else {
            img = ImProc.binarize(getImage(), 40, 90).getSubimage(x, y, w, h);
        }

        Point tl = ImProc.getTopLeft(img, 0);
        console.append("TLeft: " + tl);

        Point tr = ImProc.getTopRight(img, 0);
        console.append("TRight: " + tr);

        Point vec = PointUtils.sub(tr, tl);
        double xv = vec.x;
        double yv = vec.y;
        double hyp = Math.sqrt(xv * xv + yv * yv);
        double radian = Math.asin(vec.x / hyp) - Math.PI / 2;
        console.append("Angle: " + Math.toDegrees(radian));

        if (tl.y < tr.y) {
            addImage(ImProc.rotate(getSelectedImage(), radian, true));
        } else {
            addImage(ImProc.rotate(getSelectedImage(), radian, false));
        }
        this.console.append("Rotated: " + radian);
    }

    @FXML
    protected void alignReg() {
        double radian1 = ImProc.alignmentTop(getSelectedImage());
        align();
        double radian2 = 0;
        BufferedImage bi = ImUtils.copyImage(getImage());
        bi = ImProc.binarize(bi, 40, 90);

        boolean isHorizontal = ImProc.isHorizontal(bi);

        console.append("### IsHorizontal: " + isHorizontal);
        if (!isHorizontal) {
            ImProc.rotate(bi, Math.PI / 2, true);
            radian2 += Math.PI / 2;
        }

        Boolean flip = ImProc.upturnNeeded(bi);
        console.append("### flip needed = " + flip);
        if (flip) {
            ImProc.rotate(bi, Math.PI, true);
            radian2 += Math.PI;
        }

        // radian2 += ImProc.getRotation(bi);
        // console.append("### Rotate: " + radian2);
        addImage(ImProc.rotate(getSelectedImage(), radian1 + radian2, true));

    }

    @FXML
    protected void pointsFromLeft() {
        // setImage(ImProc.f)
    }

    @FXML
    protected void alignProd() {
        BufferedImage bi = ImUtils.copyImage(getImage());
        bi = ImProc.binarize(bi, 40, 90);
        int alignment = ImProc.getAlignment(bi, 0);
        double radian = 0;

        if (alignment == ImProc.SQUARED_ANGLE) {
            radian += Math.PI / 4;
            bi = ImProc.rotate(bi, Math.PI / 4, true);
        }

        alignment = ImProc.getAlignment(bi, 0);

        if (alignment == ImProc.VERTICAL) {
            radian += Math.PI / 2;
            bi = ImProc.rotate(bi, Math.PI / 2, true);
        }

        double tmp_rad = ImProc.alignmentRadian(bi);
        radian += tmp_rad;

        bi = ImProc.rotate(bi, tmp_rad, true);

        console.append("T)MP_RAD: " + tmp_rad);

        Boolean flip = ImProc.upturnNeeded(bi);
        console.append("### flip needed = " + flip);
        if (flip) {
            bi = ImProc.rotate(bi, Math.PI, true);
            radian += Math.PI;
        }

        Rectangle rId = ImProc.extractId(bi, 5, 5);
        console.append("rId = " + RectUtils.getString(rId));

        // setImage(bi);
        addImage(bi.getSubimage(rId.x, rId.y, rId.width, rId.height));

        // BufferedImage result = ImProc.rotate(getSelectedImage(), radian,
        // true);
        // result = result.getSubimage(rId.x, rId.y, rId.width, rId.height);
        // setImage(result);
        this.selection = new Rectangle(0, 0, rId.width, rId.height);

        this.doJustOcr();
    }

    @FXML
    protected void alignProdTmp() {
        BufferedImage bi = ImUtils.copyImage(getImage());
        bi = ImProc.binarize(bi, 40, 90);

        double tmp_rad = 0;// - Math.PI / 4 + Math.PI / 8;

        Map<String, Point> xtPoints = ImProc.getMinMaxXY(bi);

        Point leftPoint = xtPoints.get(ImProc.MINX);
        Point rightPoint = xtPoints.get(ImProc.MAXX);
        Point vec = PointUtils.sub(rightPoint, leftPoint);

        double slope = Math.atan(vec.getY() / vec.getX());

        console.append("AnglePure: " + slope + "; Pi4: " + Math.PI / 4);

        double qPi = Math.PI / 4;

        boolean isRotatedLeft = leftPoint.y > rightPoint.y;

        if (isRotatedLeft) {
            tmp_rad = Math.abs(slope) - qPi;
            bi = ImProc.rotate(bi, tmp_rad, true);
        } else {
            tmp_rad = Math.abs(slope) - qPi;
            bi = ImProc.rotate(bi, tmp_rad, false);
        }

        console.append("TMP_RAD: " + tmp_rad);
        console.append("PL: " + leftPoint.toString());
        console.append("PR: " + rightPoint.toString());

        // Rectangle rId = ImProc.extractId(bi, 5,5);
        // console.append("rId = " + RectUtils.getString(rId));
        addImage(bi);
        // setImage(bi.getSubimage(rId.x, rId.y, rId.width, rId.height));

        // this.selection = new Rectangle(0,0, rId.width, rId.height);
        // this.doJustOcr();
    }

    @FXML
    protected void alignProdWeight() {
        BufferedImage bi = ImUtils.copyImage(getImage());
        bi = ImProc.binarize(bi, 40, 90);
        double piHalf = Math.PI / 2;

        int cnt = 0;
        double threshold = 0.01;

        double rad = threshold + 1;
        double rad_erg = 0;

        bi = ImProc.trimRegion(bi);

        while (rad > threshold) {

            BufferedImage biLeft = ImProc.getSide(bi, ImProc.LEFT);
            BufferedImage biRight = ImProc.getSide(bi, ImProc.RIGHT);

            Point cmLeft = ImProc.calcCenterOfMass(biLeft);
            Point cmRight = ImProc.calcCenterOfMass(biRight);

            cmRight.setLocation(cmRight.x + biLeft.getWidth(), cmRight.y);

            Point vec = PointUtils.sub(cmRight, cmLeft);

            rad = MathOps.calcAngleRad(vec.x, vec.y);

            console.append("Rad bare: " + rad + " cnt:" + cnt);
            if (rad > 0) {
                rad = piHalf - rad;
                rad_erg -= rad;
                bi = ImProc.rotate(bi, rad, false);
            } else if (rad < 0) {
                rad = piHalf - Math.abs(rad);
                rad_erg += rad;
                bi = ImProc.rotate(bi, rad, true);
            }

            console.append("Rad after: " + rad + " cnt:" + cnt);
            cnt++;

        }

        Rectangle rId = ImProc.extractId(ImProc.binarize(bi, 40, 90), 2, 2);
        console.append("RID:: " + RectUtils.getString(rId));

        // console.append("rId = " + RectUtils.getString(rId));
        // bi = ImProc.rotate(ImUtils.copyImage(getImage()), rad_erg, true);
        // DrawingUtils.drawRect(bi, rId, Color.GREEN, 2);
        // setImage(bi);
        addImage(bi.getSubimage(rId.x, rId.y, rId.width, rId.height));

        this.doJustOcr();
    }

    @FXML
    protected void centersOfMass() {
        BufferedImage bi = ImUtils.copyImage(getImage());
        bi = ImProc.binarize(bi, 40, 90);

        // bi = ImProc.trimRegion(bi);
        BufferedImage biLeft = ImProc.getSide(bi, ImProc.LEFT);
        BufferedImage biRight = ImProc.getSide(bi, ImProc.RIGHT);

        Point cmLeft = ImProc.calcCenterOfMass(biLeft);
        Point cmRight = ImProc.calcCenterOfMass(biRight);

        cmRight.setLocation(cmRight.x + biLeft.getWidth(), cmRight.y);

        Rectangle rLeft = new Rectangle(cmLeft.x - 1, cmLeft.y - 1, 3, 3);
        Rectangle rRight = new Rectangle(cmRight.x - 1, cmRight.y - 1, 3, 3);

        DrawingUtils.drawRect(bi, rLeft, Color.RED, 2);
        DrawingUtils.drawRect(bi, rRight, Color.RED, 2);

        addImage(bi);
    }

    @FXML
    protected void regLine() {
        BufferedImage bi = ImProc.binarize(getImage(), 40, 90);
        ImProc.drawRegLine(bi);
        addImage(bi);
    }

    @FXML
    protected void meanSquareDiv() {
        int chan = 0;
        this.console.append("Average: " + ColorUtils.getAverageRGB(getImage()));
        this.console.append("MeanSquareDiv channel" + chan + ": " + ColorUtils.meanSquare(getImage(), chan));
        this.console
                .append("MeanSquareContrast channel" + chan + ": " + ColorUtils.meanSquareDeviation(getImage(), chan));
        addImage(ColorUtils.getHistImage(getImage()));
    }

    @FXML
    protected void subImage() {
        BufferedImage img = getImage().getSubimage(selection.x, selection.y, selection.width, selection.height);
        this.clearSelection();
        addImage(img);
    }

    @FXML
    protected void translate() {
        addImage(ImProc.align(getImage(), new Rectangle(735, 264, 387, 121), new Point(12, 7)));
    }

    @FXML
    protected void filterHSVColor() {
        BufferedImage bi = getImage();

        Double h_min = slider_h1.getValue();
        Double s_min = slider_s1.getValue();
        Double v_min = slider_v1.getValue();
        Double h_max = slider_h2.getValue();
        Double s_max = slider_s2.getValue();
        Double v_max = slider_v2.getValue();

        ImProc.filterHSVColor(bi, h_min.intValue(), s_min.intValue(), v_min.intValue(), h_max.intValue(),
                s_max.intValue(), v_max.intValue(), true);
        addImage(bi);
    }

    @FXML
    protected void filterHSVColorNeg() {
        BufferedImage bi = getImage();

        Double h_min = slider_h1.getValue();
        Double s_min = slider_s1.getValue();
        Double v_min = slider_v1.getValue();
        Double h_max = slider_h2.getValue();
        Double s_max = slider_s2.getValue();
        Double v_max = slider_v2.getValue();

        ImProc.filterHSVColor(bi, h_min.intValue(), s_min.intValue(), v_min.intValue(), h_max.intValue(),
                s_max.intValue(), v_max.intValue(), false);
        addImage(bi);
    }

    @FXML
    protected void calcCenterOfMass() {

        BufferedImage bi = ImUtils
                .copyImage(getImage().getSubimage(selection.x, selection.y, selection.width, selection.height));

        Point p = ImProc.calcCenterOfMass(bi);
        int x = p.x - 1 + selection.x;
        int y = p.y - 1 + selection.y;
        int w = 3;
        int h = 4;
        bi = getImage();
        DrawingUtils.drawRect(bi, new Rectangle(x, y, w, h), Color.RED);

        addImage(bi);
    }

    @FXML
    protected void setColorBelowValue() {
        BufferedImage bi = getImage();
        ImProc.setColorForMaxHsvValue(bi, pInt(this.tf_b2.getText()), Color.BLUE);
        addImage(bi);
    }

    private Integer pInt(String s) {
        int result = 0;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            console.append(e.getMessage());
        }
        return result;
    }

    @FXML
    protected void edgeExtraction() {

        BufferedImage bi = ImProc.binarize(getImage(), 40, 90);

        bi = ImProc.extractEdges(bi, 30);

        addImage(bi);

    }

    public void addImage(BufferedImage bi) {
        WritableImage img = SwingFXUtils.toFXImage(bi, null);
        this.addImage(img);
    }

    public void addImage(Image img) {
        if (this.siv != null) {
            this.clearSelection();
        }
        this.currentFrame.setImage(img);
        BufferedImage bi = SwingFXUtils.fromFXImage(img, null);
        this.imageStack.add(bi);
        this.selection = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
    }

    public void setImage(BufferedImage bi) {
        this.clearSelection();
        // logger.log(Level.INFO, "setImage");
        WritableImage img = new WritableImage(bi.getWidth(), bi.getHeight());
        SwingFXUtils.toFXImage(bi, img);
        this.currentFrame.setImage(img);
        this.selection = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
    }

    public void setCurrentImageStackImage() {
        setImage(imageStack.getCurrentElement());
    }

    @FXML
    public void resetImageStack() {
        this.imageStack.reset();
        setCurrentImageStackImage();
    }

    private void setImage(Image image) {
        this.currentFrame.setImage(image);
    }

    public BufferedImage getImage() {
        return SwingFXUtils.fromFXImage(this.currentFrame.getImage(), null);
    }

    /**
     *
     * @return
     */
    public BufferedImage getSelectedImage() {
        return SwingFXUtils.fromFXImage(new Image(this.selectedImage.toURI().toString()), null);
    }

    /**
     *
     * @param rect
     * @return
     */
    private String rectToString(Rectangle rect) {
        return "x:" + rect.x + ";y:" + rect.y + ";w:" + rect.width + ";h:" + rect.height;
    }

    /**
     *
     * @param r
     */
    private void updateSelectionLabel(Rectangle r) {
        this.label_selX.setText("" + r.x);
        this.label_selY.setText("" + r.y);
        this.label_selW.setText("" + r.width);
        this.label_selH.setText("" + r.height);
    }

    /**
     *
     */
    private void clearSelection() {
        // this.selection.setBounds(0, 0, 0, 0);
        this.siv.clear();
        this.selection.setSize(0, 0);
        updateSelectionLabel(selection);
    }

}
