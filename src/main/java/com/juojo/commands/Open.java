package com.juojo.commands;

import java.nio.file.Path;

import com.juojo.screens.Screen;
import com.juojo.util.Alerts;

public class Open extends Command {

	private static final String name = "Open";
	private static final String desc = "Open files.";
	
	private String[] args;
	private Screen executedFromScreen;
	
	protected Open(String[] args, Screen executedFromScreen) {
		super(name, desc);
		this.args = args;
		this.executedFromScreen = executedFromScreen;
		
		if (executedFromScreen.canHandleFiles()) {
			executeCommand();
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
		}		
	}

	@Override
	protected void executeCommand() {
		if (args[0] == null) {
			Alerts.FILE_DONT_SPECIFIED.newAlert();
			return;
		}
		
		Path path = Path.of(args[0]);
		executedFromScreen.readPrintData(path);
	}

}
