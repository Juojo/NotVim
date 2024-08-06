package com.juojo.util;

public abstract class Util {

	public static String returnColorString(String content, Colors fgColor, Colors bgColor) {
		if (bgColor == null || bgColor == Colors.DEFAULT) {
			return ("\033[" + ";" + ";" + fgColor.getFgCode() + "m" + content + "\033[0m");
		} else {
			return ("\033[" + fgColor.getFgCode() + ";" + bgColor.getBgCode() + "m" + content + "\033[0m");
		}
    }
	
}
