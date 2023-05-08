/*
 * HammingWeight.java: compute the Hamming weight of the binary
 * representation of integers! Here the *Hamming weight* is the number
 * of 1s in the representation. This program supplies two methods for
 * computing Hamming weights: a simple "vanilla" implementation
 * (vanillaHW), and a vectorized implementation (vectorHW).
 *
 * The program uses the Java vector API. To compile and run the
 * program use:
 *
 * > javac --add-modules jdk.incubator.vector HammingWeight.java
 * > java --add-modules jdk.incubator.vector HammingWeight
 */

import jdk.incubator.vector.*;

public class HammingWeight {
    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;
    public static final int SIZE = 1 << 10;
    public static final int INT_LENGTH = 8 * Integer.BYTES;
    public static final int WARMUP = 100;
    public static final int NUM_TESTS = 1 << 12;

    // compute the Hamming weight of each a[i] and store it to b[i]
    public static void vanillaHW(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            int val = a[i];
            int weight = 0;
            int idx = 1;
//            for (int j = 0; j < INT_LENGTH; j++) {
//                if ((val & idx) != 0) {
//                    weight++;
//                }
//                idx = idx << 1;
//            }
            weight = bitCount(a[i]);
            b[i] = weight;
        }
    }

    public static int bitCount(int x) { // each of the 5 steps below adds neighboring bits together in groups of 1, 2, 4, 8, 16.

        int c;

        c = (x & 0x55555555) + ((x >> 1) & 0x55555555);
        c = (c & 0x33333333) + ((c >> 2) & 0x33333333);
        c = (c & 0x0F0F0F0F) + ((c >> 4) & 0x0F0F0F0F);
        c = (c & 0x00FF00FF) + ((c >> 8) & 0x00FF00FF);
        c = (c & 0x0000FFFF) + ((c >> 16)& 0x0000FFFF);

        return c;
    }

    // compute the Hamming weight of each a[i] and store it to b[i]
    // using vectorized operations
    public static void vectorHW(int[] a, int[] b) {
        int step = SPECIES.length();
        int bound = SPECIES.loopBound(a.length);

        int i = 0;
        for (; i < bound; i += step) {
            var va = IntVector.fromArray(SPECIES, a, i);
            var vb = IntVector.broadcast(SPECIES, 0);
            int idx = 1;
            for (int j = 0; j < INT_LENGTH; j++) {
                var bitMask = va.and(idx).eq(0).not();
                vb = vb.add(1, bitMask);
                idx = idx << 1;
            }
            vb.intoArray(b, i);
        }

        for (; i < a.length; i++) {
            int val = a[i];
            int weight = 0;
            int idx = 1;
            for (int j = 0; j < INT_LENGTH; j++) {
                if ((val & idx) != 0) {
                    weight++;
                }
                idx = idx << 1;
            }
            b[i] = weight;
        }
    }

    public static void main(String[] args) {
        int[] a = new int[SIZE];
        int[] b = new int[SIZE];
        int[] c = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            a[i] = i;
        }

        //////////////////////////////////////////////////
        // performance test for vanillaHW
        //////////////////////////////////////////////////

        for (int i = 0; i < WARMUP; i++) {
            vanillaHW(a, b);
        }

        long start = System.nanoTime();

        for (int i = 0; i < NUM_TESTS; i++) {
            vanillaHW(a, b);
        }

        long elapsed = System.nanoTime() - start;

        System.out.println("vanilla time: " + elapsed / 1_000_000 + "ms");

        //////////////////////////////////////////////////
        // performance test for vectorHW
        //////////////////////////////////////////////////

        for (int i = 0; i < WARMUP; i++) {
            vectorHW(a, c);
        }

        start = System.nanoTime();

        for (int i = 0; i < NUM_TESTS; i++) {
            vectorHW(a, c);
        }

        elapsed = System.nanoTime() - start;

        System.out.println("vector time: " + elapsed / 1_000_000 + "ms");

        //////////////////////////////////////////////////
        // check that the outputs agree
        //////////////////////////////////////////////////


        for (int i = 0; i < SIZE; i++) {
            if (b[i] != c[i]) {
                System.out.println("the arrays are not equal!");
                return;
            }
        }

        System.out.println("the arrays are equal!");
    }
}
