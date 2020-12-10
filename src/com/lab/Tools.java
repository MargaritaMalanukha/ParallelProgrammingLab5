package com.lab;

public class Tools {

    public static void printArray(double [][] arrayToPrint) {
        for (int i = 0; i < Main.dim; i++) {
            for (int j = 0; j < Main.dim; j++) {
                System.out.print(arrayToPrint[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printArray(double [] arrayToPrint) {
        for (int i = 0; i < Main.dim; i++) {
            System.out.print(arrayToPrint[i] + " ");
        }
        System.out.println();
    }
}
