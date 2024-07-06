package org.example.screens;

import java.io.IOException;

import org.example.util.Util;

public abstract class Screen {

	
	private int row, col;
	private int statusHeight = 1;
	private boolean loop = false;
	
	protected int firstChar;
	
	
	public Screen(int row, int col) {
		this.loop = true;
		resizeScreen(row, col);
	}
	
	protected void handleKey() throws IOException {
		int secondChar = 0, thirdChar = 0;
		firstChar = System.in.read();
		
		
		if (firstChar == 113) {
			loop = false;
		} else if (firstChar == 13) {
			System.out.print("\n");
		}
		
//		if (firstChar == 27) {
//			secondChar = System.in.read();
//			if (secondChar == 91) { // arrow keys
//				if (firstChar == 65) System.out.print("arrow up");
//				if (firstChar == 66) System.out.print("arrow down");
//				if (firstChar == 67) System.out.print("arrow right");
//				if (firstChar == 68) System.out.print("arrow left");
//			} else if (secondChar == 79) {	// function key
//				 if (firstChar == 80) System.out.print("F1");
//				 if (firstChar == 81) System.out.print("F2");
//			} else if (secondChar == 0) {
//				System.out.print("esc");
//			}
//		}
		
		// Read all posible chars
		if (firstChar == 27) {
			secondChar = System.in.read();
			if (secondChar == 0) System.out.print("esc");
		}
		
		if (firstChar == 27 && secondChar == 91) { // Arrow keys
			thirdChar = System.in.read();
			if (thirdChar == 65) System.out.print("arrow up");
			if (thirdChar == 66) System.out.print("arrow down");
			if (thirdChar == 67) System.out.print("arrow right");
			if (thirdChar == 68) System.out.print("arrow left");
		} else if (firstChar == 27 && secondChar == 79) { // Function keys
			thirdChar = System.in.read();
			if (thirdChar == 80) System.out.print("F1");
			if (thirdChar == 81) System.out.print("F2");
		}
		
		if (firstChar == 27) firstChar = 0;
	}
	
	protected boolean getLoop() {
		return loop;
	}
	
	protected void printStatusBar() {
		System.out.print("NotVim text editor"); // Make this look nicer
	}
	
	private void resizeScreen(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	
	protected String printCustomString(String content, String color) {
        return ("\033[" + ";" + ";" + color + "m" + content + "\033[0m");
    }

	protected int getStatusHeight() {
		return statusHeight;
	}
	
	//System.out.println("\033[4;44;31mHola\033[0m");
}
 