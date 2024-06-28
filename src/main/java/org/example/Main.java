package org.example;

import org.example.util.Colors;

public class Main {
    public static void main(String[] args) {

//        System.out.println("Hola");
//        System.out.println("\033[4;44;31mHola\033[0m");

        System.out.println(escArroundString("funcion", Colors.DEFAULT.getColor()));

    }

    private static String escArroundString(String content, String color) {
        return ("\033[" + ";" + ";" + color + "m" + content + "\033[0m");
    }
}