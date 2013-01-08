package lp.iem.gk;

public class Geometry
{

	//! produit vectoriel de 2 vecteurs.
	public static Vector cross( Vector v1, Vector v2 )
	{
	    return new Vector(
	        ( v1.getY() * v2.getZ() ) - ( v1.getZ() * v2.getY() ),
	        ( v1.getZ() * v2.getX() ) - ( v1.getX() * v2.getZ() ),
	        ( v1.getX() * v2.getY() ) - ( v1.getY() * v2.getX()) );
	}
	
	//! produit scalaire de 2 vecteurs.
	public static float Dot(Vector v1,Vector v2)
	{
	    return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
	}
	
	//! valeur absolue du produit scalaire de 2 vecteurs.
	public static float AbsDot(Vector v1,Vector v2 )
	{
	    return Math.abs(Dot( v1, v2 ));		
	}
	
	public static float ZeroDot(Vector v1,Vector v2 )
	{
	    return Math.max(0.f, Dot( v1, v2 ));
	}

}
