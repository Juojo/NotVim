package com.juojo.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.juojo.Main;
import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;
import com.juojo.virtualkeymapping.VK;

public class Data {

	private List<String> data;
	private Path path;
	private File file;
	
	private String commandData = "";

	protected Data() {
		data = new ArrayList<>();
	}
	
	protected void readPrint(Path path) {
		this.path = path;
		this.file = new File(path.toString());
		
		if (Screen.canHandleFiles()) { 
			try (Stream<String> stream = Files.lines(this.path)) {
				data = stream.collect(Collectors.toCollection(ArrayList::new));
				printFile();				
			} catch (FileNotFoundException e) {
				Alerts.FILE_NOT_FOUND.newAlert();
			} catch (NoSuchFileException e) {
				Alerts.FILE_NOT_FOUND.newAlert();
			} catch (Exception e) {
				Alerts.newCustomAlert("Error", e.toString(), Colors.RED, null);
			}	
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
		}
	}

	protected void insert(char key, int row, int col) {		
		if (key == 13 || key == 10) return; // Don't insert \n or \r
		if (key == 127) return; // Don't insert delete
		
		/* 
		 Every time the user enters a new key this method is called. Creating a completely new array
		 for the line that is being edited just to add a new char. It can be improved if a different
		 data structure is implemented. See:
		 https://www.averylaird.com/programming/the%20text%20editor/2017/09/30/the-piece-table
		 for more info about data structures in text editors.
		*/
		
		try {
			// Remove position from data array if it has an EMPTY_LINE code
			// This will result in currentLine throwing a IndexOutOfBoundsException and handling the new insert like the first insert of the file
			if (isLineEmpty(row)) {
				char[] buffer = new char[1];
				buffer[0] = key;
				
				data.set(row-1, new String(buffer));
			} else {
				char[] currentLine = data.get(row-1).toCharArray();
				char[] buffer = new char[currentLine.length+1];
				
				for (int i = 0; i < buffer.length; i++) {
					if (i < col-1) {
						// The loop didn't reach the cursor position yet.
						// Assign existing chars to the new arr (buffer).
						buffer[i] = currentLine[i];
					} else if (i == col-1) {
						// The loop is at the cursor position.
						// Add the new key to the buffer.
						buffer[i] = key;
					} else if (i > col-1)  {
						// The loop is now after cursor position.
						// Keep assigning existing chars to the buffer.
						buffer[i] = currentLine[i-1];
					}
				}
				
				data.set(row-1, new String(buffer));
			}
		} catch (IndexOutOfBoundsException e) {
			// Add EMPTY_LINE to the first position of data if the user wants to input an enter as their first line on the file
			if (data.isEmpty() && row-1 == 1) {
				char[] firstEmpty = new char[1];
				firstEmpty[0] = (char) VK.EMPTY_LINE.getCode();
				data.add(0, new String(firstEmpty));
				updateLine(0);
			}
			
			char[] buffer = new char[1];
			buffer[0] = key;
			
			data.add(row-1, new String(buffer));
		}

		// Don't increment cursor position if the char inserted is EMPTY_LINE
		if (key != (char) VK.EMPTY_LINE.getCode()) {			
			Screen.cursor.incrementCol(1);
			//Screen.cursor.updatePosition();
		}
		updateLine(row-1);
	}
	
	protected void insertCommand(char key, int col) {
		if (key < 0) return; // Don't insert virtual key binds
		if (key == 13 || key == 10) return; // Don't insert \n or \r
		if (key == 127) return; // Don't insert delete
		if (key == 58) return; // Don't insert :
		
		try {
			char[] currentLine = commandData.toCharArray();
			char[] buffer = new char[currentLine.length+1];
			
			for (int i = 0; i < buffer.length; i++) {
				if (i < col-2) {
					// The loop didn't reach the cursor position yet.
					// Assign existing chars to the new arr (buffer).
					buffer[i] = currentLine[i];
				} else if (i == col-2) {
					// The loop is at the cursor position.
					// Add the new key to the buffer.
					buffer[i] = key;
				} else if (i > col-2) {
					// The loop is now after cursor position.
					// Keep assigning existing chars to the buffer.
					buffer[i] = currentLine[i-1];
				}
			}
			
			commandData = new String(buffer);
			updateCommandModeLine();
			Screen.cursor.incrementExCol(1);
		} catch (Exception e) {
			Alerts.newCustomAlert("Error inserting command data", e.toString(), Colors.RED, null);
			Screen.cursor.setExCol(1);
		}
	}
	
	protected void delete(int row, int col, com.juojo.screens.cursor.Cursor cursor) {
		if (data.isEmpty()) return;
		if (row == 1 && col == 1) return;
		
		String currentLine = data.get(row-1);
		
		if (col == 1) {
			cursor.moveSet(getRowLenght(row-1)+1, row-1);
			
			if (isLineEmpty(row-1)) {
				if (!isLineEmpty(row)) {
					data.set(row-2, currentLine);
				}
			} else {
				if (!isLineEmpty(row)) {
					data.set(row-2, data.get(row-2) + currentLine);
				}
			}
			
			data.remove(row-1);			
			updateAllLines();
		} else {
			String firstSegment = currentLine.substring(0, col-2);
			String secondSegment = currentLine.substring(col-1, currentLine.length());
			
			data.set(row-1, firstSegment+secondSegment);
			
			cursor.moveSet(col-1, row);
			
			updateLine(row-1);
		}
		
		// Clear data if the only value stored is EMPTY_LINE
		if (data.size() == 1 && isLineEmpty(1)) {
			data.clear();
			System.out.print(Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
		}
	}
	
	protected int deleteCommand(int col) {
		if (commandData.isEmpty() || col == 2) return col;
				
		String firstSegment = commandData.substring(0, col-3);
		String secondSegment = commandData.substring(col-2, commandData.length());
		
		commandData = firstSegment+secondSegment;
		updateCommandModeLine();
		
		return col-1;
	}
	
	protected void write(Path path) {		
		if (path != null) {
			// Check if the path provided is the same one stored in Data
			if (path != this.path) {
				// Save new path
				this.path = path;
				
				// Create file for the new path
				try {
					this.file = new File(this.path.toString());
					this.file.createNewFile();
				} catch (Exception e) {
					Alerts.newCustomAlert("Error creating file", e.toString(), Colors.RED, null);
				}
			}
			
			writeFile();
		} else {
			if (file == null || file.exists() == false) Alerts.FILE_DONT_SPECIFIED.newAlert();
			else writeFile();
		}
	}
	
	private void writeFile() {
		try {
			FileWriter writer = new FileWriter(this.path.toString());
			
			if (data.size() > 0) {
				for (int i = 0; i < data.size(); i++) {
					if (!isLineEmpty(i+1)) writer.write(data.get(i));

					writer.write(System.lineSeparator()); // Unix: \n | Windows: \r\n
				}
				
				Alerts.newCustomAlert("The file has been successfully written in", "'"+this.path.toString()+"'", Colors.GREEN, null);
			} else {
				Alerts.newCustomAlert("Error writing file", "There is no content to be written", Colors.RED, null);
			}
			
			writer.close();
		} catch (Exception e) {
			Alerts.newCustomAlert("Error writing file", e.toString(), Colors.RED, null);
		}		
	}
	
	private void printFile() {
		ANSI.moveCursorHome();
		
		for (int i = 0; i < Screen.getTerminalRow()-Screen.getStatusHeight(); i++) {
			if (i >= data.size()) {
				System.out.print(Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
			} else {
				System.out.print(data.get(i));
			}
			ANSI.deleteEndOfRow();
			System.out.print("\r\n");
		}
		
		Screen.cursor.moveSet(1, 1);
	}
	
	private boolean isLineEmpty(int line) {
		if (line-1 < 0) return true;
		else if (data.get(line-1).length() == 0) return true;
		else return data.get(line-1).toCharArray()[0] == (char) VK.EMPTY_LINE.getCode();
	}
	
	private void updateLine(int line) {
		ANSI.saveCursorPosition();
		
		ANSI.moveCursor(line+1, 1);
		if (!isLineEmpty(line+1)) System.out.print(data.get(line));
		ANSI.deleteEndOfRow();
		
		ANSI.restoreCursorPosition();
	}	
	
	private void updateAllLines() {
		ANSI.saveCursorPosition();
		
		ANSI.moveCursorHome();
		
		for (int i = 0; i < data.size(); i++) {
			ANSI.moveCursorToColumn(1);
			if (!isLineEmpty(i+1)) System.out.print(data.get(i));
			ANSI.deleteEndOfRow();
			ANSI.moveCursorDown(1);
		}
		
		// Delete last line
		ANSI.moveCursorToColumn(1);
		ANSI.deleteEndOfRow();
		System.out.print(Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
		
		ANSI.restoreCursorPosition();
	}
		
	protected void updateCommandModeLine() {
		ANSI.saveCursorPosition();
		
		ANSI.moveCursor(Main.rows, 1);
		System.out.print(":" + commandData);
		ANSI.deleteEndOfRow();
		
		ANSI.restoreCursorPosition();
	}
	
	public int getRowLenght(int row) {
		int lenght;
		
		if (data.isEmpty()) {
			lenght = 0;
		} else if (data.size() < row) {
			lenght = 0;
		} else if (isLineEmpty(row)) {
			lenght = 0;
		} else {
			lenght = data.get(row-1).length();
		}
		
		return lenght;
	}	

	public int getCommandRowLenght() {
		return commandData.length();
	}

	public int getAmountOfRows() {
		int amount;
		
		if (data.isEmpty()) {
			amount = 0;
		} else {
			amount = data.size();
		}
		
		return amount;
	}

	public void clearCommandData() {
		commandData = "";		
	}
	
	public String getCommandData() {
		return commandData;
	}
	
}
