package com.juojo.commands;

import java.nio.file.Path;

import com.juojo.screens.Data;

public class Open extends Command {

	private static final String name = "Open";
	private static final String desc = "Open test file.";
	
	private String[] args;
	
	protected Open(String[] args) {
		super(name, desc);
		this.args = args;
		
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
		
		Data data = new Data();
		data.readPrint(path);
	}

}
