package org.example.screens;

import java.io.IOException;

import org.example.util.Colors;

public class InputViewer extends Screen {

	public InputViewer(int row, int col) {
		super(row, col);
		
		readPrintKeyCodes();
	}

	private void readPrintKeyCodes() {
		int key = 0;
        
        while ((int) key != 113) { // 113 == "q"
			try {
				key = System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//System.out.print("Char: " + (char) key + " Int: " + (int) key + "\r\n");
			System.out.printf("%s %s %s %d\r\n", printCustomString("Char:", Colors.RED.getColor()), (char) key, printCustomString("Int:", Colors.RED.getColor()), (int) key);
		}
		
	}

}
