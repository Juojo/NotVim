package org.example.screens;

import java.io.IOException;

import org.example.util.Colors;

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
		
		if (firstChar == 113) { // q
			loop = false;
		} else if (firstChar == 13 || firstChar == 10) { // Enter
			System.out.print("\n");
		}
		
		if (firstChar == 27) {
			secondChar = System.in.read();
			
			if (secondChar == 91) {
				thirdChar = System.in.read();
				if (thirdChar == 65) System.out.print("arrow up");
				if (thirdChar == 66) System.out.print("arrow down");
				if (thirdChar == 67) System.out.print("arrow right");
				if (thirdChar == 68) System.out.print("arrow left");
			} else if (secondChar == 79) {
				thirdChar = System.in.read();
				if (thirdChar == 80) System.out.print("F1");
				if (thirdChar == 81) System.out.print("F2");
			} else if (secondChar == 58) {
				System.out.print(returnColorString("Enter command:", Colors.BLUE.getColor()));
				// Enter NORMAL mode
			} else {
				// Enter NORMAL mode
			}
			
			firstChar = 0; // set first char to ASCII NULL to avoid print
		}
		
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
	
	
	protected String returnColorString(String content, String color) {
        return ("\033[" + ";" + ";" + color + "m" + content + "\033[0m");
    }

	protected int getStatusHeight() {
		return statusHeight;
	}
	
	//System.out.println("\033[4;44;31mHola\033[0m");
}
 