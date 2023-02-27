import java.util.Random;

/*
 *  LocalAdder is a class that that generates a random array of floats
 *  of a prescribed size. It provides methods to sum the elements of
 *  the arrays in two ways: one by adding the elements sequentially,
 *  and the other by adding the elements in a random order.
 */

public class LocalAdder {
    private int size;
    private int[] linearIndex;
    private int[] randomIndex;
    private float[] values;

    public LocalAdder(int size) {
	this.size = size;
	Random r = new Random();
	
	linearIndex = new int[size];
	randomIndex = new int[size];
	values = new float[size];

	// initialize linearIndex and randomIndex so that linearIndex
	// is stores the values [0, 1, ..., size], and randomIndex
	// stores a random shuffling of the same values. This method
	// uses the "Fisher-Yates shuffle" to generate a random
	// permutation
	for (int i = 0; i < size; ++i) {
	    linearIndex[i] = i;
	    randomIndex[i] = i;

	    int s = r.nextInt(i+1);
	    int j = randomIndex[s];
	    randomIndex[s] = i;
	    randomIndex[i] = j;

	    values[i] = r.nextFloat();
	}
    }

    // add the elements of values in sequential order
    public float linearSum() {
	float total = 0;

	for (int i = 0; i < size; ++i) {
	    int idx = linearIndex[i];
	    total += values[idx];
	}

	return total;
    }

    // add the elements of values in random order according to the
    // random index array
    public float randomSum() {
	float total = 0;

	for (int i = 0; i < size; ++i) {
	    int idx = randomIndex[i];
	    total += values[idx];
	}

	return total;
    }
}
