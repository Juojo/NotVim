package org.example.util;

public enum Colors {

	DEFAULT("", ""),
    RED("31", ""),
	WHITE("97", ""),
	BLUE("34", "104");

    private final String fg;
    private final String bg;

    Colors(String fg, String bg) {
        this.fg = fg;
        this.bg = bg;
    }
    
    public String getFgColor() {
    	return fg;
    }

}
