package com.juojo.screens.cursor;

import com.juojo.screens.Data;
import com.juojo.screens.Mode;
import com.juojo.screens.Screen;
import com.juojo.util.ANSI;
import com.juojo.virtualkeymapping.VK;

public class Cursor {

	private int col = 1;
	private int row = 1;
	private int exCol = 1;
	
	private int maxCol = 1;
	
	private Screen screen;
	private Mode mode;
	private int terminalRow, terminalCol;
	
	public Cursor(Screen screen) {
		this.screen = screen;
		mode = screen.getCurrentMode();
		terminalRow = screen.getTerminalRow();
		terminalCol = screen.getTerminalCol();
	}
	
	public void handleMovementKeys(int charCode, Data data) {
		int amountOfRows = data.getAmountOfRows();
		
		if (mode != Mode.EX_MODE) {
			if (charCode == VK.ARROW_UP.getCode() && row > 1) {
				// ARROW UP
				row--;
				handleColMemory(data.getRowLenght(row));
			} else if (charCode == VK.ARROW_DOWN.getCode() && row < terminalRow) {
				// ARROW DOWN
				if (row < amountOfRows) {
					row++;
					handleColMemory(data.getRowLenght(row));
				}
			} else if (charCode == VK.ARROW_RIGHT.getCode() && col < terminalCol) {
				// ARROW RIGHT
				if (col == data.getRowLenght(row)+1 && amountOfRows > row) {
					row++;
					col = 1;
				} else {
					if (col <= data.getRowLenght(row)) col++;
				}
				
				// reset maxCol if the column position is changed
				maxCol = col;
			} else if (charCode == VK.ARROW_LEFT.getCode()) {
				// ARROW LEFT
				if (col == 1 && row != 1) {
					row--;
					col = data.getRowLenght(row)+1;
				} else if (col > 1) { 
					col--;
				}
				
				// reset maxCol if the column position is changed
				maxCol = col;
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
	
	private void handleColMemory(int rowLenght) {		
		// Assign maxCol
		if (col > maxCol) {
			maxCol = col;
		}
		
		// Move cursor to maxCol
		if (maxCol > rowLenght) {			
			col = rowLenght+1;
		} else {
			col = maxCol; 
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
