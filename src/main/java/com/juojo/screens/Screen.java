package com.juojo.screens;

import java.io.IOException;
import java.nio.file.Path;

import com.juojo.commands.CreateCommandInstance;
import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;
import com.juojo.virtualkeymapping.VK;

public abstract class Screen {
	
	private int terminalRow, terminalCol;
	private boolean canHandleFiles = false;
	
	private boolean loop = false;	
	private Mode mode;
	
	private Data data;
	private Cursor cursor;
		
	protected int charCode;
	
	private int statusBarHeight = 2;
	
	protected Screen(int terminalRow, int terminalCol, boolean canHandleFiles) {
		this.terminalRow = terminalRow;
		this.terminalCol = terminalCol;
		this.canHandleFiles = canHandleFiles;
		Util.setStatusBarHeight(statusBarHeight);
		
		this.loop = true;		
		mode = Mode.INSERT_MODE;
		
		cursor = new Cursor(terminalRow, terminalCol, mode);
		ANSI.moveCursorHome();
	}
	
	protected void handleKey() throws IOException {
		int secondChar = 0, thirdChar = 0;
		charCode = System.in.read();
		
		Mode originalMode;
		while (charCode == 27) {
			originalMode = mode;
			changeMode(Mode.NORMAL_MODE);
			
			secondChar = System.in.read();
			
			if (secondChar != 27) { // if 27 the ESC key has been pressed 2 times, restart cycle
				
				// Handle special characters
				if (secondChar == 91) {
					changeMode(originalMode);
					thirdChar = System.in.read();
					if (thirdChar == 65) charCode = VK.ARROW_UP.getCode();
					if (thirdChar == 66) charCode = VK.ARROW_DOWN.getCode();
					if (thirdChar == 67) charCode = VK.ARROW_RIGHT.getCode();
					if (thirdChar == 68) charCode = VK.ARROW_LEFT.getCode();
				} else if (secondChar == 79) {
					changeMode(originalMode);
					thirdChar = System.in.read();
					if (thirdChar == 80) charCode = VK.F1.getCode();
					if (thirdChar == 81) charCode = VK.F2.getCode();
				} else {
					charCode = secondChar;
				}
				
			} else {
				charCode = 27;
			}
		}
		
		if (charCode == 9) charCode = 0; // Disable TAB
		
		handleCustomBinds(mode, charCode);
		if (canHandleFiles) cursor.handleMovementKeys(charCode, data);
	}

	private void handleCustomBinds(Mode actualMode, int charCode) {		
		// Handle custom binds for each mode
		switch (actualMode) {
		case NORMAL_MODE: {
			
			if (charCode == 105) { // i
				changeMode(Mode.INSERT_MODE);
			} else if (charCode == 58) { // :
				Alerts.setActiveAlertFalse();
				
				// Restart command input field
				data.clearCommandData();
				changeMode(Mode.EX_MODE);
				cursor.setExCol(2);	
			}
			
			// u -> undo
			// d -> delete word						
			break;
		}
		case INSERT_MODE: {
			
			if (charCode == 13 || charCode == 10) { // Enter
				int actualCol = cursor.getCol();
				cursor.moveSet(1, cursor.getRow()+1);
				data.insert((char) VK.EMPTY_LINE.getCode(), cursor.getRow(), cursor.getCol());
				data.handleEnterBetweenText(cursor.getRow()-1, actualCol);
			} else if (charCode == 127) { // Delete
				data.delete(cursor.getRow(), cursor.getCol(), cursor);
			} else if (charCode >= 0) { // Not VK
				int updatedCol = data.insert((char) charCode, cursor.getRow(), cursor.getCol());
				cursor.moveSet(updatedCol, cursor.getRow());
			}
			
			break;
		}
		case EX_MODE: {
			
			if (charCode != 27) {
				if (charCode == 127) { // Delete
					cursor.setExCol(data.deleteCommand(cursor.getExCol()));
				} else if (charCode == 58) { // :
					// Restart command input field
					data.clearCommandData();
					data.updateCommandModeLine();
					cursor.setExCol(2);					
				} else {
					// If not enter
					if (charCode != 13 && charCode != 10) {
						// And not virtual key bind
						if (charCode > 0) {
							// Insert command and update cursor position
							int updatedExCol = data.insertCommand((char) charCode, cursor.getExCol());
							cursor.setExCol(updatedExCol);
						}
					} else { // If enter was pressed
						cursor.setExCol(2);
						ANSI.deleteEndOfRow();

						// Call the command that matches the input 
						new CreateCommandInstance(data.getCommandData(), this);
						
						changeMode(Mode.NORMAL_MODE);
						data.clearCommandData();
					}
				}
			}
			
			break;
		}
		case VISUAL_MODE: {
			
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + actualMode);
		}
		
	}

	protected void changeMode(Mode mode) {
		if (this.mode == mode) return; // Don't change mode to the same mode

		this.mode = mode;
		cursor.updateMode(mode);
		
		if (mode == Mode.INSERT_MODE || mode == Mode.VISUAL_MODE) {
			Alerts.setActiveAlertFalse();
		}
		
		printStatusBar();
		
		// Move cursor to the position required for each mode 
		if (mode == Mode.EX_MODE) {
			// Set cursor to last row
			ANSI.moveCursor(terminalRow, 0);
 
			data.updateCommandModeLine(); // Not ideal, consequence of #10 -> https://github.com/Juojo/NotVim/issues/10
		} else {
			cursor.moveSet(cursor.getCol(), cursor.getRow());
		}
	}
	
	protected void printStatusBar() {
		ANSI.saveHideCursorPosition();
		
		// Move cursor to status-bar position
		ANSI.moveCursor(terminalRow+1-statusBarHeight, 0);

		// Print status-bar
		for (int i = 0; i < terminalCol; i++) {
			System.out.print(Util.returnColorString(" ", Colors.DEFAULT, Colors.RED));
		}
		ANSI.moveCursorToColumn(0);

		System.out.print(Util.returnColorString("NotVim text editor", Colors.WHITE, Colors.RED));
		
		// Print mode on title if actual mode is:
		if (mode == Mode.NORMAL_MODE || mode == Mode.EX_MODE) {
			System.out.print(Util.returnColorString("  --  " + mode.getName() + "  --  ", Colors.BLUE, Colors.RED));
		}

		System.out.print("\r\n");
		
		// Clean command row (last row) only if there aren't any alerts active
		if (!Alerts.getActiveAlert()) {
			ANSI.moveCursorToColumn(0);
			ANSI.deleteEndOfRow();
		}
		
		// Print mode on command row if actual mode is:
		if (mode == Mode.INSERT_MODE || mode == Mode.VISUAL_MODE) {
			System.out.print(Util.returnColorString("-- ", Colors.WHITE, Colors.DEFAULT) + Util.returnColorString(mode.getName(), Colors.RED, Colors.DEFAULT) + Util.returnColorString(" --", Colors.WHITE, Colors.DEFAULT));
		}

		ANSI.restoreShowCursorPosition();
	}
	
	private void printHomeScreen() {
		for (int i = 0; i < Util.getTerminalRow()-Util.getStatusBarHeight(); i++) {
			Util.printNotWritableRowSymbol();
			System.out.print("\r\n");
		}
	}
	
	public void readPrintData(Path path) {
		if (canHandleFiles) {
			cursor.moveSet(1, 1);
			data = new Data();
			data.readPrint(path);
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
		}		
	}

	public void writeData(String fileName) {
		if (canHandleFiles) {
			if (fileName == null) data.write(null);
			else data.write(Path.of("./"+fileName));
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
		}
	}
	
	public void newFile() {
		if (canHandleFiles) {
			ANSI.clearScreen();
			cursor.moveSet(1, 1);
			printHomeScreen();
			printStatusBar();
			
			data = new Data();
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
		}
	}	
	
	protected boolean getLoop() {
		return loop;
	}

	public void endLoop() {
		loop = false;
	}
	
	public boolean canHandleFiles() {
		return canHandleFiles;
	}
	
}
 