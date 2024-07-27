package com.juojo.util;

public enum Colors {

	DEFAULT("", ""), // or 39, 49
    RED("31", "41"),
    GREEN("32", "42"),
	WHITE("97", "47"),
	BLACK("90", "100"),
	BLUE("34", "104"),
	MAGENTA("95", "105");

    private final String fg;
    private final String bg;

    Colors(String fg, String bg) {
        this.fg = fg;
        this.bg = bg;
    }
    
    public String getFgCode() {
    	return fg;
    }
    
    public String getBgCode() {
    	return bg;
    }

}
