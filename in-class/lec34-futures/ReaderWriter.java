import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderWriter {
    public static final int N_THREADS = Runtime.getRuntime().availableProcessors();
    public static final int N_TASKS = 20;
    
    
    public static void main(String[] args) {
	ExecutorService pool = Executors.newFixedThreadPool(N_THREADS);
	ArrayList<Callable<String>> tasks = new ArrayList<Callable<String>>(N_TASKS);
	for (int i = 0; i < N_TASKS; i++) {
	    tasks.add(i, new WritingTask(i, true));
	}

	try {
	    List<Future<String>> results = pool.invokeAll(tasks);
	    for (Future<String> result : results) {
		System.out.println(result.get());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	pool.shutdown();
    }
}

class WritingTask implements Callable<String> {
    public static final int MAX_DURATION = 1000;
    private int id;
    private int duration;
    private boolean printOutput;

    public WritingTask(int id, boolean printOutput) {
	this.id = id;
	this.duration = ThreadLocalRandom.current().nextInt(MAX_DURATION);
	this.printOutput = printOutput;
    }
    
    @Override
    public String call() {

	if (printOutput) {
	    System.out.println("Task " + id + " started. Waiting " + duration + "ms");
	}
	
	try {
	    Thread.currentThread().sleep(duration);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	if (printOutput) {
	    System.out.println("  Task " + id + " completed after " + duration + "ms");
	}
	
	return "Task " + id + "'s output processed.";
    }
}
