package org.example.screens;

import org.example.util.Util;

public class TextViewer extends Screen {
	
	public TextViewer(int row, int col) {
		super(row, col);
		
		// Print initial screen
		for (int i = 0; i < row-super.getStatusHeight(); i++) {
			System.out.print("~\r\n");
		}
		printStatusBar();
		
		// Move cursor to home position (0; 0)
		Util.moveCursorHome();
		
		// Handle key and print char
        while (super.getLoop()) { // 113 == "q"
			handleKey();
			System.out.print((char) super.lastKey);
		}
	}

	
	
}
