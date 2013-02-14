package lp.iem.gk;

import java.util.List;

public class Color {
	
	private float r;
	private float g;
	private float b;
	private float a;

	public Color() {
		r = 0.f;	g = 0.f;
		b = 0.f; 	a = 0.f;
	}

	public Color(float r, float g, float b, float a) {
		this.r = r;		this.g = g;
		this.b = b;		this.a = a;
	}

	public Color(float r, float g, float b) {
		this.r = r;		this.g = g;
		this.b = b;		this.a = 1;
	}

	public Color(float f) {
		this.r = f;		this.g = f;
		this.b = f;		this.a = 1;
	}

	public Color(float f, float a) {
		this.r = f;		this.g = f;
		this.b = f;		this.a = a;
	}

	/**
	 * conversion from a HDRPixel
	 * @param p a HDRPixel
	 */
	public Color(HdrPixel p) {
		r = p.getR();	g = p.getG();
		b = p.getB();	a = p.getA();
	}

	/**
	 * conversion from a Pixel
	 * @param p a Pixel
	 */
	public Color(Pixel p) {
		r = (float) p.getR() / 255.f;
		g = (float) p.getG() / 255.f;
		b = (float) p.getB() / 255.f;
		a = (float) p.getA() / 255.f;
	}

	public Pixel pixel() {
		return new Pixel(Geometry.clamp(r * 255.f, 0.f, 255.f), Geometry.clamp(
				g * 255.f, 0.f, 255.f), Geometry.clamp(b * 255.f, 0.f, 255.f),
				Geometry.clamp(a * 255.f, 0.f, 255.f));
	}

	public HdrPixel hdrPixel() { return new HdrPixel(r, g, b, a); }

	public String toString() {
		return "Color {" + r + "," + g + "," + b + "," + a + "}";
	}

	/**
	 * additon of two colours, w = this + c
	 * @param c
	 * @return w
	 */
	public Color addition(Color c) {
		return new Color(r + c.getR(), g + c.getG(), b + c.getB(), a + c.getA());
	}

	/**
	 * addition of two colours this = this + c
	 * @param c
	 */
	public void add(Color c) {
		this.a += c.getA();
		this.r += c.getR();
		this.g += c.getG();
		this.b += c.getB();
	}

	/**
	 * substraction of two colours, w = this - c
	 * @param c
	 * @return w
	 */
	public Color substraction(Color c) {
		return new Color(r - c.getR(), g - c.getG(), b - c.getB(), a - c.getA());
	}

	/**
	 * substraction of two colours, this = this - c
	 * @param c
	 */
	public void subtract(Color c) {
		this.a -= c.getA();
		this.r -= c.getR();
		this.g -= c.getG();
		this.b -= c.getB();
	}

	/**
	 * product of two colours, w = this * c
	 * @param c
	 * @return w
	 */
	public Color productColor(Color c) {
		return new Color(r * c.getR(), g * c.getG(), b * c.getB(), a * c.getA());
	}

	/**
	 * product of two colours, this = this * c
	 * @param c
	 */
	public void product(Color c) {
		this.a *= c.getA();
		this.r *= c.getR();
		this.g *= c.getG();
		this.b *= c.getB();
	}

	/**
	 * product by a real, w = this * f
	 * @param f
	 * @return w
	 */
	public Color productColor(float f) {
		return new Color(f * r, f * g, f * b, f * a);
	}

	/**
	 * product by a real, this = this * f
	 * @param f
	 */
	public void product(float f) {
		r *= f;
		g *= f;
		b *= f;
		a *= f;
	}

	/**
	 * division of a colour by a real, w = this / f
	 * @param f
	 * @return w
	 */
	public Color divideByFloat(float f) {
		if (f != 0) {
			float inv = 1.f / f;
			return new Color(r * inv, g * inv, b * inv, a * inv);
		}
		return null;
	}

	/**
	 * division of a colour by a real, this = this / f
	 * @param f
	 */
	public void divide(float f) {
		if (f != 0) {
			float inv = 1.f / f;
			r *= inv;
			g *= inv;
			b *= inv;
			a *= inv;
		}
	}

	/**
	 * negation of a color, w = - this
	 * @return w
	 */
	public Color opposite() { return new Color(-r, -g, -b, -a); }

	/**
	 * 
	 * @return the amount of energy associated
	 */
	public float power() { return (r + g + b) / 3.f; }

	/**
	 * 
	 * @return true if the amount of energy is zero
	 */
	public Boolean isBlack() { return (r + g + b) == 0.f; }

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final Color p = (Color) o;
		return this.r == p.getR() && this.g == p.getG() && this.b == p.getB()
				&& this.a == p.getA();
	}

	public float get(int index) throws Exception {
		if (index < 0)
			throw new Exception("index < 0");
		else if (index > 3)
			throw new Exception("index > 3");
		else if (index == 0)
			return this.r;
		else if (index == 1)
			return this.g;
		else if (index == 2)
			return this.b;
		else
			return this.a;
	}

	public float getR() { return r; }
	public float getG() { return g; }
	public float getB() { return b;	}
	public float getA() { return a; }
	
	public void setR(float r) { this.r = r; }
	public void setG(float g) { this.g = g; }
	public void setB(float b) { this.b = b; }
	public void setA(float a) { this.a = a; }

	public static float[] toFloatArray(List<Color> colors) {
		float[] f = new float[colors.size() * 4];
		for (int i = 0, j = 0; i < colors.size(); i++) {
			Color c = colors.get(i);
			f[j++] = c.r;
			f[j++] = c.g;
			f[j++] = c.b;
			f[j++] = c.a;
		}
		return f;
	}

}
