package lp.iem.test.test;

import static org.junit.Assert.*;
import lp.iem.gk.Geometry;
import lp.iem.gk.Vector;

import org.junit.Test;

import android.hardware.GeomagneticField;

public class GeometryTest
{
	@Test
	public void testCrossVectVect()
	{
		Vector v1 = new Vector(1,2,3);
		Vector v2 = new Vector(4,6,2);
		
		Vector v3 = Geometry.cross(v1, v2);
		assertEquals(v3,new Vector(-14,10,-2));
	}
	
	@Test
	public void testDotVectVect()
	{
		Vector v1 = new Vector(1,2,3);
		Vector v2 = new Vector(4,6,2);
		
		float prodScal = Geometry.Dot(v1, v2);
		assertTrue(prodScal == 22);		
	}
	
	@Test
	public void testAbsDot()
	{
		Vector v1 = new Vector(-1,-2,-3);
		Vector v2 = new Vector(4,6,2);
		
		float prodScal = Geometry.AbsDot(v1, v2);
		assertTrue(prodScal == 22);		
	}
	
	@Test
	public void testZeroDot()
	{
		Vector v1 = new Vector(-1,-2,-3);
		Vector v2 = new Vector(4,6,2);
		Vector v3 = new Vector(1,2,3);
		
		float prodScal = Geometry.ZeroDot(v1, v2);
		assertTrue(prodScal == 0);	
		
		float prodScal2 = Geometry.ZeroDot(v3, v2);
		assertTrue(prodScal2 == 22);	
	}
	
	
	

}
