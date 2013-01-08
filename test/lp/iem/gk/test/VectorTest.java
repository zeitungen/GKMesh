package lp.iem.gk.test;

import lp.iem.gk.Vector;

import org.junit.*;

import static org.junit.Assert.*;

public class VectorTest {
	
	static final float startx = 2.f;
	static final float starty = 3.f;
	static final float startz = 5.f;
	
	static Vector vector;
	
	@BeforeClass
	public static void setUpClass() throws Exception{
		vector = new Vector(startx, starty, startz);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception{
	}

	@Before
	public void setUp(){
	}

	@After
	public void tearDown(){
		vector.setPosition(startx,starty, startz);
	}
	
	
    @Test
    public void testGet(){
    	try {
			float x = vector.get(0);
			assertTrue(startx == x);
		} catch (Exception e) {	fail(e.getMessage()); }
    	
    	try {
    		float y = vector.get(1);
    		assertTrue(starty == y);
    	}catch (Exception e) { fail(e.getMessage()); }
    	
    	try {
    		float z = vector.get(2);
    		assertTrue(startz == z);
    	}catch (Exception e) { fail(e.getMessage()); }
    	
    	try { 
    		vector.get(-1);
    		fail("Exception don't throw");
		} catch (Exception e) {	assertTrue(true); }
    	
    	try {
    		vector.get(4);
			fail("Exception don't throw");
    	} catch (Exception e) {	assertTrue(true); }
    	
    }
	
	@Test
	public void testAddition(){
		Vector v1 = new Vector(1.f,2.f,3.f);
		Vector actual = vector.addition(v1);
		Vector expected = new Vector(3.f,5.f,8.f);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAdd(){
		Vector v1 = new Vector(1.f,2.f,3.f);
		vector.add(v1);
		Vector expected = new Vector(3.f,5.f,8.f);
		assertEquals(expected, vector);
	}
	
	@Test
	public void testSubtraction(){
		Vector v1 = new Vector(1.f,2.f,3.f);
		Vector actual = vector.subtraction(v1);
		Vector expected = new Vector(1.f,1.f,2.f);
		assertEquals(expected, actual);
	}

	@Test
	public void testSubstract(){
		Vector v1 = new Vector(1.f,2.f,3.f);
		vector.substract(v1);
		Vector expected = new Vector(1.f,1.f,2.f);
		assertEquals(expected, vector);
	}
	
	@Test
	public void testProductVector(){
		Vector actual = vector.productVector(2.f);
		Vector expected = new Vector(4.f,6.f,10.f);
		assertEquals(expected, actual);
	}

	@Test
	public void testProduct(){
		vector.product(2.f);
		Vector expected = new Vector(4.f,6.f,10.f);
		assertEquals(expected, vector);
	}

	@Test
	public void testDivision(){
		vector.setPosition(startx, 4.f, 10.f);
		Vector actual = null;
		try { actual = vector.division(2.f); } catch (Exception e) { }
		Vector expected = new Vector(1.f,2.f,5.f);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDivid(){
		vector.setPosition(startx, 4.f, 10.f);
		try { vector.divid(2.f); } catch (Exception e) { }
		Vector expected = new Vector(1.f,2.f,5.f);
		assertEquals(expected, vector);
	}

	@Test
	public void testNegation(){
		Vector actual = vector.negation();
		Vector expected = new Vector(-2.f,-3.f,-5.f);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLengthSquared(){
		float actual = vector.lengthSquared();
		float expected = 38.f;
		assertTrue(expected == actual);
	}

	@Test
	public void testLength(){
		vector.setPosition(1.f, 2.f, 2.f);
		float actual = vector.length();
		float expected = 3.f;
		assertTrue(expected == actual);
	}
}
