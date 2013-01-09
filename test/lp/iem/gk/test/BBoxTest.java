package lp.iem.gk.test;

import static org.junit.Assert.*;
import lp.iem.gk.BBox;
import lp.iem.gk.Point;
import lp.iem.gk.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BBoxTest {

	static BBox sensation;
	
	static Point p1;
	static Point p2;
	
	@BeforeClass
	public static void setUpClass() throws Exception{
		p1 = new Point(-10.f, 5.f, 15.f);
		p2 = new Point(10.f, 20.f, -15.f);
		sensation = new BBox(p1, p2);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception{
	}

	@Before
	public void setUp(){
	}

	@After
	public void tearDown(){
		sensation = new BBox(p1, p2);
	}
	
	
	
	@Test
	public void testConstructor() {
		sensation = new BBox();
		assertTrue(sensation.getMax().getX() == BBox.HUGE_VAL);
		assertTrue(sensation.getMax().getY() == BBox.HUGE_VAL);
		assertTrue(sensation.getMax().getZ() == BBox.HUGE_VAL);
		assertTrue(sensation.getMin().getX() == -BBox.HUGE_VAL);
		assertTrue(sensation.getMin().getY() == -BBox.HUGE_VAL);
		assertTrue(sensation.getMin().getZ() == -BBox.HUGE_VAL);
		
		sensation = new BBox(p1);
		assertTrue(sensation.getMax().getX() == -10.f);
		assertTrue(sensation.getMax().getY() == 5.f);
		assertTrue(sensation.getMax().getZ() == 15.f);
		assertTrue(sensation.getMin().getX() == -10.f);
		assertTrue(sensation.getMin().getY() == 5.f);
		assertTrue(sensation.getMin().getZ() == 15.f);
		
		sensation = new BBox(p1, p2);
		assertTrue(sensation.getMax().getX() == 10.f);
		assertTrue(sensation.getMax().getY() == 20.f);
		assertTrue(sensation.getMax().getZ() == 15.f);
		assertTrue(sensation.getMin().getX() == -10.f);
		assertTrue(sensation.getMin().getY() == 5.f);
		assertTrue(sensation.getMin().getZ() == -15.f);
	}

	@Test
	public void testUnion(){
		Point p1 = new Point(40.f, -50.f, 30.f);
		Point p2 = new Point(-25.f, 15.f, 30.5f);
		BBox b = new BBox(p1, p2);
		
		sensation.union(p1);
		assertTrue(sensation.getMax().getX() == 40.f);
		assertTrue(sensation.getMax().getY() == 20.f);
		assertTrue(sensation.getMax().getZ() == 30.f);
		assertTrue(sensation.getMin().getX() == -10.f);
		assertTrue(sensation.getMin().getY() == -50.f);
		assertTrue(sensation.getMin().getZ() == -15.f);
		tearDown();
		
		sensation.union(b);
		assertTrue(sensation.getMax().getX() == 40.f);
		assertTrue(sensation.getMax().getY() == 20.f);
		assertTrue(sensation.getMax().getZ() == 30.5f);
		assertTrue(sensation.getMin().getX() == -25.f);
		assertTrue(sensation.getMin().getY() == -50.f);
		assertTrue(sensation.getMin().getZ() == -15.f);
		tearDown();
		
		BBox c = BBox.union(b, sensation);
		
		assertTrue(c.getMax().getX() == 40.f);
		assertTrue(c.getMax().getY() == 20.f);
		assertTrue(c.getMax().getZ() == 30.5f);
		assertTrue(c.getMin().getX() == -25.f);
		assertTrue(c.getMin().getY() == -50.f);
		assertTrue(c.getMin().getZ() == -15.f);
		
		c = BBox.union(sensation, p1);
		assertTrue(c.getMax().getX() == 40.f);
		assertTrue(c.getMax().getY() == 20.f);
		assertTrue(c.getMax().getZ() == 30.f);
		assertTrue(c.getMin().getX() == -10.f);
		assertTrue(c.getMin().getY() == -50.f);
		assertTrue(c.getMin().getZ() == -15.f);
	}

	@Test
	public void testOverlaps(){
		Point p1 = new Point(15.f, 25.f, 20.f);
		Point p2 = new Point(20.f, 35.f, 43.f);
		Point p3 = new Point(0.f,6.f,0.f);
		
		BBox b = new BBox(p1, p2);
		assertFalse(sensation.overlaps(b));
		
		b = new BBox(p3);
		assertTrue(sensation.overlaps(b));
	}
	
	@Test
	public void testIntersection(){
		Point p1 = new Point(2.f, 3.f, 4.f);
		Point p2 = new Point(-2.f, -3.f, -4.f);
		BBox b = new BBox(p1, p2);
		
		BBox c = BBox.intersection(sensation, b);
		assertTrue(c.getMax().getX() == 2.f);
		assertTrue(c.getMax().getY() == 3.f);
		assertTrue(c.getMax().getZ() == 4.f);
		assertTrue(c.getMin().getX() == -2.f);
		assertTrue(c.getMin().getY() == 5.f);
		assertTrue(c.getMin().getZ() == -4.f);
		
		sensation.intersection(b);
		assertTrue(c.getMax().getX() == 2.f);
		assertTrue(c.getMax().getY() == 3.f);
		assertTrue(c.getMax().getZ() == 4.f);
		assertTrue(c.getMin().getX() == -2.f);
		assertTrue(c.getMin().getY() == 5.f);
		assertTrue(c.getMin().getZ() == -4.f);
	}
	
	@Test 
	public void testIsEmpty(){
		assertFalse(sensation.isEmpty());
		
		sensation.getMax().setX(-11.f);
		sensation.getMax().setY(-11.f);
		sensation.getMax().setZ(-16.f);
		assertTrue(sensation.isEmpty());
	}

	@Test
	public void testExpand(){
		float f = 2.f;
		sensation.expand(f);
		assertTrue(sensation.getMax().getX() == 12.f);
		assertTrue(sensation.getMax().getY() == 22.f);
		assertTrue(sensation.getMax().getZ() == 17.f);
		assertTrue(sensation.getMin().getX() == -12.f);
		assertTrue(sensation.getMin().getY() == 3.f);
		assertTrue(sensation.getMin().getZ() == -17.f);
	}

	@Test
	public void testGetCenter(){
		try {
			sensation.getCenter(-1);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }
		
		try {
			sensation.getCenter(3);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }
		
		try {
			assertTrue(sensation.getCenter(0) == 0.f);
			assertTrue(sensation.getCenter(1) == 12.5f);
			assertTrue(sensation.getCenter(2) == 0.f);
		} catch (Exception e) { fail("Exception throw"); }
	}

	@Test
	public void testVolume(){
		assertTrue(sensation.volume() == 9000.0f);
	}

	@Test
	public void testArea(){
		assertTrue(sensation.surfaceArea() == 2700.f);
	}

	@Test
	public void testMaxExtent(){
		assertTrue(sensation.maxExtent() == 2);
	}

	@Test
	public void testGet(){
		try {
			sensation.get(-1);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }
		
		try {
			assertEquals(sensation.get(0), sensation.getMin());
			assertEquals(sensation.get(1), sensation.getMax());
		} catch (Exception e1) {
			fail("Exception throw");
		}
		
		try {
			sensation.get(2);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }
	}
}
