package de.inf_schauer.javaCvGui.gui;

import de.inf_schauer.javaCvGui.interfaces.IConsole;
import javafx.scene.control.TextArea;

public class Console implements IConsole {
	
	TextArea ta = null;
	
	public Console(TextArea ta)
	{
		this.ta = ta;
	}

	@Override
	public void append(String str) {
		this.ta.appendText(str);
		this.ta.appendText("\n");
	}

}
