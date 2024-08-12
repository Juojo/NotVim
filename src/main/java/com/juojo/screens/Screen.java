package com.juojo.screens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.juojo.commands.CreateCommandInstance;
import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;
import com.juojo.virtualkeymapping.VK;

public abstract class Screen {

	
	private static int terminalRow, terminalCol;
	private static int statusHeight = 2;
	private static boolean loop = false;
	private static boolean canHandleFiles = false;
	
	public static Cursor cursor;
	
	protected int charCode;
	
	protected Mode mode;
	
	private List<Integer> charCodeList = new ArrayList<>(); // Array list for commands from EX_MODE
	private String userInput = "";
	
	public Screen(int row, int col, boolean canHandleFiles) {
		resizeScreen(row, col);
		this.loop = true;
		this.canHandleFiles = canHandleFiles;
		mode = Mode.INSERT_MODE;
		
		cursor = new Cursor(this);
		ANSI.moveCursorHome();
	}
	
	protected void handleKey(int rowLenght, int amountOfRows) throws IOException {
		int secondChar = 0, thirdChar = 0;
		charCode = System.in.read();
		
		Mode originalMode;
		while (charCode == 27) {
			originalMode = mode;
			changeMode(Mode.NORMAL_MODE);
			
			secondChar = System.in.read();
			
			if (secondChar != 27) { // if 27 the ESC key has been pressed 2 times, restart cycle
				
				// Handle special characters
				if (secondChar == 91) {
					changeMode(originalMode);
					thirdChar = System.in.read();
					if (thirdChar == 65) charCode = VK.ARROW_UP.getCode();
					if (thirdChar == 66) charCode = VK.ARROW_DOWN.getCode();
					if (thirdChar == 67) charCode = VK.ARROW_RIGHT.getCode();
					if (thirdChar == 68) charCode = VK.ARROW_LEFT.getCode();
				} else if (secondChar == 79) {
					changeMode(originalMode);
					thirdChar = System.in.read();
					if (thirdChar == 80) charCode = VK.F1.getCode();
					if (thirdChar == 81) charCode = VK.F2.getCode();
				} else {
					charCode = secondChar;
				}
				
			} else {
				charCode = 27;
			}
		}
		
		handleCustomBinds(mode, charCode);
		cursor.handleMovementKeys(charCode, rowLenght, amountOfRows);
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
				Alerts.setActiveAlertFalse();
				changeMode(Mode.EX_MODE);
			}
			
			// u -> undo
			// d -> delete word
			
			
			break;
		}
		case INSERT_MODE: {
			
			if (charCode == 13 || charCode == 10) { // Enter);
				cursor.moveSet(0, cursor.getRow()+1);
			}
			
			break;
		}
		case EX_MODE: {
			
			if (charCode != 27) {
				if (charCode != 13 && charCode != 10) {
					charCodeList.add(charCode);
				} else {
					cleanRow();
					cursor.setExCol(1);

					// Remove all unnecessary : from the start of charCodeList
					while (!charCodeList.isEmpty() && charCodeList.getFirst() == 58) {
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

	protected void changeMode(Mode mode) {
		int currentCol = cursor.getCol();
		int currentRow = cursor.getRow();
		int currentExCol = cursor.getExCol();
		
		if (this.mode != mode) {
			this.mode = mode;
			cursor.updateMode(mode);
			
			if (mode == Mode.INSERT_MODE || mode == Mode.VISUAL_MODE) {
				Alerts.setActiveAlertFalse();
				printStatusBar(false, currentCol, currentRow, currentExCol);
			} else {
				printStatusBar(true, currentCol, currentRow, currentExCol);
			}
				
			if (mode == Mode.EX_MODE) {
				ANSI.moveCursor(terminalRow, 0);
			} else {
				cursor.moveSet(currentCol, currentRow);
				
				// Make sure incomplete commands are cleaned
				charCodeList.clear();
				userInput = "";
			}
		}
		
		
	}
	
	protected void printStatusBar(boolean modeOnTitle, int currentX, int currentY, int currentExPosX) {
		ANSI.moveCursor(terminalRow+1-statusHeight, 0); // Move cursor to status-bar position

		// Print status-bar
		for (int i = 0; i < terminalCol; i++) {
			System.out.print(Util.returnColorString(" ", Colors.DEFAULT, Colors.RED));
		}
		ANSI.moveCursorToColumn(0);

		System.out.print(Util.returnColorString("NotVim text editor", Colors.WHITE, Colors.RED));
		
		if (modeOnTitle) { // Can replace this with if (mode == Mode.INSERT_MODE || mode == Mode.VISUAL_MODE)
			System.out.print(Util.returnColorString("  --  " + mode.getName() + "  --  ", Colors.BLUE, Colors.RED));
		}

		System.out.print("\r\n");
		if (Alerts.getActiveAlert() == false) { // Don't clean last row if an alert is active
			cleanRow();
		}
		
		if (mode == Mode.INSERT_MODE) System.out.print(Util.returnColorString("-- ", Colors.WHITE, Colors.DEFAULT) + Util.returnColorString(mode.getName(), Colors.RED, Colors.DEFAULT) + Util.returnColorString(" --", Colors.WHITE, Colors.DEFAULT));
		
		if (mode != Mode.EX_MODE) ANSI.moveCursor(currentY, currentX);
		else ANSI.moveCursorToColumn(currentExPosX);
	}
	
	private void resizeScreen(int row, int col) {
		this.terminalRow = row;
		this.terminalCol = col;
	}

	protected boolean getLoop() {
		return loop;
	}
	
	public static int getStatusHeight() {
		return statusHeight;
	}

	public static void endLoop() {
		loop = false;
	}
	
	public static int getTerminalRow() {
		return terminalRow;
	}
	
	public int getTerminalCol() {
		return terminalCol;
	}
	
	public static boolean canHandleFiles() {
		return canHandleFiles;
	}
	
	private static void cleanRow() {
		ANSI.moveCursorToColumn(0);
		for (int i = 0; i < terminalCol; i++) {
			System.out.print(" ");
		}
		ANSI.moveCursorToColumn(0);
	}

	public static void cleanTextArea() {
		int currentCol = cursor.getCol();
		int currentRow = cursor.getRow();
		
		ANSI.moveCursor(1, 1);
		for (int i = 0; i < terminalRow-statusHeight+1; i++) {
			cleanRow();
			ANSI.moveCursor(i+1, 0);
		}
		
		ANSI.moveCursor(currentRow, currentCol); // Restore original cursor position
	}

	public Mode getCurrentMode() {
		return mode;
	}
	
	//System.out.println("\033[4;44;31mHola\033[0m");
}
 