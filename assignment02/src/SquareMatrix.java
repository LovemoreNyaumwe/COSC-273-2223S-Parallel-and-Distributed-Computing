//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Arrays;
import java.util.Random;

public class SquareMatrix {
    private float[][] matrix;

    public SquareMatrix(float[][] var1) {
        this.matrix = var1;
    }

    public SquareMatrix(int var1) {
        this.matrix = new float[var1][var1];
        Random var2 = new Random();

        for(int var3 = 0; var3 < var1; ++var3) {
            for(int var4 = 0; var4 < var1; ++var4) {
                if (var3 == var4) {
                    this.matrix[var3][var4] = 0.0F;
                } else {
                    this.matrix[var3][var4] = (float)var2.nextInt();
                }
            }
        }

    }

    public float[][] getMatrix() {
        return this.matrix;
    }

    public int getSize() {
        return this.matrix.length;
    }

    public float getEntry(int var1, int var2) {
        return 0 <= var1 && var1 < this.matrix.length && 0 <= var2 && var2 < this.matrix[var1].length ? this.matrix[var1][var2] : -1.0F;
    }

    public boolean equals(Object var1) {
        if (var1 == this) {
            return true;
        } else if (!(var1 instanceof SquareMatrix)) {
            return false;
        } else {
            SquareMatrix var2 = (SquareMatrix)var1;
            if (this.matrix.length != var2.matrix.length) {
                return false;
            } else {
                for(int var3 = 0; var3 < this.matrix.length; ++var3) {
                    if (this.matrix[var3].length != var2.matrix[var3].length) {
                        return false;
                    }

                    for(int var4 = 0; var4 < this.matrix[var3].length; ++var4) {
                        if (this.matrix[var3][var4] != var2.matrix[var3][var4]) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }
    }

    public SquareMatrix getShortcutMatrixBaseline() {
        int var1 = this.matrix.length;
        float[][] var2 = new float[var1][var1];

        for(int var3 = 0; var3 < var1; ++var3) {
            for(int var4 = 0; var4 < var1; ++var4) {
                float var5 = Float.MAX_VALUE;

                for(int var6 = 0; var6 < var1; ++var6) {
                    float var7 = this.matrix[var3][var6];
                    float var8 = this.matrix[var6][var4];
                    float var9 = var7 + var8;
                    if (var9 < var5) {
                        var5 = var9;
                    }
                }

                var2[var3][var4] = var5;
            }
        }

        return new SquareMatrix(var2);
    }

    public float[][] matrixTranspose(float[][] matrix2Transpose) {
        float[][] transposedMatrix = new float[matrix2Transpose.length][matrix2Transpose.length];

        for (int i = 0; i < matrix2Transpose.length; i++) {
            for (int j = 0; j < matrix2Transpose[i].length; j++) {
                transposedMatrix[j][i] = matrix2Transpose[i][j];
            }
        }

        return transposedMatrix;
    }

    public SquareMatrix getShortcutMatrixOptimized() {
        // the shared array with one index for each thread
        int numThreads = 2;

        int var1 = this.matrix.length;
        float[][] var2 = new float[var1][var1];

        float[][] rows = this.matrix;
        float[][] columns = matrixTranspose(this.matrix);

        // number of points per thread
        int numRowsPerThread = (var1 + numThreads - 1) / numThreads;

        // make an array of threads
        Thread[] threads = new Thread[numThreads];

        int start = 0;

        // initialize threads with a welcome message
        for (int i = 0; i < numThreads; i++) {
            int end = start + numRowsPerThread;
            if (end > var1) {
                threads[i] = new Thread(new ShortcutMatrixOptimizedThread(start, var1, rows, columns, var2));
            } else {
                threads[i] = new Thread(new ShortcutMatrixOptimizedThread(start, end, rows, columns, var2));
            }
            start+=numRowsPerThread;
        }

        // start all the threads
        for (Thread t : threads) {
            t.start();
        }

        // wait for all threads to complete
        for (Thread t : threads) {
            try {
                t.join();
            }
            catch (InterruptedException ignored) {
                // don't care if t was interrupted
            }
        }

        return new SquareMatrix(var2);
    }
}
