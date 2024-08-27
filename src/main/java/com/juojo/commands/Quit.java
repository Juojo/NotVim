package com.juojo.commands;

import com.juojo.screens.Screen;

public class Quit extends Command {

	private static final String name = "Quit";
	private static final String desc = "Exit program.";
	
	private Screen executedFromScreen;
	
	protected Quit(Screen executedFromScreen) {
		super(name, desc);
		this.executedFromScreen = executedFromScreen;
		
		executeCommand();
	}
	
	@Override
	protected void executeCommand() {
		executedFromScreen.endLoop();
	}

}
