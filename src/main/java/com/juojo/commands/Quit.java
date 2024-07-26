package com.juojo.commands;

import com.juojo.screens.Screen;

public class Quit extends Command {

	private static final String name = "Quit";
	private static final String desc = "Exit program.";
	//private static final String[] options = {":q", ":quit"};
	
	protected Quit() {
		super(name, desc);
	}
	
	@Override
	protected void executeCommand() {
		//System.out.print("Command " + name + " executed succesfully."); // Replace this with new Alert();
		Screen.endLoop();
	}

}
