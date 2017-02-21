
package filters;

import javax.swing.JOptionPane;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

public class ColorReplacer implements ImageFilter {
	private ImgProvider filteredImage;

	@Override
	public void filter(ImgProvider ip) {
		// get pixels

		short[][] red = ip.getRed();
		short[][] green = ip.getGreen();
		short[][] blue = ip.getBlue();
		short[][] transp = ip.getAlpha();

		// create a new array for pixels
		int height = red.length;
		int width = red[0].length;

		short[][] newRed = new short[height][width];
		short[][] newGreen = new short[height][width];
		short[][] newBlue = new short[height][width];
		short[][] newTransp = new short[height][width];

		// copy pixel values into new array

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				newRed[row][col] = red[row][col];
				newGreen[row][col] = green[row][col];
				newBlue[row][col] = blue[row][col];
				newTransp[row][col] = transp[row][col];
			}
		}

		int inRed = Integer.parseInt(JOptionPane.showInputDialog("What red?"));
		int inGreen = Integer.parseInt(JOptionPane.showInputDialog("What green?"));
		int inBlue = Integer.parseInt(JOptionPane.showInputDialog("What blue?"));

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				double r = red[row][col];
				double g = green[row][col];
				double b = blue[row][col];

				double t = Math.sqrt((inGreen - g) * (inGreen - g) + (inBlue - b) * (inBlue - b) + (inRed - r) * (inRed - r));
				
				if (t < 150) {
					newRed[row][col] = 0;
					newGreen[row][col] = 0;
					newBlue[row][col] = 0;
				}

			}
		}

		filteredImage = new ImgProvider();
		filteredImage.setColors(newRed, newGreen, newBlue, newTransp);

		filteredImage.showPix("Color Replacer ");
	}

	@Override
	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	@Override
	public String getMenuLabel() {
		return "Color Replacer";
	}

}