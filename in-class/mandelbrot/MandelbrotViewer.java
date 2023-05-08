import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class MandelbrotViewer extends JPanel{
    public static final int BOX_WIDTH = 1024;
    public static final int BOX_HEIGHT = 1024;

    // the region of the complex plane we want to draw

    // the whole set
    private double xmin = -2;
    private double xmax = 1;
    private double ymin = -1.5;
    private double ymax = 1.5;

    // elephant valley, detail:
    // private double xmin = 0.25;
    // private double xmax = 0.35;
    // private double ymin = -0.06;
    // private double ymax = 0.04;

    // sea horse valley, detail:
    // private double xmin = -1.0;
    // private double xmax = -0.5;
    // private double ymin = -0.1;
    // private double ymax = 0.4;

    

    private Color[][] bitmap = new Color[BOX_WIDTH][BOX_HEIGHT];


    public MandelbrotViewer(){
        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
    }
    
    private ComplexNumber getValueFromIndices(int i, int j) {
	double re = (xmax - xmin) * ((double) i / BOX_WIDTH) + xmin;
	double im = (ymax - ymin) * ((double) (BOX_HEIGHT - j) / BOX_HEIGHT) + ymin;
	
	return new ComplexNumber(re, im);
    }

    private Color colorMap (float val) {	
	float aVal = (float) ((Math.exp(val) - 1)  / (Math.E - 1));
	float r = 0; 
	float g = 0;
	float b = 0;

	b = Math.min(aVal / 0.5F, 1.0F);
	if (aVal > 0.5)
	    r = Math.min((aVal - 0.5F) / 0.25F, 1.0F);

	if (aVal > 0.75)
	    g = (aVal - 0.75F) / 0.25F;

	
	return new Color(r, g, b);
    }

    private Color grayscaleColorMap (float val) {
	float aVal = (float) ((Math.exp(val) - 1)  / (Math.E - 1));
	return new Color (aVal, aVal, aVal);
    }

    // iterate over the pixels of the image and determine the color
    // for each pixel
    private void updateBitmap () {    
	for (int i = 0; i < BOX_WIDTH; i++) {
	    for (int j = 0; j < BOX_HEIGHT; j++) {		
		ComplexNumber c = getValueFromIndices(i, j);
		float val = Mandelbrot.getValue(c);
		bitmap[i][j] = colorMap(val);
	    }
	}	      
    }
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

	System.out.println("Updating bitmap...");
	long startTime = System.nanoTime();
	
	updateBitmap();
	
	long endTime = System.nanoTime();
	System.out.println("That took " + (endTime - startTime) / 1_000_000 + " ms.");

	for (int i = 0; i < BOX_WIDTH; i++) {
	    for (int j = 0; j < BOX_HEIGHT; j++) {
		g.setColor(bitmap[i][j]);
		g.fillRect(i, j, 1, 1);
	    }
	}
    }
    
    public static void main(String args[]){
        JFrame frame = new JFrame("Mandelbrot Set!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MandelbrotViewer());
        frame.pack();
        frame.setVisible(true);
    }
}
