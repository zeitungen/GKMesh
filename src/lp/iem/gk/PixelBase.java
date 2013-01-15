package lp.iem.gk;

public abstract class PixelBase {

	protected float r;
	protected float g;
	protected float b;
	protected float a;
	
	public PixelBase(){
		r = 0.f; g = 0.f;
		b = 0.f; a = 255.f;
	}
	
	public PixelBase(float v){
		r = v; g = v;
		b = v; a = 255.f;
	}
	
	public PixelBase(float v, float a){
		this.r = v; this.g = v;
		this.b = v; this.a = a;
	}

	public PixelBase(float r, float g, float b, float a){
		this.r = r; this.g = g;
		this.b = b; this.a = a;
	}

	public PixelBase(float r, float g, float b){
		this.r = r; this.g = g;
		this.b = b; this.a = 255.f;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    
	    final PixelBase p = (PixelBase) o;
	    return this.r == p.getR() && this.g == p.getG() && this.b == p.getB() && this.a == p.getA();
	}

	public float getR(){ return r; }
	public float getG(){ return g; }
	public float getB(){ return b; }
	public float getA(){ return a; }
	
	public void setR(float r){ this.r = r; }
	public void setG(float g){ this.g = g; }
	public void setB(float b){ this.b = b; }
	public void setA(float a){ this.a = a; }
	
	public abstract boolean isColoredPixel();
	public abstract boolean isHdrPixel();
}
