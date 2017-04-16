package de.inf_schauer.javaCvGui.imageProcessing;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import de.inf_schauer.javaCvGui.data.TristateEnum;
import de.inf_schauer.javaCvGui.interfaces.I_ImgObj2d;

public class GraphProc {
	
	public static I_ImgObj2d breadthFirst(BufferedImage bi, Point start, int distance){
		I_ImgObj2d result = new ImgObj2d();
		result.addPoint(start);
		
		int w = bi.getWidth();
		int h = bi.getHeight();
		TristateEnum[][] arr = new TristateEnum[h][w];
		LinkedList<Point> todo = new LinkedList<>();
		todo.add(start);
		
		initCheckStateArr(arr, TristateEnum.Z);
		
		while(!todo.isEmpty()){
			markNeighbors(bi, arr, todo.getLast(), todo, result, distance);
		}
		//System.out.println("################ -- " + getSizeOfCheckStateArr(arr));
		return result;
	}
	
//	private static int getSizeOfCheckStateArr(TristateEnum[][] arr){
//		int cnt = 0;
//		for(int i = 0; i < arr[0].length; i++){
//			for(int j = 0; j < arr.length; j++){
//				if(arr[j][i] == TristateEnum.TRUE){
//					cnt++;
//				}
//			}
//		}
//		return cnt;
//	}
	
	public static void markNeighbors(BufferedImage bi, TristateEnum[][] arr, Point p, List<Point> todo, I_ImgObj2d detected, int distance){
		int w = bi.getWidth();
		int h = bi.getHeight();
		int ys = p.y - distance;
		int ye = p.y + distance;
		int xs = p.x - distance;
		int xe = p.x + distance;
		if(ys < 0) ys = 0;
		if(ye >= h) ye = h - 1;
		if(xs < 0) xs = 0;
		if(xe >= w) xe = w - 1;
		
		for(int y = ys; y <= ye; y++){
			for(int x = xs; x <= xe; x++){
				if(arr[y][x] == TristateEnum.Z){
					boolean isWhite = ColorUtils.isWhite(bi.getRGB(x, y));
					if(isWhite){
						arr[y][x] = TristateEnum.FALSE;
					}else{
						arr[y][x] = TristateEnum.TRUE;
						Point neighbor = new Point(x,y);
						todo.add(neighbor);
						detected.addPoint(neighbor);
					}
				}	
			}
		}
		todo.remove(p);
	}
	
	public static void initCheckStateArr(TristateEnum[][]arr, TristateEnum initialState){
		int h = arr.length;
		int w = arr[0].length;
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				arr[y][x] = initialState;
			}
		}
	}

}
