package lp.iem.gk.test;


import static org.junit.Assert.*;
import lp.iem.gk.Point;
import lp.iem.gk.Ray;
import lp.iem.gk.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

public class RayTest
{
	static Point p;
	static Vector v;
	static Ray ray;
	
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		p = new Point(1,0,0);
		v = new Vector(1,1,1);
		
		ray = new Ray(p,v,5);
	}
	
	@Test
	public void testConstruct()
	{
		assertNotNull(ray);
	}
	
	@Test
	public void testBacward()
	{
		Boolean bol = ray.isBackward(0);
		assertTrue(bol == false);
		assertTrue(bol != true);
	}

}
