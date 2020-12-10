package com.lab;

public class Main {

    public static double [][] A = {
            { 5, 2, 3 },
            { 2, 6, 1 },
            { 3, 1, 7 }
    };
    public static double [] b = { 10, 20, 30 };
    public static int dim = b.length;
    public static double [][] L = new double[dim][dim];
    public static double [][] LT = new double[dim][dim];
    public static double [] y = new double[dim];
    public static double [] x = new double[dim];

    public static void main(String[] args) {
        try {
            ThreadRealisation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void ThreadRealisation() throws InterruptedException {
        //поначалу находим матрицу L, распараллеливая её с помощью FindLThread.

        int quantityOfThreads = 3; //оптимальное количество тредов для обработки
        int startRow = 0; //индекс начальной строки, которую принимает поток (включительно)
        int endRow; //индекс последней строки которую принимает поток (исключительно)
        int count = 0; //номер текущего потока.

        FindLThread [] findLThreadArray = new FindLThread[quantityOfThreads];

        int step = dim / quantityOfThreads; //количество строчек на один поток
        int lastStep = dim % quantityOfThreads; //остаток от деления передается последнему потоку

        endRow = step;

        while (true) { //распараллеливаем для нахождения L

            findLThreadArray[count] = new FindLThread(startRow, endRow);
            findLThreadArray[count].start();

            count++;
            if (count == quantityOfThreads) break;

            startRow += step;
            endRow += step;

            if (endRow > dim) endRow += lastStep - step; //если следующего шага нет, то обработать только остаток
            Thread.sleep(10);

        }

        for (int i = 0; i < quantityOfThreads; i++) {
            findLThreadArray[i].join();
        }



        /* затем ещё раз распараллеливаем и находим матрицу L^T (мы не можем это сделать в предыдущих потоках поскольку
        * там мы проходим по строчкам, а тут по столбикам). */

        //количество потоков также 3, ещё нам не нужно вычислять шаг, последний шаг и длину массива.
        int startColumn = 0;
        int endColumn = step;
        count = 0;

        TransposeLThread [] transposeLThreadArray = new TransposeLThread[quantityOfThreads];

        while (true) { //распараллеливаем для транспонирования матрицы
            transposeLThreadArray[count] = new TransposeLThread(startColumn, endColumn);
            transposeLThreadArray[count].start();

            count++;
            if (count == quantityOfThreads) break;

            startColumn += step;
            endColumn += step;

            if (endColumn > dim) endColumn += lastStep - step;
            Thread.sleep(10);
        }

        for (int i = 0; i < quantityOfThreads; i++) {
            transposeLThreadArray[i].join();
        }

        /* дальше мы по формулам Ly = b и Lx = y находим х, что и является решением */

        startRow = 0;
        endRow = step;

        count = 0;

        FindXThread [] findXThreadArray = new FindXThread[quantityOfThreads];

        while (true) {
            findXThreadArray[count] = new FindXThread(startRow, endRow);
            findXThreadArray[count].start();

            count++;
            if (count == quantityOfThreads) break;

            startRow += step;
            endRow += step;

            if (endRow > dim) endRow += lastStep - step;
            Thread.sleep(10);
        }

        for (int i = 0; i < quantityOfThreads; i++) {
            findXThreadArray[i].join();
        }

        Tools.printArray(y);
        Tools.printArray(x);


    }

}
