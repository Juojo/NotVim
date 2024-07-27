package com.juojo.util;

public enum Alerts {

	COMMAND_NOT_FOUND("Command not found", null, Colors.RED, Colors.DEFAULT);
	
	private String name, desc;
	private Colors fg, bg;
	
	Alerts(String name, String desc, Colors fg, Colors bg) {
		this.name = name;
		this.desc = desc;
		this.fg = fg;
		this.bg = bg;
	}
	
	public void newAlert() {
		String text = Util.returnColorString(name, fg, bg);
		System.out.print(text);
	}
	
	public static void newCustomAlert(String name, String desc, Colors fg, Colors bg) {
		String text;
		
		if (desc == null) {
			text = name;
		} else {
			text = name + " " + desc;
		}
		
		System.out.print(Util.returnColorString(text, fg, bg));
	}
	
}
