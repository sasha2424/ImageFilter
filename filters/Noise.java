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

public class Noise implements ImageFilter{
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
		
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				newRed[row][col] = red[row][col];
				newGreen[row][col] = green[row][col];
				newBlue[row][col] = blue[row][col];
				newTransp[row][col] = transp[row][col];
			}
		}
		
		int noisePercent = Integer.parseInt(JOptionPane.showInputDialog("How much % noised"));
				
		int numPixels = (int)((noisePercent/100.0)*(width*height));
		
		for(int i = 0; i < numPixels; i++) {
			int col = (int) (Math.random()*width);
			int row = (int) (Math.random()*height);
			short r = (short) (Math.random()*255);
			short g = (short) (Math.random()*255);
			short b = (short) (Math.random()*255);
			
			newRed[row][col] = r;
			newGreen[row][col] = g;
			newBlue[row][col] = b;
			
		}
		// Create a new ImgProvider and set the filtered image to our new image
		filteredImage = new ImgProvider();
		filteredImage.setColors(newRed,newGreen, newBlue, newTransp);

		// Show the new image in a new window with title "Flipped Horizontally"
		filteredImage.showPix("Noisy");
	}

	@Override
	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	@Override
	public String getMenuLabel() {
		return "Visual Noise";
	}

}