
/**
 * Description: <describe your program here>
 *
 * @author <your name here>
 */

public class PiEstimator {
    // add any desired fields here
    private long numPoints;
    private int numThreads;

    // constructor taking in the number of sample points, numPoints, 
    // and the number of threads used to compute the estimate
    public PiEstimator (long numPoints, int numThreads) {
        this.numPoints = numPoints;
        this.numThreads = numThreads;
    }


    // compute the estimate of pi (improve this description!)
    public double getPiEstimate () {
        double estimate = (double) numPointsInCircle() / this.numPoints * 4;
        return estimate;
    }

    public long numPointsInCircle() {
        // the shared array with one index for each thread
        long[] shared = new long[this.numThreads];

        // number of points per thread
        long numPointsPerThread = this.numPoints / this.numThreads;

        // make an array of threads
        Thread[] threads = new Thread[this.numThreads];

        // initialize threads with a welcome message
        for (int i = 0; i < this.numThreads; i++) {
            threads[i] = new Thread(new PiThread(i, shared, numPointsPerThread));
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

        // print the contents of the array to the terminal
        return sumSharedCount(shared);
    }

    public long sumSharedCount(long[] shared) {
        long count = 0;
        for (long pointCount : shared) {
            count += pointCount;
        }
        return count;
    }
}