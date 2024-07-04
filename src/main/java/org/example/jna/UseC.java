package org.example.jna;

public class UseC {

	private Termios termios = new Termios();
	
	private int rc_tcgetattr = LibC.INSTANCE.tcgetattr(LibC.SYSYTEM_OUT_FD, termios);
	private Termios originalAttributes = Termios.of(termios); // Store original termios attributes to disable raw mode
	
	public void enableRawMode() {
		if (rc_tcgetattr != 0) {
			System.err.println("Error calling tcgetattr, return code: " + rc_tcgetattr);
			disableRawMode();
			System.exit(rc_tcgetattr);
		}
		
        setTermiosFlags();
	}
	
	public void disableRawMode() {
		LibC.INSTANCE.tcsetattr(LibC.SYSYTEM_OUT_FD, LibC.TCSAFLUSH, originalAttributes);
	}

	private void setTermiosFlags() {
		termios.c_lflag &= ~(LibC.ECHO | LibC.ICANON | LibC.IEXTEN | LibC.ISIG);
        termios.c_iflag &= ~(LibC.IXON | LibC.ICRNL);
        termios.c_oflag &= ~(LibC.OPOST);

//      termios.c_cc[LibC.VMIN] = 0;
//      termios.c_cc[LibC.VTIME] = 1;
		
        LibC.INSTANCE.tcsetattr(LibC.SYSYTEM_OUT_FD, LibC.TCSAFLUSH, termios);
	}
	
	public Ioctl getTermianlSize() {
		final Ioctl ioctl = new Ioctl();
		final int rc = LibC.INSTANCE.ioctl(LibC.SYSYTEM_OUT_FD, LibC.TIOCGWINSZ, ioctl);
		
		if (rc != 0) {
			System.err.println("Error calling ioctl, return code: " + rc);
			disableRawMode();
			System.exit(rc);
		}
		
		return ioctl;
	}
	
}
