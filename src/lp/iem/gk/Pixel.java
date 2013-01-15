package lp.iem.gk;

public class Pixel extends PixelBase{

	public Pixel() {
		super();
	}

	public Pixel(float v) {
		super(v);
	}

	public Pixel(float v, float a) {
		super(v, a);
	}

	public Pixel(float r, float g, float b, float a) {
		super(r, g, b, a);
	}

	public Pixel(float r, float g, float b) {
		super(r, g, b);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final Pixel p = (Pixel) o;
		return r == p.r && g == p.g && b == p.b && a == p.a;
	}

	public boolean isColoredPixel() { return true; }
	public boolean isHdrPixel() { return false; }

}
