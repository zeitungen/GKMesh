package lp.iem.gk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.gk.Vector;
import org.junit.*;

public class PointTest
{
	static Point point1;
	static Point point2;
	static Point point3;
	static Point point4;

	@BeforeClass
	public static void setUpClass() throws Exception
	{
		point1 = new Point();
		point2 = new Point((float) 12.5);
		point3 = new Point((float) 12.2,(float) 19.5,(float) 32.7);
		point4 = new Point(new Vector((float) 14.2,(float) 12.2,(float) 18.7));
	}

	@Test
	public void testAdditionPointVector()
	{
		Point test = new Point(1,1,1);
		Vector v = new Vector (2,2,2);

		point3.addition(v);
	}
	
	@Test
	public void testSubstractionPointVector()
	{
		Point test = new Point(1,1,1);
		Point test2 = new Point((float) 2.5,9,4);
		Vector v = new Vector (2,1,2);
		
		Vector res = test2.substractionPoint(test);
		Point res2 = test2.substractionVector(v);

		test2.substraction(v);
	}
	
	@Test
	public void testEqual()
	{
		Point p1 = new Point(10,45,(float) 12.3);
		Point p2 = new Point(10,45,(float) 12.3);
		
		Point p3 = new Point(13,45,(float) 12.3);
		assertEquals(p1,p2);	
	}
	
	@Test
	public void testDevid()
	{
		Point p1 = new Point(25,12,15);
		float f = (float) 2;
		
		p1.dividByFloat2(f);
		Point p2 = new Point((float)12.5,6,(float)7.5);
		
		assertEquals(p1,p2);
	}
	
	@Test
	public void testget() throws Exception
	{
		Point n3 = new Point((float)4.3,15,(float)18.25);
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

	@Test
	public void testToArrayFloat(){
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(0.f, 1.f, 2.f));
		points.add(new Point(0.f, 13.f, 2.f));
		float[] f = Point.getFloatArray(points);
		assertTrue(f[0] == 0.f); assertTrue(f[1] == 1.f); assertTrue(f[2] == 2.f);
		assertTrue(f[3] == 0.f); assertTrue(f[4] == 13.f); assertTrue(f[5] == 2.f);
	}
}
