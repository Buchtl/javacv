package de.inf_schauer.javaCvGui.data;

import java.awt.Dimension;
import java.awt.Point;

public class SmartPixel {
	private Point p;
	private TristateEnum left;
	private TristateEnum right;
	private TristateEnum top;
	private TristateEnum bottom;
	private TristateEnum bottomLeft;
	private TristateEnum bottomRight;
	private TristateEnum topLeft;
	private TristateEnum topRight;
	
	public SmartPixel(NeighborPos caller, Point p){
		clear();
		this.p = p;
		switch(caller){
		case TOPLEFT:
			topLeft = TristateEnum.TRUE;
			break;
		case TOP:
			top = TristateEnum.TRUE;
			break;
		case TOPRIGHT:
			topRight = TristateEnum.TRUE;
			break;
		case LEFT:
			left = TristateEnum.TRUE;
			break;
		case RIGHT:
			right = TristateEnum.TRUE;
			break;
		case BOTTOMLEFT:
			bottomLeft = TristateEnum.TRUE;
			break;
		case BOTTOM:
			bottom = TristateEnum.TRUE;
			break;
		case BOTTOMRIGHT:
			bottomRight = TristateEnum.TRUE;
			break;
		}
	}

	public SmartPixel() {
		clear();
	}
	
	public void clear(){
		this.left = TristateEnum.Z;
		this.right = TristateEnum.Z;
		this.top = TristateEnum.Z;
		this.bottom = TristateEnum.Z;
		this.bottomLeft = TristateEnum.Z;
		this.bottomRight = TristateEnum.Z;
		this.topLeft = TristateEnum.Z;
		this.topRight = TristateEnum.Z;
	}
	
	public void checkNeigbors(boolean[][] field){
		Dimension dim = new Dimension(field[0].length, field.length);
		int tl = p.x - 1;
		if(tl < 0) tl = 0;
		if(tl >= dim.width) tl = dim.width - 1;
		//if()
	}
	
	public TristateEnum getLeft() {
		return left;
	}
	public void setLeft(TristateEnum left) {
		this.left = left;
	}
	public TristateEnum getRight() {
		return right;
	}
	public void setRight(TristateEnum right) {
		this.right = right;
	}
	public TristateEnum getTop() {
		return top;
	}
	public void setTop(TristateEnum top) {
		this.top = top;
	}
	public TristateEnum getBottom() {
		return bottom;
	}
	public void setBottom(TristateEnum bottom) {
		this.bottom = bottom;
	}
	public TristateEnum getBottomLeft() {
		return bottomLeft;
	}
	public void setBottomLeft(TristateEnum bottomLeft) {
		this.bottomLeft = bottomLeft;
	}
	public TristateEnum getBottomRight() {
		return bottomRight;
	}
	public void setBottomRight(TristateEnum bottomRight) {
		this.bottomRight = bottomRight;
	}
	public TristateEnum getTopLeft() {
		return topLeft;
	}
	public void setTopLeft(TristateEnum topLeft) {
		this.topLeft = topLeft;
	}
	public TristateEnum getTopRight() {
		return topRight;
	}
	public void setTopRight(TristateEnum topRight) {
		this.topRight = topRight;
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}
}
