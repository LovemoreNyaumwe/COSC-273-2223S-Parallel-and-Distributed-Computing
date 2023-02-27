/**
 * Description: This program tests the effect of multithreading on the
 * performance of the PiEstimator class, which computes an estimate of
 * the mathematical constant pi (= 3.14159...).
 * 
 * @author Will Rosenbaum
 */

public class PiTester {
    // powers of two that are close to powers of 10
    public static final long THOUSAND = 1_024;
    public static final long MILLION = 1_048_576;
    public static final long BILLION = 1_073_741_824;

    
    // array of thread counts to test performance
    public static final int[] THREAD_COUNTS = {1, 2, 4, 8, 16, 32, 64, 128, 256};

    // the total number of samples to be collected
    public static final long NUM_POINTS = BILLION;
    
    public static void main (String[] args) {
	System.out.println("Running Monte Carlo simulation with n = " + NUM_POINTS + " samples...\n");
	System.out.println( "n threads | pi estimate | time (ms)\n"
			   +"-----------------------------------");

	// start and end times of computation
	long start, end;

	// test
	for (int n : THREAD_COUNTS) {
	    
	    PiEstimator pe = new PiEstimator(NUM_POINTS, n);
	    
	    start = System.nanoTime();
	    
	    double est = pe.getPiEstimate();
	    
	    end = System.nanoTime();

	    System.out.printf("%9d |     %.5f | %6d\n", n, est, (end - start) / 1_000_000);    
	}

	System.out.println("-----------------------------------");
    }


	
}
