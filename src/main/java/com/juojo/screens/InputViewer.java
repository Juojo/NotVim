package com.juojo.screens;

import java.io.IOException;

import com.juojo.util.Colors;
import com.juojo.util.Util;

public class InputViewer extends Screen {

	public InputViewer(int row, int col) {
		super(row, col);
		
		Util.clearScreen();
		Util.moveCursorHome();
		
		// Handle key and print char
        while (super.getLoop()) { // 113 == "q"
			try {
				handleKey();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.printf("%s %s %s %d\r\n", returnColorString("Char:", Colors.RED.getFgColor(), ""), (char) super.firstChar, returnColorString("Int:", Colors.RED.getFgColor(), ""), (int) super.firstChar);
		}
	}

}
