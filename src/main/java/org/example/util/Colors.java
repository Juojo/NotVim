package org.example.util;

public enum Colors {

	DEFAULT(""),
    RED("31"),
	WHITE("97"),
	BLUE("34");

    private final String color;

    Colors(String color) {
        this.color = color;
    }
    
    public String getColor() {
    	return color;
    }

}
