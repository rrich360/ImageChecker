package code;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//The main method calls the 'compareImages' method'to check if image 2 is a subset of image 1.
public class Main {
	public static void main(String[] args) throws IOException {
		ImageChecker i = new ImageChecker();
		BufferedImage one = ImageIO.read(new File("Pictures/460249177.jpg"));
		BufferedImage two = ImageIO.read(new File("Pictures/168680522.jpg"));
		if(one.getWidth() + one.getHeight() >= two.getWidth() + two.getHeight()) {
			i.setOne(one);
			i.setTwo(two);
		} else {
			i.setOne(two);
			i.setTwo(one);
		}
		System.out.println(i.compareImages());
	}
}
