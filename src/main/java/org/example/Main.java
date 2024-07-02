package org.example;

import java.io.IOException;

import org.example.util.Colors;

public class Main {
	
    public static void main(String[] args) {

//        System.out.println("\033[4;44;31mHola\033[0m");

//        System.out.println(printCustomString("Hola", Colors.RED.getColor()));
//      
    	new RawTerminalViewer();
    	
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

    private static String printCustomString(String content, String color) {
        return ("\033[" + ";" + ";" + color + "m" + content + "\033[0m");
    }
}