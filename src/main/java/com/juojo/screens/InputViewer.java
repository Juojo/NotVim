package com.juojo.screens;

import java.io.IOException;

import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class InputViewer extends Screen {

	private static boolean canHandleFiles = false;
	
	public InputViewer(int row, int col) {
		super(row, col, canHandleFiles);
		
		ANSI.clearScreen();
		ANSI.moveCursorHome();
		
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

	private void printChar() {
		System.out.printf("%s %s %s %d\r\n", Util.returnColorString("Char:", Colors.RED, Colors.DEFAULT), (char) super.charCode, Util.returnColorString("Int:", Colors.RED, Colors.DEFAULT), (int) super.charCode);
	}

}
