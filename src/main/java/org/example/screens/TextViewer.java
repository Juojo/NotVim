package org.example.screens;

import java.io.IOException;

import org.example.util.Util;

public class TextViewer extends Screen {
	
	public TextViewer(int row, int col) {
		super(row, col);	
		Util.clearScreen();
	
		// Print initial screen
		for (int i = 0; i < row-super.getStatusHeight(); i++) {
			System.out.print("~\r\n");
		}
		printStatusBar();
		
		Util.moveCursorHome();
		
		// Handle key and print char
        while (super.getLoop()) { // 113 == "q"
			try {
				handleKey();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.print((char) super.firstChar);
		}
	}

	
	
}
