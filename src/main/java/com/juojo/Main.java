package com.juojo;

import com.juojo.jna.Ioctl;
import com.juojo.jna.UseC;
import com.juojo.screens.Screen;
import com.juojo.screens.TextViewer;
import com.juojo.util.Util;

public class Main {
	
	public static UseC C = new UseC();
	
	public static Ioctl ioctl;
	public static int rows, columns;
	
	public static Screen inputViewer;
	public static Screen textViewer;
	
    public static void main(String[] args) {
    	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            C.disableRawMode();
        }));
    	
    	C.enableRawMode();
    	setRowsAndColumns();
    	
    	//inputViewer = new InputViewer(40, 30);
    	textViewer = new TextViewer(rows, columns);
    	
    	C.disableRawMode();
    	Util.clearScreen();
    	Util.moveCursorHome();
    }

	public static void setRowsAndColumns() {
		ioctl = C.getTermianlSize();
		rows = ioctl.ws_row;
		columns = ioctl.ws_col;
	}

    
}