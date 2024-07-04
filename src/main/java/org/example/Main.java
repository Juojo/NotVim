package org.example;

import org.example.screens.Screen;
import org.example.screens.TextViewer;

public class Main {
	
	public static UseC C = new UseC();
	
	public static Ioctl ioctl;
	public static int rows, columns;
	
	public static Screen inputViewer;
	public static Screen textViewer;
	
    public static void main(String[] args) {
    	C.enableRawMode();
    	setRowsAndColumns();
    	
    	//inputViewer = new InputViewer(40, 30);
    	textViewer = new TextViewer(rows, columns);
    	
    	C.disableRawMode();
    }

	public static void setRowsAndColumns() {
		ioctl = C.getTermianlSize();
		rows = ioctl.getWsRow();
		columns = ioctl.getWsCol();
	}

    
}