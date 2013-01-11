package lp.iem.gk.test;

import static org.junit.Assert.*;
import lp.iem.gk.Normal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NormalTest
{
	static Normal n1;
	static Normal n2;
	
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		 n1 = new Normal(10,(float)15.5,(float)16.2);
		 n2 = new Normal(18,15,12);
		
	}
	
	@Test
	public void testOpposite()
	{
		Normal n3 = new Normal(-18,-15,-12);
		Normal opp = n2.opposite();
		assertEquals(n3,opp);
	}
	
	@Test
	public void testAddition()
	{
		Normal n3 = new Normal(28,(float)30.5,(float)28.2);
		Normal res = n1.additionNormal(n2);
		assertEquals(res, n3);
		
		n1.additionNormal2(n2);
		assertEquals(n1, n3);
	}
	
	@Test
	public void testSubstraction()
	{
		Normal n3 = new Normal(3,(float)4.5,2);
		Normal n4 = new Normal(6,4,2);
		
		Normal res = n4.substractionNormal(n3);
		assertEquals(res, new Normal(3,(float)-0.5,0));
		
		n4.substractionNormal2(n4);
		assertEquals(n4,new Normal(0,0,0));
	}

	@Test
	public void testProduct()
	{
		Normal n3 = new Normal(3,(float)4.5,2);
		Normal n4 = new Normal(6,1,2);
		
		Normal res = n4.productFloat((float)0.5);
		assertEquals(res, new Normal(3,(float)0.5,1));
		
		n4.dividByFLoat2(2);
		assertEquals(n4,new Normal(3,(float)0.5,1));
		
	}

	@Test
	public void testDivid()
	{
		Normal n3 = new Normal(3,(float)4.5,2);
		Normal n4 = new Normal(6,1,2);
		
		Normal res = n4.dividByFloat(10);
		assertEquals(res, new Normal((float)0.6,(float)0.1,(float)0.2));
		
		n4.dividByFLoat2(2);
		assertEquals(n4, new Normal(3,(float)0.5,1));
	}
	
	@Test
	public void testLength()
	{
		Normal n3 = new Normal(4,3,0);
		float res = n3.length();
		
		assertTrue(n3.lengthSquared() == 25);
		assertTrue(res == 5);
	}
	
	@Test
	public void testget() throws Exception
	{
		Normal n3 = new Normal(4,3,0);
		float x = n3.get(0);
		float y = n3.get(1);
		float z = n3.get(2);
		
		assertTrue(x == n3.getX());
		assertTrue(y == n3.getY());
		assertTrue(z == n3.getZ());		
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
