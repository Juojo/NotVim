package com.juojo.commands;

import com.juojo.util.Alerts;
import com.juojo.util.Colors;

public class CreateCommandInstance {

	public CreateCommandInstance(String userInput) {
		
		if (userInput.equals("q") || userInput.equals("quit")) new Quit();
		else if (userInput.equals("open")) new Open();
		//else if (userInput.equals("w") || userInput.equals("write")) new Write();
		else Alerts.COMMAND_NOT_FOUND.newAlert();
		
	}
	
}
