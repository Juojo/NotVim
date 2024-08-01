package com.juojo.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.juojo.screens.Screen;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class Open extends Command {

	private static final String name = "Open";
	private static final String desc = "Open test file.";
	
	private String path;
	
	protected Open() {
		super(name, desc);
	}

	@Override
	protected void executeCommand() {
		path = "./testFile";
		String data = "";
		
		if (Screen.canHandleFiles()) { // Verify that the screen is capable of handling files.
			try {
				Screen.moveCursor(1, 1);
				Screen.cleanTextArea();
				
				File file = new File(path);
				Scanner fileReader = new Scanner(file);
				
				for (int i = 0; i < Screen.getRow()-Screen.getStatusHeight(); i++) {
					System.out.printf("%s\r\n", Util.returnColorString("~", Colors.BLUE, Colors.DEFAULT));
				}
				
				Screen.moveCursor(1, 1);
				
				while (fileReader.hasNextLine()) {
					data = fileReader.nextLine();
					System.out.print(data + "\n\r");
				}
				
				fileReader.close();
			} catch (FileNotFoundException e) {
				Alerts.FILE_NOT_FOUND.newAlert();
			} catch (Exception e) {
				Alerts.newCustomAlert("Exception not handled", e.toString(), Colors.RED, null);
				//e.printStackTrace();
			}
			
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
		}
	}

}
