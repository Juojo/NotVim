package com.juojo;

import com.juojo.jna.Ioctl;
import com.juojo.jna.UseC;
import com.juojo.screens.Screen;
import com.juojo.screens.TextViewer;
import com.juojo.util.ANSI;
import com.juojo.util.Util;

public class Main {
	
	public static UseC C = new UseC();
	
	public static Ioctl ioctl;
	public static int rows, columns;
	
	public static Screen inputViewer;
	public static Screen textViewer;
	
    public static void main(String[] args) {
    	// Disable raw mode if program crash (please don't crash)
    	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            C.disableRawMode();
        }));
    	
    	C.enableRawMode();
    	setRowsAndColumns();
    	
    	// Instantiate the main Screen of the program
    	textViewer = new TextViewer(rows, columns, args);
    	
    	C.disableRawMode();
    	ANSI.clearScreen();
    	ANSI.moveCursorHome();
    }

	public static void setRowsAndColumns() {
		ioctl = C.getTermianlSize();
		rows = ioctl.ws_row;
		columns = ioctl.ws_col;
		
		Util.setTerminalRow(rows);
		Util.setTerminalCol(columns);
	}
	
}