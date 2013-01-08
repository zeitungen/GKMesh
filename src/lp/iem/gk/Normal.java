package lp.iem.gk;

public class Normal
{
	private float x;
	private float y;
	private float z;	
	
	public Normal(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	// retourn l'opposé de la normal
	public Normal opposite ()
	{
		return new Normal(-x, -y, -z);
	}
	
	// addition de 2 Normal : q = p + n , et retourn la normal q
	public Normal additionNormal (Normal n)
	{
		return new Normal(x + n.getX(), y + n.getY(), z + n.getZ());
	}
	
	// addition de 2 Normal : p = p + n ,
	public void additionNormal2 (Normal n)
	{
		x += n.getX();
		y += n.getY();
		z += n.getZ();
	}
	
	// soustraction de 2 Normal : q = p - n et retourne la normal q
	public Normal substractionNormal (Normal n)
	{
		return new Normal(x - n.getX(), y - n.getY(), z - n.getZ());
	}
	
	// soustraction de 2 Normal : p = p - n ,
	public void substractionNormal2 (Normal n)
	{
		x -= n.getX();
		y -= n.getY();
		z -= n.getZ();
	}
	
	// produit d'une normal avec un float : q = p * f, retourn la normal q
	public Normal productFloat(float f)
	{
		return new Normal(x*f, y*f, z*f);		
	}
	
	// produit d'une normal avec un float : p = p * f
	public void productFloat2(float f)
	{
		x = x * f;
		y = y * f;
		z = z * f;
	}
	
	// division d'une normal par un float : q = p / f, retourne la normal q
	public Normal dividByFloat(float f)
	{
		if (f != 0)
		{
			 float inv = 1.f / f;
		     return new Normal( x * inv, y * inv, z * inv );
		}
		return null;
	}
	
	// division d'une normal par n float : p  = p /f
	public void dividByFLoat2(float f)
	{
		if(f != 0)
		{
			float inv = 1.f / f;
	        x *= inv;
	        y *= inv;
	        z *= inv;
		}
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final Normal p = (Normal) o;
        return (this.x == p.getX() && this.y == p.getY() && this.z == p.getZ());
	}
	
	@Override
	public String toString()
	{
		// TODO : arrondire au dixieme
		return "X : "+x+ " Y : "+y+ " Z :"+z;
	}
	
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
