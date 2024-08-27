package com.juojo.util;

public enum Alerts {

	COMMAND_NOT_FOUND("Command not found", null, Colors.RED, Colors.MAGENTA),
	FILE_NOT_FOUND("Can't open file", "The file was not found, this may be caused if you don't have the right permissions.", Colors.RED, Colors.MAGENTA),
	NO_SUCH_FILE("Can't open file", "The file does not exists.", Colors.RED, Colors.MAGENTA),
	CANT_OPEN_FILE("Can't open file", "The active screen isn't capable of handling files.", Colors.RED, Colors.MAGENTA),
	FILE_DONT_SPECIFIED("The file name hasn't been specified or it was deleted", "Please provide a file name when calling the command", Colors.RED, Colors.MAGENTA);
	
	private String name, desc;
	private Colors fg, bg;
	private static boolean activeAlert = false;
	
	Alerts(String name, String desc, Colors fg, Colors bg) {
		this.name = name;
		this.desc = desc;
		this.fg = fg;
		this.bg = bg;
	}
	
	public void newAlert() {
		ANSI.saveCursorPosition();
		ANSI.moveCursor(Util.getTerminalRow(), 0);
		
		printAlert(this.name, this.desc, this.fg, this.bg);
		
		ANSI.restoreCursorPosition();
	}
	
	public static void newCustomAlert(String name, String desc, Colors fg, Colors bg) {
		ANSI.saveCursorPosition();
		ANSI.moveCursor(Util.getTerminalRow(), 0);
		
		printAlert(name, desc, fg, bg);
		
		ANSI.restoreCursorPosition();
	}
	
	private static void printAlert(String name, String desc, Colors fg, Colors bg) {
		String text = "";
		
		if (name == null || name == "") {
			text = "";
		} else {
			
			if (desc == null || desc == "") {
				text = name;
			} else {
				text = name + ": " + desc;
			}
			
		}
		
		activeAlert = true;
		System.out.print(Util.returnColorString(text, fg, bg));
	}
	
	public static boolean getActiveAlert() {
		return activeAlert;
	}
	
	public static void setActiveAlertFalse() {
		activeAlert = false;
	}
	
	public static void setActiveAlertTrue() {
		// Only use this when need to print without creating a new alert
		activeAlert = true;
	}
	
}
