package org.example.screens;

public abstract class Screen {

	private int row, col;
	private int statusHeight = 1;
	
	
	public Screen(int row, int col) {
		resizeScreen(row, col);
	}
	
	private void cleanScreen() {
		
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
 