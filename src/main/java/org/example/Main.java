package org.example;

import org.example.screens.InputViewer;
import org.example.screens.Screen;

public class Main {
	
	public static RawMode rawMode = new RawMode();
	public static Screen inputViewer;
	
    public static void main(String[] args) {
    	rawMode.enable();
    	inputViewer = new InputViewer(40, 30);
    	rawMode.disable();
    }

    
}