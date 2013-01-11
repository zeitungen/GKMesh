package lp.iem.gk.test;

import static org.junit.Assert.*;
import lp.iem.gk.BBox;
import lp.iem.gk.Matrix4x4;
import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.gk.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Matrix4x4Test {

	static final float PRECISION = 0.0001f;
	
	static Matrix4x4 neo;
	
	@BeforeClass
	public static void setUpClass() throws Exception{
		neo = new Matrix4x4();
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception{
	}

	@Before
	public void setUp(){
	}

	@After
	public void tearDown(){
		neo = new Matrix4x4();
	}
	
	@Test
	public void testConstructor(){
		neo = new Matrix4x4();
		try {
			assertTrue(neo.get(0,0) == 1.f); assertTrue(neo.get(0,1) == 0.f); assertTrue(neo.get(0,2) == 0.f); assertTrue(neo.get(0,3) == 0.f);
			assertTrue(neo.get(1,0) == 0.f); assertTrue(neo.get(1,1) == 1.f); assertTrue(neo.get(1,2) == 0.f); assertTrue(neo.get(1,3) == 0.f);
			assertTrue(neo.get(2,0) == 0.f); assertTrue(neo.get(2,1) == 0.f); assertTrue(neo.get(2,2) == 1.f); assertTrue(neo.get(2,3) == 0.f);
			assertTrue(neo.get(3,0) == 0.f); assertTrue(neo.get(3,1) == 0.f); assertTrue(neo.get(3,2) == 0.f); assertTrue(neo.get(3,3) == 1.f);
		} catch (Exception e) { fail("Exception throw"); }
		
		neo = new Matrix4x4(0.f,1.f,2.f,3.f,   4.f,5.f,6.f,7.f,
							8.f,9.f,10.f,11.f, 12.f,13.f,14.f,15.f);
		try {
			assertTrue(neo.get(0,0) == 0.f); assertTrue(neo.get(0,1) == 1.f); assertTrue(neo.get(0,2) == 2.f); assertTrue(neo.get(0,3) == 3.f);
			assertTrue(neo.get(1,0) == 4.f); assertTrue(neo.get(1,1) == 5.f); assertTrue(neo.get(1,2) == 6.f); assertTrue(neo.get(1,3) == 7.f);
			assertTrue(neo.get(2,0) == 8.f); assertTrue(neo.get(2,1) == 9.f); assertTrue(neo.get(2,2) == 10.f); assertTrue(neo.get(2,3) == 11.f);
			assertTrue(neo.get(3,0) == 12.f); assertTrue(neo.get(3,1) == 13.f); assertTrue(neo.get(3,2) == 14.f); assertTrue(neo.get(3,3) == 15.f);
		} catch (Exception e) { fail("Exception throw"); }
		
		float[][] tabs = new float[3][4];
		try {
			neo = new Matrix4x4(tabs);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }
		
		tabs = new float[5][4];
		try {
			neo = new Matrix4x4(tabs);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }

		tabs = new float[4][3];
		try {
			neo = new Matrix4x4(tabs);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }
		
		tabs = new float[4][5];
		try {
			neo = new Matrix4x4(tabs);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true); }
		
		tabs = new float[4][4];
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				tabs[i][j] = (float)i+j;
		try {
			neo = new Matrix4x4(tabs);
			assertTrue(neo.get(0,0) == 0.f); assertTrue(neo.get(0,1) == 1.f); assertTrue(neo.get(0,2) == 2.f); assertTrue(neo.get(0,3) == 3.f);
			assertTrue(neo.get(1,0) == 1.f); assertTrue(neo.get(1,1) == 2.f); assertTrue(neo.get(1,2) == 3.f); assertTrue(neo.get(1,3) == 4.f);
			assertTrue(neo.get(2,0) == 2.f); assertTrue(neo.get(2,1) == 3.f); assertTrue(neo.get(2,2) == 4.f); assertTrue(neo.get(2,3) == 5.f);
			assertTrue(neo.get(3,0) == 3.f); assertTrue(neo.get(3,1) == 4.f); assertTrue(neo.get(3,2) == 5.f); assertTrue(neo.get(3,3) == 6.f);
		} catch (Exception e) { fail("Exception throw"); }
		
	}

	@Test
	public void testTranspose(){
		neo = new Matrix4x4(0.f,1.f,2.f,3.f,   4.f,5.f,6.f,7.f,
				8.f,9.f,10.f,11.f, 12.f,13.f,14.f,15.f);
		Matrix4x4 m = neo.transpose();
		try {
			assertTrue(m.get(0,0) == 0.f); assertTrue(m.get(0,1) == 4.f); assertTrue(m.get(0,2) == 8.f); assertTrue(m.get(0,3) == 12.f);
			assertTrue(m.get(1,0) == 1.f); assertTrue(m.get(1,1) == 5.f); assertTrue(m.get(1,2) == 9.f); assertTrue(m.get(1,3) == 13.f);
			assertTrue(m.get(2,0) == 2.f); assertTrue(m.get(2,1) == 6.f); assertTrue(m.get(2,2) == 10.f); assertTrue(m.get(2,3) == 14.f);
			assertTrue(m.get(3,0) == 3.f); assertTrue(m.get(3,1) == 7.f); assertTrue(m.get(3,2) == 11.f); assertTrue(m.get(3,3) == 15.f);
		} catch (Exception e) { fail("Exception throw"); }
	}

	@Test
	public void testGetColumn(){
		try {
			float[] f = neo.getColumn(0);
			assertTrue(f[0] == 1.f); assertTrue(f[1] == 0.f);
			assertTrue(f[2] == 0.f); assertTrue(f[3] == 0.f);
			f = neo.getColumn(3);
			assertTrue(f[0] == 0.f); assertTrue(f[1] == 0.f);
			assertTrue(f[2] == 0.f); assertTrue(f[3] == 1.f);
		} catch (Exception e) { fail("Exception throw"); }
		
		try {
			neo.getColumn(-1);
			fail("Exception don't Throw");
		} catch (Exception e) { assertTrue(true); }
		
		try {
			neo.getColumn(4);
			fail("Exception don't Throw");
		} catch (Exception e) { assertTrue(true); }
	}
	
	@Test
	public void testGetRow(){
		try {
			float[] f = neo.getRow(0);
			assertTrue(f[0] == 1.f); assertTrue(f[1] == 0.f);
			assertTrue(f[2] == 0.f); assertTrue(f[3] == 0.f);
			f = neo.getRow(3);
			assertTrue(f[0] == 0.f); assertTrue(f[1] == 0.f);
			assertTrue(f[2] == 0.f); assertTrue(f[3] == 1.f);
		} catch (Exception e) { fail("Exception throw"); }
		
		try {
			neo.getRow(-1);
			fail("Exception don't Throw");
		} catch (Exception e) { assertTrue(true); }
		
		try {
			neo.getRow(4);
			fail("Exception don't Throw");
		} catch (Exception e) { assertTrue(true); }
	}

	@Test
	public void testGetRotationVector(){
		try {
			Vector actual = neo.getRotationVector(0);
			Vector expected = new Vector(1.f, 0.f, 0.f);
			assertEquals(expected, actual);
			actual = neo.getRotationVector(2);
			expected = new Vector(0.f, 0.f, 1.f);
			assertEquals(expected, actual);
		} catch (Exception e) { fail("Exception throw"); }
		
		try {
			neo.getRotationVector(-1);
			fail("Exception don't Throw");
		} catch (Exception e) { assertTrue(true); }
		
		try {
			neo.getRotationVector(3);
			fail("Exception don't Throw");
		} catch (Exception e) { assertTrue(true); }
	}

	public void testGetTranslationVector(){
		Vector actual = neo.getTranslationVector();
		Vector expected = new Vector(0.f, 0.f, 0.f);
		assertEquals(expected, actual);
	}

	@Test
	public void testProduct(){
		Matrix4x4 m1 = new Matrix4x4(0.f,1.f,2.f,3.f,   4.f,5.f,6.f,7.f,
				8.f,9.f,10.f,11.f, 12.f,13.f,14.f,15.f);
		Matrix4x4 m2 = new Matrix4x4(0.f,1.f,2.f,3.f,   4.f,5.f,6.f,7.f,
				8.f,9.f,10.f,11.f, 12.f,13.f,14.f,15.f);
		Matrix4x4 m = Matrix4x4.product(m1, m2);
		try {
			assertTrue(m.get(0,0) == 56.f); assertTrue(m.get(0,1) == 62.f); assertTrue(m.get(0,2) == 68.f); assertTrue(m.get(0,3) == 74.f);
			assertTrue(m.get(1,0) == 152.f); assertTrue(m.get(1,1) == 174.f); assertTrue(m.get(1,2) == 196.f); assertTrue(m.get(1,3) == 218.f);
			assertTrue(m.get(2,0) == 248.f); assertTrue(m.get(2,1) == 286.f); assertTrue(m.get(2,2) == 324.f); assertTrue(m.get(2,3) == 362.f);
			assertTrue(m.get(3,0) == 344.f); assertTrue(m.get(3,1) == 398.f); assertTrue(m.get(3,2) == 452.f); assertTrue(m.get(3,3) == 506.f);
		} catch (Exception e) { fail("Exception throw"); }
		
	}

	@Test
	public void testTransform(){
		Vector v = new Vector(1.f, 2.f, 3.f);
		Matrix4x4 m = new Matrix4x4(0.f,1.f,2.f,3.f,   4.f,5.f,6.f,7.f,   8.f,9.f,10.f,11.f,   12.f,13.f,14.f,15.f);
		Vector actual = Matrix4x4.transform(m, v);
		Vector expected = new Vector(8.f, 32.f, 56.f);
		assertEquals(expected, actual);
		
		Point p = new Point(1.f, 2.f, 3.f);
		try {
			Point ac = Matrix4x4.transform(m, p);
			Point ex = new Point(11.f/95.f, 39.f/95.f, 67.f/95.f);
			assertEquals(ex.getX(), ac.getX(), PRECISION);
			assertEquals(ex.getY(), ac.getY(), PRECISION);
			assertEquals(ex.getZ(), ac.getZ(), PRECISION);
		} catch (Exception e) {	fail("Exception throw"); }
		
		Normal n = new Normal(1.f, 2.f, 3.f);
		Normal act = Matrix4x4.transform(m, n);
		Normal exp = new Normal(32.f, 38.f, 44.f);
		assertEquals(exp, act);
		
		BBox b = new BBox(new Point(1.f,1.f,1.f), new Point(2.f,2.f,2.f));
		BBox c = Matrix4x4.transform(m, b);
		assertEquals(c.getMin().getX(), 0.09677419f, PRECISION);	assertEquals(c.getMin().getY(), 0.39784947f, PRECISION);
		assertEquals(c.getMin().getZ(), 0.6989247f, PRECISION);		assertEquals(c.getMax().getX(), 0.09677419f, PRECISION);
		assertEquals(c.getMax().getY(), 0.39784947f, PRECISION);	assertEquals(c.getMax().getZ(), 0.6989247f, PRECISION);
	}

	@Test
	public void testInverse(){
		
		Matrix4x4 a = new Matrix4x4(1.f, 3.f, 6.f, 1.f,  5.f, 2.f, 3.f, 12.f,   2.f, 0.f, 15.f, 5.f,  2.f,7.f,0.f,11.f);
		try {
			Matrix4x4 m = a.getInverse();
			assertEquals(m.get(0,0), .44f, PRECISION); 		assertEquals(m.get(0,1), .320f, PRECISION); 	assertEquals(m.get(0,2), -.24f, PRECISION);
			assertEquals(m.get(0,3), -.28f, PRECISION);		assertEquals(m.get(1,0), .22880f, PRECISION); 	assertEquals(m.get(1,1), -.03360f, PRECISION); 	
			assertEquals(m.get(1,2), -.08480f, PRECISION); 	assertEquals(m.get(1,3), .0544f, PRECISION); 	assertEquals(m.get(2,0), .01653f, PRECISION); 	
			assertEquals(m.get(2,1), -.03040f, PRECISION); 	assertEquals(m.get(2,2), .06613f, PRECISION); 	assertEquals(m.get(2,3), .00160f, PRECISION);
			assertEquals(m.get(3,0), -.22560f, PRECISION);	assertEquals(m.get(3,1), -.0368f, PRECISION); 	assertEquals(m.get(3,2), .0976f, PRECISION); 
			assertEquals(m.get(3,3), .1072f, PRECISION);
		} catch (Exception e) { fail("Exception throw"); }
		
		a = new Matrix4x4(0.f,1.f,2.f,3.f,   4.f,5.f,6.f,7.f,   8.f,9.f,10.f,11.f,   12.f,13.f,14.f,15.f);
		try {
			Matrix4x4 m = a.getInverse();
			System.out.println(a);
			fail("Exception don't throw");
		} catch (Exception e) { assertTrue(true);  }
	}

	@Test
	public void testEquals(){
		Matrix4x4 m1 = new Matrix4x4();
		Matrix4x4 m2 = new Matrix4x4();
		assertTrue(m1.equals(m2));
		
		Matrix4x4 m3 = new Matrix4x4();
		try { m3.set(0, 0, 0.f); } catch (Exception e) { }
		assertFalse(m1.equals(m3));
	}
}
