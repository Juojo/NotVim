package org.example;

import java.io.IOException;

import org.example.util.Colors;

public class Main {
    public static void main(String[] args) {

//        System.out.println("\033[4;44;31mHola\033[0m");

        System.out.println(printCustomString("Hola", Colors.RED.getColor()));
        
        int key = 0;
        
        while ((int) key != 113) { // 113 == "q"
			try {
				key = System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println((int) key);
		}

    }

    private static String printCustomString(String content, String color) {
        return ("\033[" + ";" + ";" + color + "m" + content + "\033[0m");
    }
}