package com.juojo.commands;

import com.juojo.util.Alerts;

public class CreateCommandInstance {
	
	private int maxArgs = 1;
	
	private String[] args = new String[maxArgs];
	private String[] inputArray;
	
	public CreateCommandInstance(String input) {
		initArray(args);
		inputArray = null;
		
		if (input == null || input.equals("")) {
			Alerts.COMMAND_NOT_FOUND.newAlert();
		} else {
			inputArray = input.split(" ");
			String command = inputArray[0];

			// Assign all args from input array
			for (int i = 0; i < args.length; i++) {
				try {
					args[i] = inputArray[i+1];
				} catch (Exception e) {
					args[i] = null; // inputArray[i+1] doesn't exists
				}
			}
			
			if (command.equals("q") || command.equals("quit")) new Quit();
			else if (command.equals("open")) new Open(args);
			//else if (command.equals("w") || command.equals("write")) new Write();
			else Alerts.COMMAND_NOT_FOUND.newAlert();
		}
	}

	private void initArray(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = null;
		}
	}
	
}
