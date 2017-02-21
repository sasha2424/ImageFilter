
package filters;

import javax.swing.JOptionPane;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

public class ColorRemove implements ImageFilter {
	private ImgProvider filteredImage;
	
	private String color;

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
		String in = "";
		while (!(in.equals("red") || in.equals("blue") || in.equals("green"))) {
			in = JOptionPane
					.showInputDialog("What color should be removed? \"red\",\"blue\", or\"green\"");
		}
		
		
		int r = 1;
		int b = 1;
		int g = 1;
		if(in.equals("red")){
			r =0;
		}
		if(in.equals("green")){
			g =0;
		}
		if(in.equals("blue")){
			b =0;
		}
		
		color = in;

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				newRed[row][col] = (short) (red[row][col]*r);
				newGreen[row][col] = (short) (green[row][col]*g);
				newBlue[row][col] = (short) (blue[row][col]*b);
			}
		}
		
		
		filteredImage = new ImgProvider();
		filteredImage.setColors(newRed, newGreen, newBlue, newTransp);

	
		filteredImage.showPix("Color Remove " + color);
	}

	@Override
	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	@Override
	public String getMenuLabel() {
		return "Color Remover";
	}

}