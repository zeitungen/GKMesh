package lp.iem.gk;

public class Triangle
{
	Point a, b, c;
	float area;
	
	// constructeur vide
	public Triangle()
	{
		
	}
	
	public Triangle(Point a, Point b, Point c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		
		this.area = getArea();
	}

	public float getArea()
	{
		Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        
        return .5f * Geometry.cross(ab, ac).length();
        
	}

}
