package org.example;

import org.example.screens.Screen;
import org.example.screens.TextViewer;

public class Main {
	
	public static RawMode rawMode = new RawMode();
	public static Screen inputViewer;
	public static Screen textViewer;
	
    public static void main(String[] args) {
    	rawMode.enable();
    	//inputViewer = new InputViewer(40, 30);
    	textViewer = new TextViewer(40, 30);
    	rawMode.disable();
    }

    
}