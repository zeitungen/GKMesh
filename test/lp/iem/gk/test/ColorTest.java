package lp.iem.test.test;

import static org.junit.Assert.*;
import lp.iem.gk.Color;
import lp.iem.gk.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ColorTest
{
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		
	}
	
	@Test
	public void testAddit()
	{
		Color c1 = new Color(12.f,5,(float)10.4,1.f);
		Color c2 = new Color(2.f,5.f,8,1);
		Color c4 = new Color(12.f,5,(float)10.4,1.f);
		
		Color c3 = c1.additionColor(c2);
		assertEquals(c3,new Color(14.f,10,(float)18.4,2.f));
		
		c4.additionColor2(c2);
		assertEquals(c4,new Color(14.f,10,(float)18.4,2.f));
	}
	
	@Test
	public void testSubstract()
	{
		Color c1 = new Color(12.f,5,10,1.f);
		Color c2 = new Color(2.f,2,8,1);
		Color c3 = new Color(12.f,5,(float)10.4,1.f);
		
		Color c4 = c1.substractionColor(c2);
		
		assertEquals(c4,new Color(10,3,2,0));
		
		
	}

	public void testProduct()
	{
		Color c1 = new Color(12.f,5,10,1.f);
		Color c2 = new Color(2.f,2,8,1);
		Color c3 = new Color(12.f,5,(float)10.4,1.f);
		
		Color c4 = c1.productColor(c2);
		
		assertEquals(c4, new Color(24,10,80,1));
		
		c1.productColor(c2);
		assertEquals(c1, new Color(24,10,80,1));
		
		Color c5 = c1.productByFloat(2);
		assertEquals(c5,new Color(24,10,20,2));
		
		c1.productByFloat2(2);
		assertEquals(c1,new Color(24,10,20,2));
	}
	
	@Test
	public void testDivide()
	{
		Color c1 = new Color(12.f,6,10,6.f);
		
		Color c3 = c1.divideByFloat(2);
		assertEquals(c3, new Color(6,3,5,3));
		
		c1.divideByFloat2(2);
		assertEquals(c1, new Color(6,3,5,3));
		
	}

	@Test
	public void  testOpposit()
	{
		Color c1 = new Color(12.f,6,10,6.f);
		Color c2 = c1.opposite();
		
		assertEquals(c2, new Color(-12.f,-6,-10,-6.f));
	}
	
	@Test
	public void testPower()
	{
		Color c1 = new Color(10,8,12);
		float power = c1.power();
		
		assertTrue(power == 10);
	}
	
	@Test
	public void testIsBlack()
	{
		Color c1 = new Color(0,0,0);
		Color c2 = new Color(0,(float)4.2,0);
		
		assertTrue(c1.isBlack());
		
		assertFalse(c2.isBlack());
	}
	
	@Test
	public void testget() throws Exception
	{
		Color n3 = new Color((float)4.3,15,(float)18.25);
		float r = n3.get(0);
		float g = n3.get(1);
		float b = n3.get(2);
		float a = n3.get(3);
		
		assertTrue(r == n3.getR());
		assertTrue(g == n3.getG());
		assertTrue(b == n3.getB());		
		assertTrue(a == n3.getA());		
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}

	@Before
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
	}
}
