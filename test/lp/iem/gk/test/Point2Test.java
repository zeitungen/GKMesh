package lp.iem.gk.test;

import static org.junit.Assert.*;
import lp.iem.gk.Point2;

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
}
