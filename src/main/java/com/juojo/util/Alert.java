package com.juojo.util;

public enum Alert {

	COMMAND_NOT_FOUND("Command not found.", null, Colors.RED, Colors.BLACK);
	
	private String name, desc;
	private Colors fg, bg;
	
	Alert(String name, String desc, Colors fg, Colors bg) {
		this.name = name;
		this.desc = desc;
	}
	
	public void newAlert() {
		String text = Util.returnColorString(name, fg, bg);
		//System.out.print();
	}
	
}
