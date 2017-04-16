package de.inf_schauer.javaCvGui.config;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "config")
@XmlType(propOrder = { "threshold", "ppi","smoothCnt"})
public class ConfigXml {
	
	private int threshold;
	private int ppi;
	private int smoothCnt;

	public int getSmoothCnt() {
		return smoothCnt;
	}
	public void setSmoothCnt(int smoothCnt) {
		this.smoothCnt = smoothCnt;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public int getPpi() {
		return ppi;
	}
	public void setPpi(int ppi) {
		this.ppi = ppi;
	}

}
