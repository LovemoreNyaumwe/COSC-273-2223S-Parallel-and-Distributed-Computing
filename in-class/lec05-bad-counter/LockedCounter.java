public class LockedCounter {
    long count = 0;
    boolean locked = false;
    
    public long getCount () { return count; }
    public void increment () {
	count++;
    }
    public void reset () { count = 0; }
    public void lock (int id) {
	// System.out.println("Thread " + id + " attempting to acquire lock.");
	while (locked) {
	    // just wait until unlocked
	    // System.out.println("Thread " + id + " waiting...");
	}
	
	// System.out.println("Thread " + id + " acquiring lock...");
	locked = true;
	// System.out.println("Thread " + id + " lock acquired.");
    }
    public void unlock () { locked = false; }
    public boolean isLocked () { return locked; }
}
