package de.inf_schauer.javaCvGui.comparators;

import java.util.Comparator;

import de.inf_schauer.javaCvGui.interfaces.I_ImgObj2d;

public class ImgObj2dXComparator implements Comparator<I_ImgObj2d> {

	@Override
	public int compare(I_ImgObj2d l, I_ImgObj2d r) {
		// TODO Auto-generated method stub
		int vl = l.getMaxX();
		int vr = r.getMaxX();
		
		if(vl == vr) return 0;
		if(vl < vr) return -1;
		return 1;
	}

}
