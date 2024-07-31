package com.juojo.virtualkeymapping;

public enum VK {

	ARROW_UP(-10),
	ARROW_DOWN(-11),
	ARROW_RIGHT(-12),
	ARROW_LEFT(-13),
	F1(-20),
	F2(-21);
	
	private final int virtualCode;
	
	VK(int virtualCode) {
		this.virtualCode = virtualCode;
	}
	
	public int getCode() {
		return virtualCode;
	}
	
}
