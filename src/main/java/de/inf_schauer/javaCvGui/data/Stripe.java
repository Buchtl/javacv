package de.inf_schauer.javaCvGui.data;

public class Stripe {
	private int lowerBound;
	private int upperBound;
	
	public Stripe(int lowerBound, int upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public Stripe() {
		// TODO Auto-generated constructor stub
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
	
	public int getWidth(){
		return Math.abs(lowerBound - upperBound); 
	}
	
	public boolean isInStripe(int input){
		return input >= lowerBound && input <= upperBound;
	}

}
