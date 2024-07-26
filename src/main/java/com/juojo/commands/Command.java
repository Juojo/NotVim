package com.juojo.commands;

public abstract class Command {

	private String name;
	private String desc;
	//private String[] options;

	protected Command(String name, String desc) {
		this.name = name;
		this.desc = desc;
		
		executeCommand();
	}

	protected abstract void executeCommand();
	
	public void printName() {
		System.out.print(name);
	}
	
	public void printDesc() {
		System.out.print(desc);
	}
	
//	public void printOptions() {
//		System.out.print("Options: ");
//		for (int i = 0; i < options.length; i++) {
//			System.out.print(options[i]);
//			if (i+1 != options.length) System.out.print(", ");
//		}
//	}
}

