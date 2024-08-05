package com.juojo.screens;

import java.io.IOException;

import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class TextViewer extends Screen {
	
	private static boolean canHandleFiles = true;
	
	public TextViewer(int row, int col) {
		super(row, col, canHandleFiles);
		Util.clearScreen();
	
		printHomeScreen();
		super.printStatusBar(false, super.posX, super.posY, super.exPosX);
		
		Util.moveCursorHome();
		
        while (super.getLoop()) {
			try {
				super.handleKey();
			} catch (IOException e) {
				Alerts.newCustomAlert("Error", e.toString(), Colors.RED, null);
				//e.printStackTrace();
			}
			
			printChar();
		}
	}

	private void printHomeScreen() {
		for (int i = 0; i < super.getRow()-super.getStatusHeight(); i++) {
			System.out.printf("%s\r\n", Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
		}
	}

	private void printChar() {
		if (super.charCode >= 0 && (super.mode == Mode.INSERT_MODE || super.mode == Mode.EX_MODE)) {
			System.out.print((char) super.charCode);
			if (super.mode == Mode.INSERT_MODE) super.posX++;
			if (super.mode == Mode.EX_MODE) super.exPosX++;
		}
	}

}
