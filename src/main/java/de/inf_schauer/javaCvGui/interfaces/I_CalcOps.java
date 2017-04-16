package de.inf_schauer.javaCvGui.interfaces;

public interface I_CalcOps <T extends Number> {
	
	public T multiply(T a, T b);
	
	public T divide(T a, T b);
	
	public T add(T a, T b);
	
	public T sub(T a, T b);
}
