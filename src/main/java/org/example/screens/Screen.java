package org.example.screens;

import java.io.IOException;

import org.example.util.Colors;
import org.example.util.Util;

public abstract class Screen {

	
	private int row, col;
	private int statusHeight = 2;
	private boolean loop = false;
	
	protected int firstChar;
	
	private Mode mode;
	
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
				
				// Handle special characters and re assign NORMAL MODE if ESC key pressed
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
				
				// Handle custom binds if pressed after 27:
				// Enter EX MODE if second char is ":" and if mode is NORMAL MODE
				if (secondChar == 58 && mode == Mode.NORMAL_MODE) {
					changeMode(Mode.EX_MODE);
					System.out.print(returnColorString("Enter command:", Colors.BLUE.getFgColor()));
				}
				
				firstChar = 0; // set first char to ASCII NULL to avoid print and exit cycle
			} else {
				firstChar = 27;
			}
		}
		
		// Handle custom binds:
		// In any mode
		if (firstChar == 113) { // q
			loop = false;
		} else if (firstChar == 13 || firstChar == 10) { // Enter
			System.out.print("\n");
		}
		
		// In NORMAL MODE
		if (mode == Mode.NORMAL_MODE) {
			if (firstChar == 105) {// i
				changeMode(Mode.INSERT_MODE);
			}
		}
		
	}
	
	private void changeMode(Mode mode) {
		if (this.mode != mode) {
			this.mode = mode;
			printStatusBar();
		}
	}

	protected boolean getLoop() {
		return loop;
	}
	
	protected void printStatusBar() {
		Util.saveCursorPosition();
		Util.moveCursor(row+1-statusHeight, 0); // Move cursor to status-bar position
		
		// Print status-bar
		System.out.print("NotVim text editor\r\n"); // Make this look nicer
		System.out.print("{ " + returnColorString(mode.getName(), Colors.WHITE.getFgColor()) + " }");
		
		Util.restoreCursorPosition();
	}
	
	private void resizeScreen(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	
	protected String returnColorString(String content, String color) {
        return ("\033[" + ";" + ";" + color + "m" + content + "\033[0m");
    }

	protected int getStatusHeight() {
		return statusHeight;
	}
	
	//System.out.println("\033[4;44;31mHola\033[0m");
}
 