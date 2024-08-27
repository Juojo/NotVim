package com.juojo.screens.cursor;

import com.juojo.screens.Data;
import com.juojo.screens.Mode;
import com.juojo.util.ANSI;
import com.juojo.virtualkeymapping.VK;

public class Cursor {

	private int col = 1;
	private int row = 1;
	private int exCol = 2;
	
	private int maxColMem = 1;
	
	private Mode mode;
	private int terminalRow, terminalCol;
	
	public Cursor(int terminalRow, int terminalCol, Mode mode) {
		this.mode = mode;
		this.terminalRow = terminalRow;
		this.terminalCol = terminalCol;
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
				
				// reset maxColMem if the column position is changed
				maxColMem = col;
			} else if (charCode == VK.ARROW_LEFT.getCode()) {
				// ARROW LEFT
				if (col == 1 && row != 1) {
					row--;
					col = data.getRowLenght(row)+1;
				} else if (col > 1) { 
					col--;
				}
				
				// reset maxColMem if the column position is changed
				maxColMem = col;
			}

			ANSI.moveCursor(row, col);
		} else {
			if (charCode == VK.ARROW_RIGHT.getCode() && exCol < terminalCol) {
				if (exCol <= data.getCommandRowLenght()+1) {
					exCol++;
				}
			} else if (charCode == VK.ARROW_LEFT.getCode() && exCol > 2) {
				exCol--;
			}
			
			ANSI.moveCursorToColumn(exCol);
		}
		
	}
	
	private void handleColMemory(int rowLenght) {		
		// Assign maxColMem
		if (col > maxColMem) {
			maxColMem = col;
		}
		
		// Move cursor to maxColMem
		if (maxColMem > rowLenght) {			
			col = rowLenght+1;
		} else {
			col = maxColMem; 
		}
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
	
	public void incrementSetCol(int amount) {
		col += amount;
		
		ANSI.moveCursor(this.row, this.col);
	}
	
	public void incrementSetRow(int amount) {
		row += amount;
		
		ANSI.moveCursor(this.row, this.col);
	}
	
	public void incrementSetExCol(int amount) {
		exCol += amount;
		
		ANSI.moveCursor(this.terminalRow, this.exCol);
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
	
	public void setTerminalRow(int newTerminalRow) {
		terminalRow = newTerminalRow;
	}
	
	public void setTerminalCol(int newTerminalCol) {
		terminalCol = newTerminalCol;
	}
	
}
