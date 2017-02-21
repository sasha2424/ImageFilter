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

public class BWConvolved implements ImageFilter {

	private static final int[][] VANILLA_AVERAGE = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
	private static final int[][] GAUSSIAN_BLUR = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };
	private static final int[][] VERTICAL = { { 1, 0, -1 }, { 1, 0, -1 },{ 1, 0, -1 },{ 1, 0, -1 },{ 1, 0, -1 } };
	private static final int[][] HORIZONTAL = { { 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0 }, { -1, -1, -1, -1, -1 } };
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
		short[][] im = new short[height][width];

		int blur = Integer.parseInt(JOptionPane.showInputDialog("enter blur constant"));
		String s = JOptionPane.showInputDialog("mask: \"gaussian\", \"vanilla\",\"horizontal\",\"vertical\"");
		int[][] mask = VANILLA_AVERAGE;
		if (s.equals("gaussian")) {
			mask = GAUSSIAN_BLUR;
		}
		if (s.equals("horizontal")) {
			mask = HORIZONTAL;
		}
		if (s.equals("vertical")) {
			mask = VERTICAL;
		}
		
		int MASK_SUM = 0;
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[0].length; j++) {
				MASK_SUM += mask[i][j] * blur;
			}
		}

		for (int row = 0; row < height - mask.length; row++) {
			for (int col = 0; col < width - mask[0].length; col++) {
				im[row][col] = (short) (avg(mask, MASK_SUM, r, row, col, blur) + avg(mask, MASK_SUM, g, row, col, blur) + avg(mask, MASK_SUM, b, row, col, blur) / 3);
			}
		}

		// Create a new ImgProvider and set the filtered image to our new image
		filteredImage = new ImgProvider();
		filteredImage.setBWImage(im);

		// Show the new image in a new window with title "Flipped Horizontally"
		filteredImage.showPix("BWColvolved");
	}

	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	// This is what users see in the Filter menu
	public String getMenuLabel() {
		return "BWConvolved";
	}

	public short avg(int[][] mask, int MASKSUM, short[][] a, int x, int y, int c) {
		double sum = 0;
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[0].length; j++) {
				sum += a[x + i][y + j] * mask[i][j] * c;
			}
		}
		return (short) (sum / MASKSUM);
	}

}
