package org.example.screens;

import org.example.util.Colors;
import org.example.util.Util;

public class InputViewer extends Screen {

	public InputViewer(int row, int col) {
		super(row, col);
		
		Util.clearScreen();
		Util.moveCursorHome();
		
		// Handle key and print char
        while (super.getLoop()) { // 113 == "q"
			handleKey();
			System.out.printf("%s %s %s %d\r\n", printCustomString("Char:", Colors.RED.getColor()), (char) super.lastKey, printCustomString("Int:", Colors.RED.getColor()), (int) super.lastKey);
		}
	}

}
