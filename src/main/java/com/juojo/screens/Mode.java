package com.juojo.screens;

public enum Mode {

	INSERT_MODE("INSERT MODE"),
	NORMAL_MODE("NORMAL MODE"),
	EX_MODE("EX MODE"),
	VISUAL_MODE("VISUAL MODE");
	
	private String name;
	
	Mode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
