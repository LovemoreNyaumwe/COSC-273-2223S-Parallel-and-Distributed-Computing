/*
 * AdderTester tests the efficiency of adding an array of
 * floats. Specifically, it compares the efficiency of adding the
 * floats sequentially compared to adding them in random order.
 */

public class AdderTester {
    public static final int STEP = 1_000;
    public static final int START = STEP;
    public static final int MAX = 10_000;
    
    private static void testAdd(int size) {
	LocalAdder la = new LocalAdder(size);

	long linearStart, linearElapsed, randomStart, randomElapsed;

	// measure time to add in sequential order
	linearStart = System.nanoTime();
	float linearSum = la.linearSum();
	linearElapsed = System.nanoTime() - linearStart;

	// measure time to add in random order
	randomStart = System.nanoTime();
	float randomSum = la.randomSum();
	randomElapsed = System.nanoTime() - randomStart;

	System.out.printf("%10d |     %6d    |     %6d    |  %.3f\n", size, linearElapsed / 1_000, randomElapsed / 1_000, ((float) randomElapsed)/linearElapsed);
    }
    
    public static void main(String[] args) {
	System.out.println( "    size   | t linear (us) | t random (us) |  ratio\n"
			   +"-----------+---------------+---------------+--------");
	
	for (int i = START; i <= MAX; i += STEP) {
	    testAdd(i);
	}
    }
}
