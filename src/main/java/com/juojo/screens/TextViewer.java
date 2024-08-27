package com.juojo.screens;

import java.io.IOException;

import com.juojo.commands.CreateCommandInstance;
import com.juojo.util.ANSI;
import com.juojo.util.Alerts;
import com.juojo.util.Colors;
import com.juojo.util.Util;

public class TextViewer extends Screen {
	
	private static boolean canHandleFiles = true;
	
	public TextViewer(int row, int col, String[] args) {
		super(row, col, canHandleFiles);
		
		newFile();
		
		if (args.length != 0) {
			super.changeMode(Mode.NORMAL_MODE);
			new CreateCommandInstance("open " + args[0], this);
		}
		
		ANSI.moveCursorHome();
		
        while (super.getLoop()) {
			try {
				super.handleKey();
			} catch (IOException e) {
				Alerts.newCustomAlert("Error", e.toString(), Colors.RED, null);
			}
		}
	}

}
