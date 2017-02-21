package filters;

import imagelab.*;

public class HFlip implements ImageFilter {

	// Attribute to store the modified image
	ImgProvider filteredImage;

	public void filter(ImgProvider ip) {

		short[][] r = ip.getRed();
		short[][] g = ip.getGreen();
		short[][] b = ip.getBlue();

		int height = r.length;
		int width = r[0].length;

		short[][] red = new short[height][width];
		short[][] green = new short[height][width];
		short[][] blue = new short[height][width];

		// Loop through the original image and store the modified
		// version in the newImage array
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				red[row][col] = r[row][(width - 1) - col];
				green[row][col] = g[row][(width - 1) - col];
				blue[row][col] = b[row][(width - 1) - col];
			}
		}

		// Create a new ImgProvider and set the filtered image to our new image
		filteredImage = new ImgProvider();
		filteredImage.setColors(red, green, blue, ip.getAlpha());

		
		filteredImage.showPix("Horizontally Flipped");

	}

	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	// This is what users see in the Filter menu
	public String getMenuLabel() {
		return "Horizontal Flip";
	}

}