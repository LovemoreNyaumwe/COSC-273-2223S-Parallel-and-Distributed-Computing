public class MatrixTask implements Runnable {
    private float[][] matrix;
    private float[][] shortcuts;
    private int i;
    private int j;

    public MatrixTask (float[][] matrix, float[][] shortcuts, int i, int j) {
	this.matrix = matrix;
	this.shortcuts = shortcuts;
	this.i = i;
	this.j = j;
    }

    public void run () {
	int size = matrix.length;
	
	
	float min = Float.MAX_VALUE;	     

	for (int k = 0; k < size; ++k) {
	    float x = matrix[i][k];
	    float y = matrix[k][j];
	    float z = x + y;

	    if (z < min) {
		min = z;
	    }
	}
		
	shortcuts[i][j] = min;
    }
}
