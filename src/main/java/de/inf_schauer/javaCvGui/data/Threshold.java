package de.inf_schauer.javaCvGui.data;

import java.util.logging.Logger;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Threshold {

    Mat src;
    Mat dst;
    private int max_val;
    private int block_size;
    private double param1;
    int adaptive_method;
    int threshold_type;

    public Threshold() {
        this.block_size = 11;
        this.param1 = 2;
    }

    public Threshold(Mat src, Mat dst, int max_val, int adaptive_method, int threshold_type, int block_size, double param1) {
        super();
        this.src = src;
        this.dst = dst;
        this.max_val = max_val;
        this.block_size = block_size;
        this.param1 = param1;
        this.adaptive_method = adaptive_method;
        this.threshold_type = threshold_type;
    }

    public Mat getSrc() {
        return src;
    }

    public void setSrc(Mat src) {
        this.src = src;
    }

    public Mat getDst() {
        return dst;
    }

    public void setDst(Mat dst) {
        this.dst = dst;
    }

    public int getMax_val() {
        return max_val;
    }

    public void setMax_val(int max_val) {
        this.max_val = max_val;
    }

    public int getBlock_size() {
        return block_size;
    }

    public void setBlock_size(int block_size) {
        this.block_size = block_size;
    }

    public double getParam1() {
        return param1;
    }

    public void setParam1(double param1) {
        this.param1 = param1;
    }

    public int getAdaptive_method() {
        return adaptive_method;
    }

    public void setAdaptive_method(int adaptive_method) {
        this.adaptive_method = adaptive_method;
    }

    public int getThreshold_type() {
        return threshold_type;
    }

    public void setThreshold_type(int threshold_type) {
        this.threshold_type = threshold_type;
    }

    public void start() {
        Logger.getLogger(this.getClass().getName()).info(this.toString());
        //Imgproc.adaptiveThreshold(src, dst, max_val, adaptive_method, threshold_type, block_size, param1);
        Imgproc.threshold(src, dst, this.max_val, 255, Imgproc.THRESH_BINARY);
    }

    @Override
    public String toString() {
        return "AdaThres [max_val=" + max_val + ", block_size=" + block_size
                + ", param1=" + param1 + ", adaptive_method=" + adaptive_method + ", threshold_type=" + threshold_type
                + "]";
    }

}
