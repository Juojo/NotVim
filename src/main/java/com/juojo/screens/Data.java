package com.juojo.screens;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class Data {

	//private String fileName = "file";
	
	private List<String> data = List.of();
	private Path path;
	private Stream<String> stream;

	public Data() {

	}
	
	public void print(Path path) {
		this.path = path;
		
		if (Screen.canHandleFiles()) { 
			try {
				stream = Files.lines(this.path);
				//data = stream.toList();
				data = stream.collect(Collectors.toCollection(ArrayList::new));
				
				Screen.cursor.moveSet(1, 1);
				
				for (int i = 0; i < Screen.getTerminalRow()-Screen.getStatusHeight(); i++) {
					if (i >= data.size()) {
						System.out.print(Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
					} else {
						System.out.print(data.get(i));
					}
					ANSI.deleteEndOfRow();
					System.out.print("\r\n");
				}
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
		
		String currentLine = data.get(row);
		String buffer = new StringBuilder(currentLine).insert(col, key).toString();
		
		data.set(row, buffer);
	}
	
	public void writeNewFile(Path path) {
		this.path = path;
		
	}
	
	public void writeExistingFile() {
		
	}
}
