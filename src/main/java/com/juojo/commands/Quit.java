package com.juojo.commands;

public class Quit extends Command {

	private static final String name = "Quit";
	private static final String desc = "Exit program.";
	private static final String[] options = new String[2];
	
	public Quit() {
		super(name, desc);
		setOptions(options);
	}

	@Override
	protected void initializeOptions() {
		options[0] = ":q";
		options[1] = ":quit";
	}
	
	@Override
	protected void executeCommand() {
		System.out.println("Command " + name + " executed succesfully.");
	}

}
