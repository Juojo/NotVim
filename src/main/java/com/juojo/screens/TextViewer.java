package com.juojo.screens;

import java.io.IOException;

import com.juojo.util.Colors;
import com.juojo.util.Util;

public class TextViewer extends Screen {
	
	public TextViewer(int row, int col) {
		super(row, col);	
		Util.clearScreen();
	
		// Print initial screen
		for (int i = 0; i < row-super.getStatusHeight(); i++) {
			System.out.printf("%s\r\n", super.returnColorString("~", Colors.BLUE.getFgColor(), null));
		}
		printStatusBar(false);
		
		Util.moveCursorHome();
		
		// Handle key and print char
        while (super.getLoop()) {
			try {
				handleKey();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (super.mode == Mode.INSERT_MODE || super.mode == Mode.EX_MODE) System.out.print((char) super.firstChar);
		}
	}

	
	
}
