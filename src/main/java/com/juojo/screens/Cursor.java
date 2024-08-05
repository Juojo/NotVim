package com.juojo.screens;

import com.juojo.util.ANSI;
import com.juojo.virtualkeymapping.VK;

public class Cursor {

	private int col = 1;
	private int row = 1;
	private int exCol = 1;
	
	private Screen screen;
	private Mode mode;
	private int terminalRow, terminalCol;
	
	public Cursor(Screen screen) {
		this.screen = screen;
		mode = screen.getCurrentMode();
		terminalRow = screen.getRow();
		terminalCol = screen.getCol();
	}
	
	public void handleMovementKeys(int charCode) {
		
		if (mode != Mode.EX_MODE) {
			if      (charCode == VK.ARROW_UP.getCode()    && row > 1)   row--;
			else if (charCode == VK.ARROW_DOWN.getCode()  && row < row) row++;
			else if (charCode == VK.ARROW_RIGHT.getCode() && col < col) col++;
			else if (charCode == VK.ARROW_LEFT.getCode()  && col > 1)   col--;
			
			ANSI.moveCursor(row, col); // row, col
		} else {
			if      (charCode == VK.ARROW_RIGHT.getCode() && col < col) exCol++;
			else if (charCode == VK.ARROW_LEFT.getCode()  && col > 1)   exCol--;
			
			ANSI.moveCursorToColumn(exCol);
		}
		
	}
	
	// Move and set cursor
	
	public void moveSet(int x, int y) {
		this.col = x;
		this.row = y;
		
		ANSI.moveCursor(this.row, this.col);
	}
	
	public void moveSetCol(int col) {
		this.col = col;
		
		ANSI.moveCursor(this.row, this.col);
	}
	
	public void moveSetRow(int row) {
		this.row = row;
		
		ANSI.moveCursor(this.row, this.col);
	}
	
	// Set row and col
	
	public void setCol(int coor) {
		col = coor;
	}
	
	public void setRow(int coor) {
		row = coor;
	}
	
	public void setExCol(int coor) {
		exCol = coor;
	}
	
	// Increment row and col
	
	public void incrementCol(int amount) {
		col += amount;
	}
	
	public void incrementRow(int amount) {
		row += amount;
	}
	
	public void incrementExCol(int amount) {
		exCol += amount;
	}
	
	// Get row and col
	
	public int[] getRowCol() {
		int[] output = new int[2];
		output[0] = row;
		output[1] = col;
		
		return output;
	}
	
	// Update screen attributes
	
	public void updateMode(Mode newMode) {
		mode = newMode;
	}
	
	public void updateTerminalRow(int newTerminalRow) {
		terminalRow = newTerminalRow;
	}
	
	public void updateCol(int newTerminalCol) {
		terminalCol = newTerminalCol;
	}
	
}
