package org.example.screens;

import java.io.IOException;

import org.example.util.Util;

public abstract class Screen {

	
	private int row, col;
	private int statusHeight = 1;
	private boolean loop = false;
	
	protected int lastKey;
	
	
	public Screen(int row, int col) {
		this.loop = true;
		resizeScreen(row, col);
		Util.clearScreen();
	}
	
	protected void handleKey() {
		try {
			lastKey = System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (lastKey == 113) {
			loop = false;
		} else if (lastKey == 13) {
			System.out.print("\n");
		}
	}
	
	protected boolean getLoop() {
		return loop;
	}
	
	protected void printStatusBar() {
		System.out.print("NotVim text editor"); // Make this look nicer
	}
	
	private void resizeScreen(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	
	protected String printCustomString(String content, String color) {
        return ("\033[" + ";" + ";" + color + "m" + content + "\033[0m");
    }

	protected int getStatusHeight() {
		return statusHeight;
	}
	
	//System.out.println("\033[4;44;31mHola\033[0m");
}
 