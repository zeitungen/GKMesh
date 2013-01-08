package lp.iem.gk;

public class Color
{
	private float r;
	private float g;
	private float b;
	private float a;
	
	
	public Color()
	{
		r = 0.f;
		g = 0.f;
		b = 0.f;
		a = 0.f;
	}
		
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
	}
	
	public Color (float f)
	{
		this.r = f;
		this.g = f;
		this.b = f;
		
		this.a = 1;
	}
	
	public Color (float f, float a)
	{
		this.r = f;
		this.g = f;
		this.b = f;
		
		this.a = a;
	}

	/* TODO :
	 * 
	 *  //! constructeur, conversion depuis un HDRPixel.
    Color( const HDRPixel& pixel )
        :
        r(pixel.r),
        g(pixel.g),
        b(pixel.b),
        a(pixel.a)
    {}
    
    //! constructeur, conversion depuis un Pixel.
    Color( const Pixel& pixel )
        :
        r((float) pixel.r / 255.f),
        g((float) pixel.g / 255.f),
        b((float) pixel.b / 255.f),
        a((float) pixel.a / 255.f)
    {}

    operator Pixel( ) const
    {
        return Pixel(
            Clamp(r * 255.f, 0.f, 255.f), 
            Clamp(g * 255.f, 0.f, 255.f),
            Clamp(b * 255.f, 0.f, 255.f),
            Clamp(a * 255.f, 0.f, 255.f));
    }
    
    operator HDRPixel( ) const
    {
        return HDRPixel(r, g, b, a);
    }
	 */
	
	// affiche une couleur
	public String toString()
	{
		return "Color {"+r+","+g+","+b+","+a+"}";
	}
	
	//addition de 2 couleurs, w= u + v, renvoie w.
	public Color additionColor(Color c)
	{
		return new Color(r + c.getR(), g + c.getG(), b + c.getB(), a + c.getA());
	}

	//  addition de 2 vecteurs, u= u + v.
	public void additionColor2(Color c)
	{
		this.a += c.getA();
		this.r += c.getR();
		this.g += c.getG();
		this.b += c.getB();
	}
	
	// soustraction de 2 vecteurs, w= u - v, renvoie w
	public Color substractionColor(Color c)
	{
		return new Color(r - c.getR(), g - c.getG(), b - c.getB(), a - c.getA());
	}

	// soustraction de 2 vecteurs,u= u - v
	public void substractionColor2(Color c)
	{
		this.a -= c.getA();
		this.r -= c.getR();
		this.g -= c.getG();
		this.b -= c.getB();
	}
	
	// produit de 2 color, w = u * v, retourn la Color w
	public Color productColor(Color c)
	{
		return new Color(r * c.getR(), g * c.getG(), b * c.getB(), a * c.getA());
	}
	
	// produit de 2 color, u = u * v
	public void productColor2(Color c)
	{
		this.a *= c.getA();
		this.r *= c.getR();
		this.g *= c.getG();
		this.b *= c.getB();
	}

	//! produit par un reel, w= k * u, renvoie w.
	public Color productByFloat(float f)
	{
		return new Color(f * r, f * g, f * b, f * a);
	}
	
	//! produit par un reel, u= u * k.
	public void productByFloat2(float f)
	{
        r *= f;
        g *= f;
        b *= f;
        a *= f;			
	}
	
	// division d'une color par un float w = u / k
	public Color divideByFloat(float f)
	{
		if (f != 0)
		{
			float inv = 1.f / f;
	        return new Color( r * inv, g * inv, b * inv, a * inv );
		}
		return null;
	}
	
	// division d'une color par un float u = u / k
	public void divideByFloat2(float f)
	{
		if (f != 0)
		{
			float inv = 1.f / f;
	        r *= inv;
	        g *= inv;
	        b *= inv;
	        a *= inv;
		}
	}
	
	// negation d'un vecteur, w= -u, renvoie w.
	public Color opposite()
	{
		return new Color( -r, -g, -b, -a );
	}
	
	// renvoie la quantite d'energie associe
	public float power() 
    {
        return (r + g + b) / 3.f;
    }
	
	//! renvoie vrai la quantite d'energie est nulle
	public  Boolean isBlack( ) 
    {
        return (r + g + b) == 0.f;
    }
    
	@Override
	public boolean equals(Object o) 
	{
		if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    
	    final Color p = (Color) o;
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
	
	/* TODO
	 * 
	//! renvoie une composante du vecteur.
    const float& operator[]( const unsigned int i ) const
    {
        return ( &r )[i];
    }
    
    //! renvoie une reference sur une composante du vecteur.
    float &operator[]( const unsigned int i )
    {
        return ( &r )[i];
    }
	 * 
	 */
	
}
