package lp.iem.gk;


public class Pixel
{
	private float r;
	private float g;
	private float b;
	private float a;
	
	public Pixel()
	{
		r = 0.f;
		g = 0.f;
		b = 0.f;
		a = 255.f;
	}
	
	public Pixel(float v)
	{
		r = v;
		g = v;
		b = v;
		a = 255;
	}
	
	public Pixel (float v, float a)
	{
		r = v;
		g = v;
		b = v;
		this.a = a;
	}
	
	public Pixel (float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Pixel (float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 255;
	}	

	// compare 2 Pixel, true si elles sont egales, 0 sinon
	@Override
	public boolean equals(Object o) 
	{
		if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    
	    final Pixel p = (Pixel) o;
	    return this.r == p.getR() && this.g == p.getG() && this.b == p.getB() && this.a == p.getA();
	}
		
	public float getR()
	{
		return r;
	}
	public void setR(float r)
	{
		this.r = r;
	}
	public float getG()
	{
		return g;
	}
	public void setG(float g)
	{
		this.g = g;
	}
	public float getB()
	{
		return b;
	}
	public void setB(float b)
	{
		this.b = b;
	}
	public float getA()
	{
		return a;
	}
	public void setA(float a)
	{
		this.a = a;
	}
	
	static Boolean isColoredPixel()
	{
		return true;
	}
	
	static Boolean isHdrPixel()
	{
		return false;
	}

}
