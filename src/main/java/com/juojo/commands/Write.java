package com.juojo.commands;

import com.juojo.screens.Screen;

public class Write extends Command {

	private static final String name = "Write";
	private static final String desc = "Write files in the directory from where the program was executed.";
	
	private String[] args;
	private Screen executedFromScreen;
	
	protected Write(String[] args, Screen executedFromScreen) {
		super(name, desc);
		this.args = args;
		this.executedFromScreen = executedFromScreen;
		
		executeCommand();
	}
	
	@Override
	protected void executeCommand() {
		executedFromScreen.writeData(args[0]);
	}
	
}
