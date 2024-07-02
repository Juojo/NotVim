package org.example;

public class RawTerminalViewer {

	Termios termios = new Termios();
	int returnCode = LibC.INSTANCE.tcgetattr(LibC.SYSYTEM_OUT_FD, termios);
	
	public RawTerminalViewer() {

		if (returnCode != 0) { // error
			System.err.println("Error calling tcgetattr");
			System.exit(returnCode);
		}
		
        setTermiosFlags();
	}

	private void setTermiosFlags() {
		termios.c_lflag &= ~(LibC.ECHO | LibC.ICANON | LibC.IEXTEN | LibC.ISIG);
        termios.c_iflag &= ~(LibC.IXON | LibC.ICRNL);
        termios.c_oflag &= ~(LibC.OPOST);

//      termios.c_cc[LibC.VMIN] = 0;
//      termios.c_cc[LibC.VTIME] = 1;
		
        LibC.INSTANCE.tcsetattr(LibC.SYSYTEM_OUT_FD, LibC.TCSAFLUSH, termios);
	}
	
}
