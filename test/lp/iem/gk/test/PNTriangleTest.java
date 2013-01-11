package lp.iem.gk.test;

import static org.junit.Assert.*;
import lp.iem.gk.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class PNTriangleTest
{
	static Point p1;
	static Point p2;
	static Point p3;
	
	static Normal n1;
	static Normal n2;
	static Normal n3;
	
	static Triangle t1;
	static PNTriangle pnt1;
	
	@BeforeClass
    public static void setUpClass() throws Exception 
    {
		p1 = new Point(1,0,1);
		p2 = new Point(1,1,0);
		p3 = new Point(1,0,0);
		
		n1 = new Normal(0,0,0);
		n2 = new Normal(1,1,1);
		n3 = new Normal(2,0,1);
		
		
		t1 = new Triangle(p1,p2,p3);
		pnt1 = new PNTriangle(t1, n1, n2, n3);
    }
	
	@Test
	public void TestArea()
	{
		float area = pnt1.getArea();
		assertTrue(area == 0.5f);
	}
	
	@Test
	public void TestgetUVNormal() throws Exception
	{
		Normal v1 = pnt1.getUVNormal(3, 1);
		assertEquals(0.707f, v1.getX(), 0.01);
		assertEquals(0.424f, v1.getY(), 0.01);
		assertEquals(0.575f, v1.getZ(), 0.01);
	}


}
