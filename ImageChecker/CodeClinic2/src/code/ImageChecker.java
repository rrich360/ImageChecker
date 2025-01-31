package code;

import java.awt.image.BufferedImage;

public class ImageChecker {

	private BufferedImage one;
	private BufferedImage two;
	private double difference = 0;
	private int x = 0;
	private int y = 0;

	public ImageChecker() {

	}
//The for loops in this method choose every possible picture of the larger(original) image 
//in order as the upper left corner region to be compared. Then it compares the smaller image 
//to that same region in the larger image to see how similar they are. The integer f represents
//the 20 pixels being compared both horizontally and vertically.
	public boolean compareImages() {
		int f = 20;
		int w1 = Math.min(50, one.getWidth() - two.getWidth());
		int h1 = Math.min(50, one.getHeight() - two.getHeight());
		int w2 = Math.min(5, one.getWidth() - two.getWidth());
		int h2 = Math.min(5, one.getHeight() - two.getHeight());
		for (int i = 0; i <= one.getWidth() - two.getWidth(); i += f) {
			for (int j = 0; j <= one.getHeight() - two.getHeight(); j += f) {
				compareSubset(i, j, f);
			}
		}

		one = one.getSubimage(Math.max(0, x - w1), Math.max(0, y - h1),
				Math.min(two.getWidth() + w1, one.getWidth() - x + w1),
				Math.min(two.getHeight() + h1, one.getHeight() - y + h1));
		x = 0;
		y = 0;
		difference = 0;
		f = 5;
		for (int i = 0; i <= one.getWidth() - two.getWidth(); i += f) {
			for (int j = 0; j <= one.getHeight() - two.getHeight(); j += f) {
				compareSubset(i, j, f);
			}
		}
		one = one.getSubimage(Math.max(0, x - w2), Math.max(0, y - h2),
				Math.min(two.getWidth() + w2, one.getWidth() - x + w2),
				Math.min(two.getHeight() + h2, one.getHeight() - y + h2));
		f = 1;
		for (int i = 0; i <= one.getWidth() - two.getWidth(); i += f) {
			for (int j = 0; j <= one.getHeight() - two.getHeight(); j += f) {
				compareSubset(i, j, f);
			}
		}
		System.out.println(difference);
		return difference < 0.1;
	}
//The algorithm used in this method is called the least absolute deviation algorithm.
//The input for this class consists of three integers. Integers a & b represents the 
//starting coordinates of the subset image of the larger image. Integer f is used to 
//speed up the algorithm by defining what proportions of the pixels to actually compare.  
	public void compareSubset(int a, int b, int f) {
		double diff = 0;
		for (int i = 0; i < two.getWidth(); i += f) {
			for (int j = 0; j < two.getHeight(); j += f) {
//This portion of the method generates the difference factor in red,green,&blue values 
//for each of the corresponding pixels.It then combines all the results into a single 
//percent diff-factor.*Empirical specification*  
				int onepx = one.getRGB(i + a, j + b);
				int twopx = two.getRGB(i, j);
				int r1 = (onepx >> 16);
				int g1 = (onepx >> 8) & 0xff;
				int b1 = (onepx) & 0xff;
				int r2 = (twopx >> 16);
				int g2 = (twopx >> 8) & 0xff;
				int b2 = (twopx) & 0xff;
//This formula takes the absolute value of the differences of the two points between r, g, b values 
//the differences are added up for every pixel and then normalized to get percentage difference of 
//that region of the two images.       
				diff += (Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1
						- b2)) / 3.0 / 255.0;
			}
		}
//The if statement is looking for the best match in the results.Calibrates each match until the best
//match is found.
		double percentDiff = diff * f * f / (two.getWidth() * two.getHeight());
		if (percentDiff < difference || difference == 0) {
			difference = percentDiff;
			x = a;
			y = b;
		}
	}

	public BufferedImage getOne() {
		return one;
	}

	public void setOne(BufferedImage one) {
		this.one = one;
	}

	public BufferedImage getTwo() {
		return two;
	}

	public void setTwo(BufferedImage two) {
		this.two = two;
	}
}
