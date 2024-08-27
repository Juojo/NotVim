package com.juojo.util;

import java.util.List;

public abstract class Util {

	private static int statusBarHeight, terminalRow, terminalCol;
	
	public static String returnColorString(String content, Colors fgColor, Colors bgColor) {
		if (bgColor == null || bgColor == Colors.DEFAULT) {
			return ("\033[" + ";" + ";" + fgColor.getFgCode() + "m" + content + "\033[0m");
		} else {
			return ("\033[" + fgColor.getFgCode() + ";" + bgColor.getBgCode() + "m" + content + "\033[0m");
		}
    }
	
	public static String charCodeListToString(List<Integer> charCodeList) {
		String output = "";
		int listLenght = charCodeList.size();
		
		for (int i = 0; i < listLenght; i++) {
			output += Character.toString(charCodeList.getFirst());
			charCodeList.removeFirst();
		}
		
		return output;
	}
	
	public static void printNotWritableRowSymbol() {
		System.out.print(Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));		
	}
	
	public static void setStatusBarHeight(int value) {
		statusBarHeight = value;
	}
	
	public static int getStatusBarHeight() {
		return statusBarHeight;
	}

	public static void setTerminalRow(int rows) {
		terminalRow = rows;		
	}
	
	public static int getTerminalRow() {
		return terminalRow;
	}

	public static void setTerminalCol(int columns) {
		terminalCol = columns;			
	}
	
	public static int getTerminalCol() {
		return terminalCol;
	}

}
