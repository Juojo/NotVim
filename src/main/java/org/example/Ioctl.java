package org.example;

import com.sun.jna.Structure;

@Structure.FieldOrder(value = {"ws_row", "ws_col", "ws_xpixel", "ws_ypixel"})
public class Ioctl extends Structure {

	public short ws_row, ws_col, ws_xpixel, ws_ypixel;

	public int getWsRow() {
		return ws_row;
	} 
	
	public int getWsCol() {
		return ws_col;
	}
}
