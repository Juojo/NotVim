package com.juojo.util;

public enum Alerts {

	COMMAND_NOT_FOUND("Command not found", null, Colors.RED, Colors.MAGENTA),
	OPEN("Open file", null, Colors.RED, Colors.MAGENTA);
	
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
		String text = Util.returnColorString(name, fg, bg);
		
		activeAlert = true;
		System.out.print(text);
	}
	
	public static void newCustomAlert(String name, String desc, Colors fg, Colors bg) {
		String text;
		
		if (name == null || name == "") {
			text = "";
		} else {
			
			if (desc == null || desc == "") {
				text = name;
			} else {
				text = name + ". " + desc;
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
