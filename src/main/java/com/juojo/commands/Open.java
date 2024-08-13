package com.juojo.commands;

import java.nio.file.Path;

import com.juojo.screens.Data;
import com.juojo.screens.Screen;

public class Open extends Command {

	private static final String name = "Open";
	private static final String desc = "Open test file.";
	
	private String[] args;
	private Screen executedFromScreen;
	
	protected Open(String[] args, Screen executedFromScreen) {
		super(name, desc);
		this.args = args;
		this.executedFromScreen = executedFromScreen;
		
		executeCommand();
	}

	@Override
	protected void executeCommand() {
		Path path;
		
		if (args[0] == null) {
			path = Path.of("testFile");
		} else {
			path = Path.of(args[0]);
		}
		
		executedFromScreen.readPrintData(path);
	}

}
