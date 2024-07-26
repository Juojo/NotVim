package com.juojo.commands;

public class CreateCommandInstance {

	public CreateCommandInstance(String userInput) {
		
		if (userInput.equals("q") || userInput.equals("quit")) new Quit();
		//else if (userInput.equals("w") || userInput.equals("write")) new Write();
		//else new Alert("Command not found");
		
	}
	
}
