public class LockedCounterTester {
    public static int NUM_THREADS = 2;
    public static long TIMES_PER_THREAD = 100;

    public static void main (String[] args) {
	LockedCounter counter = new LockedCounter();
	Thread[] threads = new Thread[NUM_THREADS];
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new LockedThreadCount(counter, TIMES_PER_THREAD, i));
	}

	for (Thread t : threads)
	    t.start();

	for (Thread t : threads) {
	    try {
		t.join();
	    }
	    catch (InterruptedException e) {
	    }
	}

	System.out.println("Expected final count: " + NUM_THREADS * TIMES_PER_THREAD);
	System.out.println("Actual final count: " + counter.getCount());
    }
}
