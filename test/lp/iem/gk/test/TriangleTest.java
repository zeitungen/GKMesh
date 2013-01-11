package lp.iem.gk.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import lp.iem.gk.BBox;
import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.gk.Sampler;
import lp.iem.gk.Triangle;
import lp.iem.gk.Vector;

public class TriangleTest
{
	static Point p1;
	static Point p2;
	static Point p3;
	static Triangle t1;
	
	@BeforeClass
    public static void setUpClass() throws Exception 
    {
		p1 = new Point(1,0,1);
		p2 = new Point(1,1,0);
		p3 = new Point(1,0,0);
		
		t1 = new Triangle(p1,p2,p3);		
    }
	
	@Test
	public void testGetArea()
	{		
		float area = t1.getArea();
		assertTrue(area == 0.5f);
	}
	
	@Test
	public void testGetNormal() throws Exception
	{
		Normal n1 = t1.getNormal();
		
		assertTrue(n1.getX() == -1);
		assertTrue(n1.getY() == 0 );
		assertTrue(n1.getZ() == 0 );
	}

	@Test
	public void testLocal() throws Exception
	{
		Vector v = new Vector(1,0,1);
		Vector res = t1.local(v);
				
		assertEquals(0.70, res.getX(), 0.01);
		assertEquals(-0.70, res.getY(), 0.01);
		assertEquals(-1, res.getZ(), 0.01);
	}
	
	@Test
	public void testWorld() throws Exception
	{
		Vector v = new Vector(1,0,1);
		Vector res = t1.world(v);
		
		assertTrue(0 == res.getX());
		assertEquals(1.41, res.getY(), 0.01);
		assertEquals(1.41, res.getZ(), 0.01);
		
	}

	@Test
	public void testGetBbox()
	{
		BBox sensation = t1.getBBox();
		assertEquals(sensation.getMax(), new Point(1,1,1));
		assertEquals(sensation.getMin(), new Point(1,0,0));
	}

	@Test
	public void testGetUVPoint()
	{
		Point p = t1.getUVPoint(2, 6);
		assertEquals(new Point(1,2,-7), p);
		
	}

	@Test
	public void testsampleUniform()
	{
		Point p =  new Point();
		float area = t1.sampleUniform(9, 2,p);
		
		assertEquals(new Point(1,-3,-2), p);
		assertTrue(area == 2);
	}
	
	@Test
	public void testSampleUniformSample()
	{
		Sampler samp = new Sampler();
		float[] area = t1.sampleUniformUV(samp, 2, 4);
		assertTrue(area[2] == 2); // area[2] : valeur de 1/area or area = 0.5 pout t1
	}

}
