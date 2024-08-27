package com.juojo.util;

public abstract class ANSI {

	public static void clearScreen() {
		System.out.print("\033[2J");
	}

	public static void moveCursor(int row, int col) {
		System.out.printf("\033[%d;%dH", row, col);
	}
	
	public static void moveCursorHome() {
		System.out.print("\033[H");
	}

	public static void moveCursorToColumn(int position) {
		System.out.printf("\033[%dG", position);		
	}

	public static void deleteEndOfRow() {
		System.out.print("\033[K");
		
	}
	
	public static void saveHideCursorPosition() {
		// Save cursor position in DEC and SCO
		System.out.print("\033 7\033[s");		
		// Hide the cursor
		hideCursor();
	}
	
	public static void restoreShowCursorPosition() {
		// Restore original cursor position in DEC and SCO
		System.out.print("\033 8\033[u");
		// Hide the cursor
		showCursor();
	}

	public static void moveCursorDown(int amountOfLines) {
		System.out.printf("\033[%dB", amountOfLines);		
	}
	
	public static void hideCursor() {
		System.out.print("\033[?25l");
	}
	
	public static void showCursor() {
		System.out.print("\033[?25h");
	}
	
}
