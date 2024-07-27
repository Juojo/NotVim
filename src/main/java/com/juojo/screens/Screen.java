package com.juojo.screens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.juojo.commands.CreateCommandInstance;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public abstract class Screen {

	
	private int row, col;
	private int statusHeight = 2;
	private static boolean loop = false;
	
	protected int firstChar;
	
	protected Mode mode;
	
	private List<Integer> charCodeList = new ArrayList<>(); // Array list for commands from EX_MODE
	private String userInput = "";
	
	
	public Screen(int row, int col) {
		mode = Mode.INSERT_MODE;
		this.loop = true;
		resizeScreen(row, col);
	}
	
	protected void handleKey() throws IOException {
		int secondChar = 0, thirdChar = 0;
		firstChar = System.in.read();
		
		Mode originalMode;
		while (firstChar == 27) {
			originalMode = mode;
			changeMode(Mode.NORMAL_MODE);
			
			secondChar = System.in.read();
			
			if (secondChar != 27) { // if 27 the ESC key has been pressed 2 times, restart cycle
				
				// Handle special characters
				if (secondChar == 91) {
					changeMode(originalMode);
					thirdChar = System.in.read();
					if (thirdChar == 65) System.out.print("arrow up");
					if (thirdChar == 66) System.out.print("arrow down");
					if (thirdChar == 67) System.out.print("arrow right");
					if (thirdChar == 68) System.out.print("arrow left");
				} else if (secondChar == 79) {
					changeMode(originalMode);
					thirdChar = System.in.read();
					if (thirdChar == 80) System.out.print("F1");
					if (thirdChar == 81) System.out.print("F2");
				}
				
				firstChar = secondChar;
			} else {
				firstChar = 27;
			}
		}
		
		handleCustomBinds(mode, firstChar);
		
	}
	
	private void handleCustomBinds(Mode actualMode, int charCode) {
		// Handle custom binds for all modes
		if (charCode == 13 || charCode == 10) { // Enter
			System.out.print("");
		}
		
		// Handle custom binds for each mode
		switch (actualMode) {
		case NORMAL_MODE: {
			
			if (charCode == 105) { // i
				changeMode(Mode.INSERT_MODE);
			} else if (charCode == 58) { // :
				changeMode(Mode.EX_MODE);
			}
			
			// u -> undo
			// d -> delete word
			
			
			break;
		}
		case INSERT_MODE: {
			
			if (charCode == 13 || charCode == 10) { // Enter
				System.out.print("\n");
			}
			
			break;
		}
		case EX_MODE: {
			
			if (charCode != 27) {
				if (charCode != 13 && charCode != 10) {
					charCodeList.add(charCode);
				} else {
					cleanRow();
					
					// Remove all unnecessary : from the start of charCodeList
					while (charCodeList.getFirst() == 58) {
						charCodeList.removeFirst();
					}
					
					userInput = charCodeListToString(charCodeList);
					new CreateCommandInstance(userInput);
					
					changeMode(Mode.NORMAL_MODE);
					charCodeList.clear();
					userInput = "";
				}
			}
			
			break;
		}
		case VISUAL_MODE: {
			
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + actualMode);
		}
		
	}

	private String charCodeListToString(List<Integer> charCodeList) {
		String output = "";
		int listLenght = charCodeList.size();
		
		for (int i = 0; i < listLenght; i++) {
			output += Character.toString(charCodeList.getFirst());
			charCodeList.removeFirst();
		}
		
		return output;
	}

	private void cleanRow() {
		Util.moveCursorToColumn(0);
		for (int i = 0; i < col; i++) {
			System.out.print(" ");
		}
		Util.moveCursorToColumn(0);
	}

	private void changeMode(Mode mode) {
		if (this.mode != mode) {
			this.mode = mode;
			//printStatusBar(true);
		}
		
		if (mode == Mode.EX_MODE) {
			Util.moveCursor(row, 0);
		} else if (mode != Mode.EX_MODE) {
			Util.moveCursor(0, 0); // Replace this with last cursor position
			
			// Make sure incomplete commands are cleaned
			charCodeList.clear();
			userInput = "";
		}
	}
	
	protected void printStatusBar(boolean noTitle) {
		Util.saveCursorPosition();
		Util.moveCursor(row+1-statusHeight, 0); // Move cursor to status-bar position

		// Print status-bar
		if (noTitle == false) {
			for (int i = 0; i < col; i++) {
				System.out.print(Util.returnColorString(" ", Colors.DEFAULT, Colors.RED));
			}
			Util.moveCursorToColumn(0);
			
			System.out.print(Util.returnColorString("NotVim text editor", Colors.WHITE, Colors.RED));
		}

		System.out.print("\r\n");
		cleanRow();
		
		if (mode == Mode.INSERT_MODE) System.out.print(Util.returnColorString("-- ", Colors.WHITE, Colors.DEFAULT) + Util.returnColorString(mode.getName(), Colors.RED, Colors.DEFAULT) + Util.returnColorString(" --", Colors.WHITE, Colors.DEFAULT));
		else if (mode == Mode.NORMAL_MODE) System.out.print(Util.returnColorString("-- ", Colors.WHITE, Colors.DEFAULT) + Util.returnColorString(mode.getName(), Colors.BLUE, Colors.DEFAULT) + Util.returnColorString(" --", Colors.WHITE, Colors.DEFAULT));
		else if (mode == Mode.EX_MODE) {
			for (int i = 0; i < col; i++) {
				System.out.print(" ");
			}
			Util.moveCursorToColumn(0);
		}
		
		
		Util.restoreCursorPosition();
	}
	
	private void resizeScreen(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	

	protected boolean getLoop() {
		return loop;
	}
	
	protected int getStatusHeight() {
		return statusHeight;
	}

	public static void endLoop() {
		loop = false;
	}
	
	//System.out.println("\033[4;44;31mHola\033[0m");
}
 