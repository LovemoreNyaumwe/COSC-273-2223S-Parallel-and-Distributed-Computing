import java.util.concurrent.ThreadLocalRandom;

public class PiThread implements Runnable {
    private int threadId;                           // thread id
    private long[] array;                      // array
    private long numPoints;
    public PiThread(int threadId, long[] array, long numPoints) {
        this.threadId = threadId;
        this.array = array;
        this.numPoints = numPoints;

    }

    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long count = array[threadId];

        for (int i = 0; i < numPoints; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1) {
                count++;
            }
        }

        array[threadId] = count;

    }
}
