package com.juojo.commands;

import com.juojo.util.Alerts;
import com.juojo.util.Colors;

public class CreateCommandInstance {
	
	private String[] args = new String[1];
	
	public CreateCommandInstance(String input) {
		
		if (input == null || input.equals("")) {
			Alerts.COMMAND_NOT_FOUND.newAlert();
		} else {
			
			String[] inputString = input.split(" ");
			String command = inputString[0];
			
			try {
				args[0] = inputString[1];
			} catch (Exception e) {}
			
			if (command.equals("q") || command.equals("quit")) new Quit();
			else if (command.equals("open")) new Open(args);
			//else if (command.equals("w") || command.equals("write")) new Write();
			else Alerts.COMMAND_NOT_FOUND.newAlert();
		}

	}
	
}
