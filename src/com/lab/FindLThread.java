package com.lab;

public class FindLThread extends Thread {

    private final int startFromRow;
    private final int endAtRow;

    public FindLThread(int startFromRow, int endAtRow) {
        this.startFromRow = startFromRow;
        this.endAtRow = endAtRow;
    }

    public void run() {
        for (int i = startFromRow; i < endAtRow; i++) {
            double temp;

            for (int j = 0; j < i; j++) {
                temp = 0;

                for (int k = 0; k < j; k++) //вычисляем сумму для формулы
                {
                    temp += Main.L[i][k] * Main.L[j][k];
                }
                Main.L[i][j] = (Main.A[i][j] - temp) / Main.L[j][j];
            }

            temp = Main.A[i][i];
            for (int k = 0; k < i; k++) {
                temp -= Main.L[i][k] * Main.L[i][k];
            }
            Main.L[i][i] = Math.sqrt(temp);
        }
    }

}
