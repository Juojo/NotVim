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
		terminalRow = screen.getTerminalRow();
		terminalCol = screen.getTerminalCol();
	}
	
	public void handleMovementKeys(int charCode, int rowLenght, int amountOfRows) {
		
		if (mode != Mode.EX_MODE) {
			if (charCode == VK.ARROW_UP.getCode() && row > 1) {
				row--;
			} else if (charCode == VK.ARROW_DOWN.getCode() && row < terminalRow) {
				if (row < amountOfRows) row++;
			} else if (charCode == VK.ARROW_RIGHT.getCode() && col < terminalCol) {
				if (row == 1 && col <= rowLenght) col++;
				else if (row != 1 && col < rowLenght) col++;
			} else if (charCode == VK.ARROW_LEFT.getCode() && col > 1) {
				col--;
			}
			
			ANSI.moveCursor(row, col); // row, col
		} else {
			if (charCode == VK.ARROW_RIGHT.getCode() && col < terminalCol) {
				exCol++;
			} else if (charCode == VK.ARROW_LEFT.getCode() && col > 1) {
				exCol--;
			}
			
			ANSI.moveCursorToColumn(exCol);
		}
		
	}
	
	public void updatePosition() {
		ANSI.moveCursor(this.row, this.col);
	}
	
	// Move and set cursor
	
	public void moveSet(int col, int row) {
		this.col = col;
		this.row = row;
		
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
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getExCol() {
		return exCol;
	}
	
	// Update screen attributes
	
	public void updateMode(Mode newMode) {
		mode = newMode;
	}
	
	public void updateTerminalRow(int newTerminalRow) {
		terminalRow = newTerminalRow;
	}
	
	public void updateTerminalCol(int newTerminalCol) {
		terminalCol = newTerminalCol;
	}
	
}
