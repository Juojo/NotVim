package com.juojo.screens;

import java.io.IOException;

import com.juojo.util.Colors;
import com.juojo.util.Util;

public class InputViewer extends Screen {

	public InputViewer(int row, int col) {
		super(row, col, false);
		
		Util.clearScreen();
		Util.moveCursorHome();
		
		// Handle key and print char
        while (super.getLoop()) { // 113 == "q"
			try {
				handleKey();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.printf("%s %s %s %d\r\n", Util.returnColorString("Char:", Colors.RED, Colors.DEFAULT), (char) super.firstChar, Util.returnColorString("Int:", Colors.RED, Colors.DEFAULT), (int) super.firstChar);
		}
	}

}
