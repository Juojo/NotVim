package com.juojo.commands;

import com.juojo.screens.Screen;
import com.juojo.util.Alerts;

public class CreateCommandInstance {
	
	private int maxArgs = 1;
	
	private String[] args = new String[maxArgs];
	private String[] inputArray;
	
	public CreateCommandInstance(String input, Screen executedFromScreen) {
		if (input == null || input.equals("")) {
			Alerts.COMMAND_NOT_FOUND.newAlert();
			return;
		}
		
		// Initialize arrays
		initArray(args);
		inputArray = null;
		
		// Assign command from input
		inputArray = input.split(" ");
		String command = inputArray[0];

		// Assign all args from inputArray
		for (int i = 0; i < args.length; i++) {
			try {
				args[i] = inputArray[i+1];
			} catch (Exception e) {
				args[i] = null; // inputArray[i+1] doesn't exists
			}
		}
		
		// Create command object
		if (command.equals("q") || command.equals("quit")) new Quit(executedFromScreen);
		else if (command.equals("open") && executedFromScreen.canHandleFiles()) new Open(args, executedFromScreen);
		else if (command.equals("w") || command.equals("write") && executedFromScreen.canHandleFiles()) new Write(args, executedFromScreen);
		else if (command.equals("n") || command.equals("new") && executedFromScreen.canHandleFiles()) new NewFile(executedFromScreen);
		else Alerts.COMMAND_NOT_FOUND.newAlert();
	}

	private void initArray(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = null;
		}
	}
	
}
