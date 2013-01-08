package lp.iem.gk.test;

import lp.iem.gk.Point2;

import static org.junit.Assert.*;
import org.junit.*;

public class Point2Test {
	
	static final float startx = 2.f;
	static final float starty = 3.f;
	
	static Point2 point;
	
	@BeforeClass
    public static void setUpClass() throws Exception {
		point = new Point2(startx, starty);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
		point.setX(startx);
		point.setY(starty);
    }
    
    @Test
    public void testGet(){
    	try {
			float x = point.get(0);
			assertTrue(startx == x);
		} catch (Exception e) {	fail(e.getMessage()); }
    	
    	try {
    		float y = point.get(1);
    		assertTrue(starty == y);
    	}catch (Exception e) { fail(e.getMessage()); }
    	
    	try { 
    		point.get(-1);
    		fail("Exception don't throw");
		} catch (Exception e) {	assertTrue(true); }
    	
    	try {
			point.get(3);
			fail("Exception don't throw");
    	} catch (Exception e) {	assertTrue(true); }
    	
    }
    
	@Test
	public void testAdditionVector() {
		Point2 p1 = new Point2(1.f, 3.f);
		Point2 actual = point.addition(p1);
		Point2 expected = new Point2(3.f, 6.f);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAddition() {
		Point2 p1 = new Point2(1.f, 3.f);
		point.add(p1);
		Point2 expected = new Point2(3.f, 6.f);
		assertEquals(expected, point);
	}
	
	@Test
	public void testSubtractionVector() {
		Point2 p1 = new Point2(1.f, 2.f);
		Point2 actual = point.subtraction(p1);
		Point2 expected = new Point2(1.f, 1.f);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSubtraction() {
		Point2 p1 = new Point2(1.f, 2.f);
		point.substract(p1);
		Point2 expected = new Point2(1.f, 1.f);
		assertEquals(expected, point);
	}
	
	@Test
	public void testProductVector() {
		Point2 actual = point.productPoint2(2.f);
		Point2 expected = new Point2(4.f, 6.f);
		assertEquals(expected, actual);
	}

	@Test
	public void testProduct() {
		point.product(2.f);
		Point2 expected = new Point2(4.f, 6.f);
		assertEquals(expected, point);
	}
	
	@Test
	public void testDivision(){
		Point2 actual = null;
		try { actual = point.division(2.f); } catch (Exception e) { }
		Point2 expected = new Point2(1.f, 1.5f);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDivide(){
		try { point.divide(2.f); } catch (Exception e) {	}
		Point2 expected = new Point2(1.f, 1.5f);
		assertEquals(expected, point);
	}
	
	@Test
	public void testNegation(){
		Point2 actual = point.negation();
		Point2 expected = new Point2(-2.f, -3.f);
		assertEquals(expected, actual);
	}

	@Test
	public void testLengthSquared(){
		float actual = point.lengthSquared();
		float expected = 13.f;
		assertTrue(actual == expected);
	}
	
	@Test
	public void testLength(){
		point.setPosition(0.f, 2.f);
		float actual = point.length();
		float expected = 2.f;
		assertTrue(actual == expected);
	}
}
