package lp.iem.test.test;

import static org.junit.Assert.*;
import lp.iem.gk.BasicRay;
import lp.iem.gk.Point;
import lp.iem.gk.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

public class BasicRayTest
{
	static Point p;
	static Vector v;
	static BasicRay bas;
	
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		p = new Point(1,0,0);
		v = new Vector(1,1,1);
		
		bas = new BasicRay(p,v,5);
	}
	
	@Test
	public void testConstructeurs()
	{
		assertNotNull(bas);
	}
	
	@Test
	public void testAbcisse()
	{
		Point abs = bas.abscisse(2);
		assertTrue(abs.getX() == 3);
		assertTrue(abs.getY() == 2);
		assertTrue(abs.getZ() == 2);
		
	}

}
