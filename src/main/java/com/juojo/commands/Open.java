package com.juojo.commands;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.juojo.screens.Screen;
import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class Open extends Command {

	private static final String name = "Open";
	private static final String desc = "Open test file.";
	
	private String[] args;
	private Path path;
	
	protected Open(String[] args) {
		super(name, desc);
		this.args = args;
		
		executeCommand();
	}

	@Override
	protected void executeCommand() {
		List<String> data = List.of();
	
		if (args[0] == null) {
			path = Path.of("testFile");
		} else {
			path = Path.of(args[0]);
		}
		
		if (Screen.canHandleFiles()) { // Verify that the screen is capable of handling files.
			try {
				Screen.cursor.moveSet(1, 1);
				
				Stream<String> stream = Files.lines(path);
				data = stream.toList();
				
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

}
