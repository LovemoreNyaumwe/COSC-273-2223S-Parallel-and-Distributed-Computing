public class LockedThreadCount implements Runnable {
    private LockedCounter counter;
    private long times;        // number of times to increment counter
    private int id;

    public LockedThreadCount (LockedCounter counter, long times, int id) {
	this.counter = counter;
	this.times = times;
	this.id = id;
    }

    public void run () {
	for (long i = 0; i < times; i++) {
	    // System.out.println("Thread " + id + " attempting to acquire lock.");
	    counter.lock(id);
	    try {
		// System.out.println("Thread " + id + " acquired locked.");
		counter.increment();
		// System.out.println("Thread " + id + " incremented counter.");
	    }
	    finally {
		// System.out.println("Thread " + id + " unlocking counter.");
		counter.unlock();
	    }
	    
	}
    }
}
