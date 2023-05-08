public class ComplexNumber {

    private double re;
    private double im;

    public ComplexNumber (double re, double im) {
	this.re = re;
	this.im = im;
    }

    public void setValue (double re, double im) {
	this.re = re;
	this.im = im;
    }
    
    public double getRe () { return re; }
    public double getIm () { return im; }
    
    public double modulus () { return Math.sqrt(re * re + im * im); }

    public ComplexNumber plus (ComplexNumber z) {
	return new ComplexNumber(re + z.re, im + z.im);
    }

    public ComplexNumber times (ComplexNumber z) {
	return new ComplexNumber(re * z.re - (im * z.im), re * z.im + z.re * im);
    }
}
