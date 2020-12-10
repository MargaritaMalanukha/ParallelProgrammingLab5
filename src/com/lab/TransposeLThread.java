package com.lab;

public class TransposeLThread extends Thread {

    private final int startFromColumn;
    private final int endAtColumn;

    public TransposeLThread(int startFromColumn, int endAtColumn) {
        this.startFromColumn = startFromColumn;
        this.endAtColumn = endAtColumn;
    }

    public void run() {
        for (int i = 0; i < Main.dim; i++) {
            for (int j = startFromColumn; j < endAtColumn; j++) {
                Main.LT[i][j] = Main.L[j][i];
            }
        }
    }

}
