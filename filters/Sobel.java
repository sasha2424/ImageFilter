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

public class Sobel implements ImageFilter {

	private static final int[][] V = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
	private static final int[][] H = { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };
	// Attribute to store the modified image
	ImgProvider filteredImage;

	public void filter(ImgProvider ip) {

		// Grab the pixel information and put it into a 2D array
		short[][] BW = ip.getBWImage();

		// Make variables for image height and width
		int height = BW.length;
		int width = BW[0].length;

		// Create a new array to store the modified image
		short[][] im = new short[height][width];

		int thresh = Integer.parseInt(JOptionPane.showInputDialog("Enter the threshold you putant slave!!!"));

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int a = mask(V, BW, row, col);
				int b = mask(H, BW, row, col);
				short F = (short) (Math.sqrt(a * a + b * b));
				if (thresh < 0 || thresh > 255) {
					im[row][col] = F;
				} else if (F > thresh) {
					im[row][col] = 255;
				} else {
					im[row][col] = 0;
				}
			}
		}

		// Create a new ImgProvider and set the filtered image to our new image
		filteredImage = new ImgProvider();
		filteredImage.setBWImage(im);

		// Show the new image in a new window with title "Flipped Horizontally"
		filteredImage.showPix("Sobeled");
	}

	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	// This is what users see in the Filter menu
	public String getMenuLabel() {
		return "Sobeled";
	}

	public short mask(int[][] mask, short[][] a, int x, int y) {
		double sum = 0;
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[0].length; j++) {
				if (isValid(a, x + i + 1, y + j + 1))
					sum += get(a, x + i + 1, y + j + 1) * mask[i][j];
			}
		}
		return (short) (sum);
	}

	public short get(short[][] a, int x, int y) {
		if (0 < x && x < a.length && 0 < y && y < a[0].length) {
			return a[x][y];
		}
		return 0;
	}

	public boolean isValid(short[][] a, int x, int y) {
		return 0 < x && x < a.length && 0 < y && y < a[0].length;
	}

}
