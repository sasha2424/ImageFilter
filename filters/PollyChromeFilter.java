
package filters;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

public class PollyChromeFilter implements ImageFilter {
	private ImgProvider filteredImage;

	private String color;

	@Override
	public void filter(ImgProvider ip) {
		// get pixels

		short[][] red = ip.getRed();
		short[][] green = ip.getGreen();
		short[][] blue = ip.getBlue();

		// create a new array for pixels
		int height = red.length;
		int width = red[0].length;

		short[][] r = new short[height][width];
		short[][] g = new short[height][width];
		short[][] b = new short[height][width];

		int k = Integer.parseInt(JOptionPane.showInputDialog("Number of Colors:"));
		

		ArrayList<Pixel> pixels = new ArrayList<Pixel>();

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				Pixel p = new Pixel(red[row][col], green[row][col], blue[row][col], 0);
				pixels.add(p);
			}
		}

		Group[] groups = new Group[k];
		for (int i = 0; i < k; i++) {
			//groups[i] = new Group(pixels.get((int) (Math.random() * pixels.size())));
			groups[i] = new Group(new Pixel((short)(Math.random() *255),(short)(Math.random() *255),(short)(Math.random() *255),-1));
		}

		// ________________________________________________

		boolean update = false;

		int counter = 0;

		while (!update) {
			update = true;

			counter++;

			long[][] avgData = new long[k][4];

			for (Pixel p : pixels) {

				p.setGroup(p.closest(groups));

				avgData[p.getGroup()][0] += p.getR();
				avgData[p.getGroup()][1] += p.getG();
				avgData[p.getGroup()][2] += p.getB();
				avgData[p.getGroup()][3]++;

			}
			for (int i = 0; i < groups.length; i++) {
				if (avgData[i][3] != 0) {
					groups[i].update((short) (avgData[i][0] / avgData[i][3]), (short) (avgData[i][1] / avgData[i][3]),
							(short) (avgData[i][2] / avgData[i][3]));
				}

				update = update && groups[i].isDone();
			}
		}

		for (int i = 0; i < pixels.size(); i++) {
			r[(short) (i / width)][i % width] = groups[pixels.get(i).closest(groups)].getL().getR();
			g[(short) (i / width)][i % width] = groups[pixels.get(i).closest(groups)].getL().getG();
			b[(short) (i / width)][i % width] = groups[pixels.get(i).closest(groups)].getL().getB();
		}

		filteredImage = new ImgProvider();
		filteredImage.setColors(r, g, b, ip.getAlpha());

		filteredImage.showPix("PollyChrome");
	}

	@Override
	public ImgProvider getImgProvider() {
		return filteredImage;
	}

	@Override
	public String getMenuLabel() {
		return "PollyChrome";
	}

	public class Group {
		private Pixel L;
		private Pixel prev;

		public Group(Pixel p) {
			L = new Pixel(p.getR(), p.getG(), p.getB(), -1);
			prev = new Pixel(p.getR(), p.getG(), p.getB(), -1);
		}

		public void update(short r, short g, short b) {

			prev.setR(L.getR());
			prev.setG(L.getG());
			prev.setB(L.getB());
			L.setR((short) (r));
			L.setG((short) (g));
			L.setB((short) (b));
		}

		public boolean isDone() {
			if ((L.getR() == prev.getR()) && (L.getG() == prev.getG()) && (L.getB()) == prev.getB()) {
				return true;
			}
			return false;
		}

		public Pixel getL() {
			return L;
		}

		public void setL(Pixel l) {
			L = l;
		}

	}

	public class Pixel {
		int G; // group

		private short r;
		private short g;
		private short b;

		public Pixel(short r, short g, short b, int group) {
			this.r = r;
			this.g = g;
			this.b = b;
			this.G = group;
		}


		public int closest(Group[] g) {
			int closest = 0;
			for (int i = 0; i < g.length; i++) {
				if (dist(g[closest]) > dist(g[i])) {
					closest = i;
				}
			}
			return closest;
		}

		public double dist(Group g) {
			return Math.sqrt(((int) this.getR() - (int) g.getL().getR()) * ((int) this.getR() - (int) g.getL().getR())
					+ ((int) this.getG() - (int) g.getL().getG()) * ((int) this.getG() - (int) g.getL().getG())
					+ ((int) this.getB() - (int) g.getL().getB()) * ((int) this.getB() - (int) g.getL().getB()));
		}

		public short getR() {
			return r;
		}

		public void setR(short r) {
			this.r = r;
		}

		public short getG() {
			return g;
		}

		public void setG(short g) {
			this.g = g;
		}

		public short getB() {
			return b;
		}

		public void setB(short b) {
			this.b = b;
		}

		public void setGroup(int g) {
			G = g;
		}

		public int getGroup() {
			return G;
		}

	}

}