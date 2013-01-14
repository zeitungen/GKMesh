package lp.iem.mesh;

import lp.iem.gk.Point;

//! representation indexee des sommets d'un triangle.
public class MeshTriangle
{

	Mesh mesh;
	int a, b, c;
	
	public MeshTriangle()
	{
		mesh = null;
		a = -1;
		b = -1;
		c = -1;
	}
	
	public MeshTriangle(Mesh _mesh, int _a, int _b, int _c)
	{
		mesh = _mesh;
		a = _a;
		b = _b;
		c = _c;
	}
	
	  //! renvoie l'indice du sommet k.
	public int vertex(int index) throws Exception
	{
		if (index < 0)
			throw new Exception ("index <0");
		else if(index > 2)
			throw new Exception ("index > 2");
		else if(index == 0)
			return this.a;
		else if(index == 1)
			return this.b;
		else 
			return this.c;		
	}
	
	 //! renvoie l'indice du sommet k.
	public int get(int index) throws Exception
	{
		if (index < 0)
			throw new Exception ("index <0");
		else if(index > 2)
			throw new Exception ("index > 2");
		else if(index == 0)
			return this.a;
		else if(index == 1)
			return this.b;
		else 
			return this.c;		
	}

	
}
