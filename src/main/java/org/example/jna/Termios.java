package org.example.jna;

import java.util.Arrays;
import com.sun.jna.Structure;

@Structure.FieldOrder(value = {"c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc"})
public class Termios extends Structure {

	public int c_iflag;      /* input modes */
	public int c_oflag;      /* output modes */
	public int c_cflag;      /* control modes */
	public int c_lflag;      /* local modes */
    public byte[] c_cc = new byte[19];   /* special characters */

    public Termios() {
    	
    }
    
    public static Termios of(Termios t) {
    	Termios copy = new Termios();
    	copy.c_iflag = t.c_iflag;
    	copy.c_oflag = t.c_oflag;
    	copy.c_cflag = t.c_cflag;
    	copy.c_lflag = t.c_lflag;
    	copy.c_cc = t.c_cc.clone();
    	return copy;
    }

	@Override
	public String toString() {
		return "Termios [c_iflag=" + c_iflag + ", c_oflag=" + c_oflag + ", c_cflag=" + c_cflag + ", c_lflag=" + c_lflag
				+ ", c_cc=" + Arrays.toString(c_cc) + "]";
	}
    
    
    
}
