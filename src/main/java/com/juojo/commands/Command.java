package com.juojo.commands;

public abstract class Command {

	private String name;
	private String desc;

	protected Command(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	protected abstract void executeCommand();
	
	public void printName() {
		System.out.print(name);
	}
	
	public void printDesc() {
		System.out.print(desc);
	}

}

