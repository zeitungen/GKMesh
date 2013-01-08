package lp.iem.gk;

public class Point 
{
	private float x;
	private float y;
	private float z;
	
	public Point()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Point(float v)
	{
		x = v;
		y = v;
		z = v;
	}
	
	public Point(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point (Vector v)
	{
		x = v.getX();
		y = v.getY();
		z = v.getZ();
	}
	
	@Override
	public String toString()
	{
		// TODO : arrondire au dixieme
		return "X : "+x+ " Y : "+y+ " Z :"+z;
	}
	
	//! addition d'un point et d'un vecteur, q= p + v, renvoie le point q.
	public Point additionVector(Vector v)
	{		
		return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
	}
	
	 //! addition d'un point et d'un vecteur, p= p + v.
	public void addition(Vector v)
	{
		x += v.getX();
		y += v.getY();
		z += v.getZ();
		
	}
	
	 //! soustraction de 2 points, v= p - q, renvoie le vecteur v.
	public Vector substractionPoint(Point p)
	{
		return new Vector(x - p.getX(), y - p.getY(), z - p.getZ());
	}
	
	//! soustraction d'un point et d'un vecteur, q= p - v, renvoie le point q.
	public Point substractionVector(Vector v)
	{
		return new Point(x - v.getX(), y - v.getY(), z - v.getZ());
	}
	
	//! soutraction d'un point et d'un vecteur, p= p - v.
	public void substraction(Vector v)
	{
		x -= v.getX();
		y -= v.getY();
		z -= v.getZ();	
	}
	
	 //! addition de 2 points p= p + q.
	public void additionPoint(Point p)
	{
		x = p.getX();
		y = p.getY();
		z = p.getZ();		
	}
	
	//! addition de 2 points, q= p + v, renvoie le point q.
	public Point pointAddition(Point p)
	{
		return new Point(x + p.getX(), y + p.getY(), z + p.getZ());
	}
	
	// produit d'un point par un float, q = p*n et retourne le point q
	public Point productFloat(float n)
	{
		return new Point(x * n, y * n, z * n);
	}
	
	// produit d'un point par un float, p = p*n
	public void productFloat2(float n)
	{
		x = x * n;
		y = y * n;
		z = z * n;
	}
	
	// division d'un Point par un float : q = p / n et retourn le point q
	public Point dividByFloat (float n)
	{
		float inv = 1 / n;
		return new Point(n * x, n * y, n * z);
	}
	
	// division d'un Point par un float : p = p / n
	public void dividByFloat2 (float n)
	{
		float inv = 1 / n;
		x = x * inv;
		y = y * inv;
		z = z * inv;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final Point p = (Point) o;
        return (this.x == p.getX() && this.y == p.getY() && this.z == p.getZ());
	}	

	// TODO : surcharge operateur [] :
	/*
	 const float& operator[]( const unsigned int i ) const
    {
        return ( &x )[i];
    }
    
    float &operator[]( const unsigned int i )
    {
        return ( &x )[i];
    }
    */
	


	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}
}
