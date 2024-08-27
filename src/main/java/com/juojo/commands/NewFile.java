package com.juojo.commands;

import com.juojo.screens.Screen;
import com.juojo.util.Alerts;

public class NewFile extends Command {

	private static final String name = "New File";
	private static final String desc = "Creates a new file, it can be passed a file name through args.";
	
	private Screen executedFromScreen;
	
	protected NewFile(Screen executedFromScreen) {
		super(name, desc);
		this.executedFromScreen = executedFromScreen;
		
		if (executedFromScreen.canHandleFiles()) {
			executeCommand();
		} else {
			Alerts.CANT_OPEN_FILE.newAlert();
		}
	}
	
	@Override
	protected void executeCommand() {
		executedFromScreen.newFile();
	}
	
}
