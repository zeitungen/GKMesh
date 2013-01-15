package lp.iem.gk;

public class HdrPixel extends PixelBase{

	public HdrPixel() {
		super();
	}

	public HdrPixel(float v) {
		super(v);
	}

	public HdrPixel(float v, float a) {
		super(v, a);
	}

	public HdrPixel(float r, float g, float b, float a) {
		super(r, g, b, a);
	}

	public HdrPixel(float r, float g, float b) {
		super(r, g, b);
	}

	public HdrPixel(Pixel p) {
		super(p.getR() / 255.f, p.getG() / 255.f, p.getB() / 255.f, p.getA() / 255.f);
	}

	public boolean isColoredPixel() { return false; }
	public boolean isHdrPixel() { return true; }

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final HdrPixel p = (HdrPixel) o;
		return r == p.r && g == p.g && b == p.b && a == p.a;
	}

}
