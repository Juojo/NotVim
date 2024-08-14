package com.juojo.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class Data {

	private List<String> data;
	private Path path;
	File file;

	protected Data() {
		data = new ArrayList<>();
	}
	
	protected void readPrint(Path path) {
		this.path = path;
		this.file = new File(path.toString());
		
		if (Screen.canHandleFiles()) { 
			try (Stream<String> stream = Files.lines(this.path)) {
				data = stream.toList();
				printFile();
			} catch (FileNotFoundException e) {
				Alerts.FILE_NOT_FOUND.newAlert();
			} catch (NoSuchFileException e) {
				Alerts.FILE_NOT_FOUND.newAlert();
			} catch (Exception e) {
				Alerts.newCustomAlert("Error", e.toString(), Colors.RED, null);
				//e.printStackTrace();
			}	
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
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

	protected void insert(char key, int row, int col) {
		
		/* 
		 This is probably the worst possible approach. Every time the user enters a new key this method is called.
		 Creating a completely new array for the line that is being edited just to add a new char.
		 It can be improved if a different data structure is implemented. See:
		 https://www.averylaird.com/programming/the%20text%20editor/2017/09/30/the-piece-table
		 for more info about data structures in text editors.
		*/
		
		try {
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
		} catch (Exception e) {
			char[] buffer = new char[1];
			buffer[0] = key;
			
			data.add(row-1, new String(buffer));
		}
		
		updateLine(row-1);
	}
	
	private void updateLine(int line) {
		ANSI.saveCursorPosition();
		
		ANSI.moveCursorToColumn(0);
		System.out.print(data.get(line));
		
		ANSI.restoreCursorPosition();
		
		Screen.cursor.incrementCol(1);
		Screen.cursor.updatePosition();
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
					writer.write(data.get(i));
					writer.write(System.lineSeparator()); // Unix: \n | Windows: \r\n
				}
				
				Alerts.newCustomAlert("The file has been successfully written in", "'"+this.path.toString()+"'", Colors.GREEN, null);
			} else {
				Alerts.newCustomAlert("Error writing file", "There is no content to be written", Colors.RED, null);
			}
			
			writer.close();
		} catch (IndexOutOfBoundsException e) {
			Alerts.newCustomAlert("Error writing file", "There is no content to be written", Colors.RED, null);
		} catch (Exception e) {
			Alerts.newCustomAlert("Error writing file", e.toString(), Colors.RED, null);
		}		
	}

	public int getRowLenght(int row) {
		int lenght;
		
		if (data.isEmpty()) {
			lenght = 0;
		} else {
			lenght = data.get(row-1).length();
		}
		
		return lenght;
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
	
}
