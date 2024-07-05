package org.example.util;

public abstract class Util {

	public static void clearScreen() {
		System.out.print("\033[2J");
	}

	public static void moveCursorHome() {
		System.out.print("\033[H");
	}
	
}
