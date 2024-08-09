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
	private Data data = new Data();
	
	public TextViewer(int row, int col, String[] args) {
		super(row, col, canHandleFiles);
		ANSI.clearScreen();
	
		printHomeScreen();		
		super.printStatusBar(false, cursor.getCol(), cursor.getRow(), cursor.getExCol());
		
		if (args.length != 0) {
			super.changeMode(Mode.NORMAL_MODE);
			new CreateCommandInstance("open " + args[0]);
		}
		
		ANSI.moveCursorHome();
		
        while (super.getLoop()) {
			try {
				super.handleKey();
			} catch (IOException e) {
				Alerts.newCustomAlert("Error", e.toString(), Colors.RED, null);
				//e.printStackTrace();
			}
			
			if (super.mode == Mode.INSERT_MODE) {
				printChar();
				data.insert((char) super.charCode, cursor.getRow(), cursor.getCol());
			} else if (super.mode == Mode.EX_MODE) {
				printChar();
			}
			
			
		}
	}

	private void printHomeScreen() {
		for (int i = 0; i < super.getTerminalRow()-super.getStatusHeight(); i++) {
			System.out.printf("%s\r\n", Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
		}
	}

	private void printChar() {
		if (super.charCode >= 0) {
			System.out.print((char) super.charCode);
			if (super.mode == Mode.INSERT_MODE) cursor.incrementCol(1);
			if (super.mode == Mode.EX_MODE) cursor.incrementExCol(1);
		}
	}

}
