package com.juojo.commands;

public class Open extends Command {

	private static final String name = "Open";
	private static final String desc = "Open test file.";
	
	private String path;
	
	protected Open() {
		super(name, desc);
		path = "./testFile";
	}

	@Override
	protected void executeCommand() {
		
	}

}
