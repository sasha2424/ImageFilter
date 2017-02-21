/**
 * 
 * Worked with Jannick
 * 
 * 
 */


package filters;

import javax.swing.JOptionPane;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

public class Blur implements ImageFilter {

	// Attribute to store the modified image
	ImgProvider filteredImage;

	public void filter(ImgProvider ip) {

		// Grab the pixel information and put it into a 2D array
		short[][] r = ip.getRed();
		short[][] g = ip.getGreen();
		short[][] b = ip.getBlue();

		// Make variables for image height and width
		int height = r.length;
		int width = r[0].length;

		// Create a new array to store the modified image
		short[][] red = new short[height][width];
		short[][] green = new short[height][width];
		short[][] blue = new short[height][width];

		int blur = Integer.parseInt(JOptionPane.showInputDialog("enter blur constant"));
		int weight = Integer.parseInt(JOptionPane.showInputDialog("enter weight constant"));

		// Loop through the original image and store the modified
		// version in the newImage array
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				// flips the image horizontally by reversing the column
				// direction
				red[row][col] = getColorAverageInRange(row, col, weight, blur, r);
				green[row][col] = getColorAverageInRange(row, col, weight, blur, g);
				blue[row][col] = getColorAverageInRange(row, col, weight, blur, b);
			}
		}

		// Create a new ImgProvider and set the filtered image to our new image
		filteredImage = new ImgProvider();
		filteredImage.setColors(red, green, blue, ip.getAlpha());

		// Show the new image in a new window with title "Flipped Horizontally"
		filteredImage.showPix("Blurred");
	}

	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	// This is what users see in the Filter menu
	public String getMenuLabel() {
		return "Blur";
	}

	private short getColorAverageInRange(int x, int y, int weight, int r, short[][] im) {
		double F = 0;
		int counter = 0;
		for (int i = -1 * r / 2; i < r / 2; i++) {
			for (int j = -1 * r / 2; j < r / 2; j++) {
				if ((x + i > 0 && x + i < im.length) && (y + j > 0 && y + j < im[0].length)) {
					if (i == 0 && j == 0) {
						F += ((double) im[x + i][y + j])*weight;
						counter += weight;
					} else {
						F += ((double) im[x + i][y + j]);
						counter++;
					}
				}
			}
		}
		return (short) (F / counter);
	}

}
