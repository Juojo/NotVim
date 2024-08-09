package com.juojo.screens;

import java.io.FileNotFoundException;
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

	//private String fileName = "file";
	
	private List<String> data;
	private Path path;

	public Data() {
		data = new ArrayList<>();
	}
	
	public void readPrint(Path path) {
		this.path = path;
		
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

	public void insert(char key, int row, int col) {
		
		/* 
		 This is probably the worst possible approach. Every time the user enters a new key this method is called.
		 Creating a completely new array for the line that is being edited just to add a new char. This result in
		 tons of memory usage, which can be improved if a different data structure is implemented. See:
		 https://www.averylaird.com/programming/the%20text%20editor/2017/09/30/the-piece-table
		 for more info about data structures in text editors.
		*/
		
		char[] currentLine;
		
		if (!data.isEmpty()) currentLine = new char[1];
		else currentLine = data.get(row).toCharArray();
		
		char[] buffer = new char[currentLine.length+1];
		
		for (int i = 0; i < buffer.length; i++) {
			if (i < col-1) {
				// The loop didn't reach the cursor position yet.
				// Assign existing chars to the new arr (buffer).
				buffer[i] = currentLine[i];
			} else {
				if (i == col-1) {
					// The loop is at the cursor position.
					// Add the new key to the buffer.
					buffer[i] = key;
				} else  {
					// The loop is now after cursor position.
					// Keep assigning existing chars to the buffer.
					buffer[i+1] = currentLine[i]; 
				}
			}
		}
		
		data.set(row-1, buffer.toString());
		updateLine(row-1);
	}
	
	public void write(Path path) {
		this.path = path;
		
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
	
	private void updateLine(int line) {
		ANSI.saveCursorPosition();
		
		System.out.print(data.get(line));
		ANSI.deleteEndOfRow();
		
		ANSI.restoreCursorPosition();
	}
	
}
