package org.example;

import com.sun.jna.Native;
import com.sun.jna.Library;

public interface LibC extends Library {

	int SYSYTEM_OUT_FD = 0;
	int ISIG = 1, ICANON = 2, ECHO = 10, TCSAFLUSH = 2, IXON = 2000, ICRNL = 400, IEXTEN = 100000, OPOST = 1, VMIN = 6, VTIME = 5, TIOCGWINSZ = 0x5413;
	
	LibC INSTANCE = Native.load("c", LibC.class);
	
	int tcgetattr(int fd, Termios termios);
	
	int tcsetattr(int fd, int optional_actions, Termios termios);
	
}

