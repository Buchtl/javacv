package de.inf_schauer.javaCvGui.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class RgbSliderChangeListener implements ChangeListener<Number> {
	
	private TextField tf = null;
	private Slider slider = null;
	

	public RgbSliderChangeListener(TextField tf, Slider slider) {
		super();
		this.tf = tf;
		this.slider = slider;
	}



	@Override
	public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
		Double d = new Double(slider.getValue());
		tf.setText("" + d.intValue());

	}

}
