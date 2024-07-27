package com.juojo.util;

public abstract class Util {

	public static String returnColorString(String content, String fg, String bg) {
		if (bg == null || bg == "") {
			return ("\033[" + ";" + ";" + fg + "m" + content + "\033[0m");
		} else {
			return ("\033[" + fg + ";" + bg + "m" + content + "\033[0m");
		}
    }
	
	public static void clearScreen() {
		System.out.print("\033[2J");
	}

	public static void moveCursorHome() {
		System.out.print("\033[H");
	}

	public static void saveCursorPosition() {
		// Save cursor position in DEC and SCO
		System.out.print("\033 7\033[s");		
	}

	public static void moveCursor(int row, int col) {
		System.out.printf("\033[%d;%dH", row, col);
	}

	public static void restoreCursorPosition() {
		// Restore original cursor position in DEC and SCO
		System.out.print("\033 8\033[u");
	}

	public static void moveCursorToColumn(int position) {
		System.out.printf("\033[%dG", position);
		
	}
	
}
