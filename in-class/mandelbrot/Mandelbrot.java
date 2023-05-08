public class Mandelbrot {
    public static final int MAX_ITER = 64;
    public static final double MAX_MODULUS = 100;
    
    public static float getValue (ComplexNumber c) {
	ComplexNumber z = new ComplexNumber(0, 0);
	int iter = 0;

	while (iter < MAX_ITER && z.modulus() <= MAX_MODULUS) {
	    z = z.times(z).plus(c);
	    iter++;
	}

	return (float) (MAX_ITER - iter) / MAX_ITER;
    }
}
