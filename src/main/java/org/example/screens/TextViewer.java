package org.example.screens;

public class TextViewer extends Screen {

	public TextViewer(int row, int col) {
		super(row, col);
		
		for (int i = 0; i < row-super.getStatusHeight() ; i++) {
			System.out.print("~\r\n");
		}
	}

	
	
}
