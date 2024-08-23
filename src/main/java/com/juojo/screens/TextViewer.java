package com.juojo.screens;

import java.io.IOException;
import java.nio.file.Path;

import com.juojo.commands.CreateCommandInstance;
import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class TextViewer extends Screen {
	
	private static boolean canHandleFiles = true;
	
	public TextViewer(int row, int col, String[] args) {
		super(row, col, canHandleFiles);
		ANSI.clearScreen();
	
		printHomeScreen();		
		super.printStatusBar(false, cursor.getCol(), cursor.getRow(), cursor.getExCol());
		
		if (args.length != 0) {
			super.changeMode(Mode.NORMAL_MODE);
			new CreateCommandInstance("open " + args[0], this);
		}
		
		ANSI.moveCursorHome();
		
        while (super.getLoop()) {
			try {
				super.handleKey();
			} catch (IOException e) {
				Alerts.newCustomAlert("Error", e.toString(), Colors.RED, null);
				//e.printStackTrace();
			}
			
			if (super.charCode >= 0) {
				if (super.mode == Mode.INSERT_MODE) {
					super.data.insert((char) super.charCode, cursor.getRow(), cursor.getCol());
				} else if (super.mode == Mode.EX_MODE) {
//					printChar();
//					cursor.incrementExCol(1);
				}
			}
		}
	}

	private void printHomeScreen() {
		for (int i = 0; i < super.getTerminalRow()-super.getStatusHeight(); i++) {
			System.out.printf("%s\r\n", Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
		}
	}

	private void printChar() {
		System.out.print((char) super.charCode);
	}

}
