package com.lab;

public class FindXThread extends Thread {

    private final int startFromRow;
    private final int endAtRow;

    public FindXThread(int startFromRow, int endAtRow) {
        this.startFromRow = startFromRow;
        this.endAtRow = endAtRow;
    }

    public void run() {
        findLowerMatrix(); //матрица L нижняя, считаем сверху вниз
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        findUpperMatrix(); // матрица LT верхняя, считаем снизу вверх
    }

    private void findLowerMatrix() {
        double sum = 0.0;
        for (int i = startFromRow; i < endAtRow; i++) {
            if (i == 0) {
                Main.y[i] = Main.b[i] / Main.L[i][i];
            }
            else {
                for (int j = 0; j < i; j++) {
                    sum += (Main.L[i][j] * Main.y[j]);

                }
                Main.y[i] = (Main.b[i] - sum) / Main.L[i][i];
            }
        }
    }

    private void findUpperMatrix() {
        int startRow = Main.dim - startFromRow - 1;
        int endRow = Main.dim - endAtRow - 1;
        double sum = 0.0;
        for (int i = startRow; i > endRow; i--) {
            if (i == Main.dim-1)
                Main.x[i] = Main.y[i] / Main.LT[i][i];
            else {
                for (int j = i+1; j < Main.dim; j++) {
                    sum += (Main.LT[i][j] * Main.x[j]);
                }
                Main.x[i] = (Main.y[i] - sum) / Main.LT[i][i];
            }
        }
    }


}
