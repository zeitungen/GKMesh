package lp.iem.gk;

public class Vector
{
	private float x;
	private float y;
	private float z;
	
	public Vector()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	

	public Vector(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
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
