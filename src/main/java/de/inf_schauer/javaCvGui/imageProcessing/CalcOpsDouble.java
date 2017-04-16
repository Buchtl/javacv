package de.inf_schauer.javaCvGui.imageProcessing;

import de.inf_schauer.javaCvGui.interfaces.I_CalcOps;

public class CalcOpsDouble implements I_CalcOps<Double> {

	@Override
	public Double multiply(Double a, Double b) {
		// TODO Auto-generated method stub
		return a * b;
	}

	@Override
	public Double divide(Double a, Double b) {
		return a/b;
	}

	@Override
	public Double add(Double a, Double b) {
		return a + b;
	}

	@Override
	public Double sub(Double a, Double b) {
		return a - b;
	}

}
