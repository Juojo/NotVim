package com.juojo.screens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.juojo.commands.CreateCommandInstance;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;
import com.juojo.virtualkeymapping.VK;

public abstract class Screen {

	
	private static int row, col;
	private int statusHeight = 2;
	private static boolean loop = false;
	
	protected int charCode;
	
	protected Mode mode;
	
	private List<Integer> charCodeList = new ArrayList<>(); // Array list for commands from EX_MODE
	private String userInput = "";
	
	protected int posX = 1, posY = 1;
	protected int exPosX = 1; // For EX_MODE cursor position
	
	public Screen(int row, int col) {
		mode = Mode.INSERT_MODE;
		this.loop = true;
		resizeScreen(row, col);
		Util.moveCursor(posX, posY); // Move cursor to initial position (0; 0)
	}
	
	protected void handleKey() throws IOException {
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
		moveCursor(charCode);
	}
	
	private void moveCursor(int charCode) {
		
		if (mode != Mode.EX_MODE) {
			if      (charCode == VK.ARROW_UP.getCode()    && posY > 1)   posY--;
			else if (charCode == VK.ARROW_DOWN.getCode()  && posY < row) posY++;
			else if (charCode == VK.ARROW_RIGHT.getCode() && posX < col) posX++;
			else if (charCode == VK.ARROW_LEFT.getCode()  && posX > 1)   posX--;
			
			Util.moveCursor(posY, posX); // row, col
		} else {
			if      (charCode == VK.ARROW_RIGHT.getCode() && posX < col) exPosX++;
			else if (charCode == VK.ARROW_LEFT.getCode()  && posX > 1)   exPosX--;
			
			Util.moveCursorToColumn(exPosX);
		}
		
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
			
			if (charCode == 13 || charCode == 10) { // Enter
				System.out.print("\n");
				posY++;
				posX=0;
			}
			
			break;
		}
		case EX_MODE: {
			
			if (charCode != 27) {
				if (charCode != 13 && charCode != 10) {
					charCodeList.add(charCode);
				} else {
					cleanRow();
					exPosX = 1;
					
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
		int currentX = posX;
		int currentY = posY;
		int currentExPosX = exPosX;
		
		if (this.mode != mode) {
			this.mode = mode;
			
			if (mode == Mode.INSERT_MODE || mode == Mode.VISUAL_MODE) {
				Alerts.setActiveAlertFalse();
				printStatusBar(false, currentX, currentY, currentExPosX);
			} else {
				printStatusBar(true, currentX, currentY, currentExPosX);
			}
				
			if (mode == Mode.EX_MODE) {
				Util.moveCursor(row, 0);
			} else {
				Util.moveCursor(currentY, currentX);
				
				// Make sure incomplete commands are cleaned
				charCodeList.clear();
				userInput = "";
			}
		}
		
		
	}
	
	protected void printStatusBar(boolean modeOnTitle, int currentX, int currentY, int currentExPosX) {
		Util.moveCursor(row+1-statusHeight, 0); // Move cursor to status-bar position

		// Print status-bar
		for (int i = 0; i < col; i++) {
			System.out.print(Util.returnColorString(" ", Colors.DEFAULT, Colors.RED));
		}
		Util.moveCursorToColumn(0);

		System.out.print(Util.returnColorString("NotVim text editor", Colors.WHITE, Colors.RED));
		
		if (modeOnTitle) { // Can replace this with if (mode == Mode.INSERT_MODE || mode == Mode.VISUAL_MODE)
			System.out.print(Util.returnColorString("  --  " + mode.getName() + "  --  ", Colors.BLUE, Colors.RED));
		}

		System.out.print("\r\n");
		if (Alerts.getActiveAlert() == false) { // Don't clean last row if an alert is active
			cleanRow();
		}
		
		if (mode == Mode.INSERT_MODE) System.out.print(Util.returnColorString("-- ", Colors.WHITE, Colors.DEFAULT) + Util.returnColorString(mode.getName(), Colors.RED, Colors.DEFAULT) + Util.returnColorString(" --", Colors.WHITE, Colors.DEFAULT));
		
		if (mode != Mode.EX_MODE) Util.moveCursor(currentY, currentX);
		else Util.moveCursorToColumn(currentExPosX);
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
	
	public static int getRow() {
		return row;
	}
	
	//System.out.println("\033[4;44;31mHola\033[0m");
}
 