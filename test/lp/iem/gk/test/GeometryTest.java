package lp.iem.gk.test;

import static org.junit.Assert.*;
import lp.iem.gk.Geometry;
import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.gk.Vector;

import org.junit.Test;

public class GeometryTest {
	public static final float precis = 0.001f;

	@Test
	public void testCrossVectVect() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(4, 6, 2);

		Vector v3 = Geometry.cross(v1, v2);
		assertEquals(v3, new Vector(-14, 10, -2));
	}

	@Test
	public void testCrossVectNorm() {
		Vector v1 = new Vector(1, 2, 3);
		Normal v2 = new Normal(4, 6, 2);

		Vector v3 = Geometry.cross(v1, v2);
		assertEquals(v3, new Vector(-14, 10, -2));
	}

	@Test
	public void testCrossNormVect() {
		Vector v1 = new Vector(1, 2, 3);
		Normal v2 = new Normal(4, 6, 2);

		Vector v3 = Geometry.cross(v2, v1);
		assertEquals(v3, new Vector(14, -10, 2));
	}

	@Test
	public void testDotVectVect() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(4, 6, 2);

		float prodScal = Geometry.dot(v1, v2);
		assertTrue(prodScal == 22);
	}

	@Test
	public void testAbsDot() {
		Vector v1 = new Vector(-1, -2, -3);
		Vector v2 = new Vector(4, 6, 2);

		float prodScal = Geometry.absDot(v1, v2);
		assertTrue(prodScal == 22);
	}

	@Test
	public void testZeroDot() {
		Vector v1 = new Vector(-1, -2, -3);
		Vector v2 = new Vector(4, 6, 2);
		Vector v3 = new Vector(1, 2, 3);

		float prodScal = Geometry.zeroDot(v1, v2);
		assertTrue(prodScal == 0);

		float prodScal2 = Geometry.zeroDot(v3, v2);
		assertTrue(prodScal2 == 22);
	}

	@Test
	public void testNormalizeVector() throws Exception {
		Vector v1 = new Vector(-1, -2, -3);

		Vector res = Geometry.normalize(v1);

		assertEquals("test Normalize", 1.f, res.length(), precis);
	}

	@Test
	public void testCoordinate() {
		Vector v1 = new Vector(-1, -2, -3);
		Vector v2 = new Vector(4, 6, 2);
		Vector v3 = new Vector(1, 2, 3);

		Geometry.coordinateSystem(v1, v2, v3);

	}

	@Test
	public void testDistance() {
		Point p1 = new Point(5, 3, 6);
		Point p2 = new Point(1, 4, 2);

		float dist = Geometry.distance(p1, p2);
		assertEquals("test distance", Math.sqrt(33), dist, precis);

	}

	@Test
	public void testDistanceSquared() {
		Point p1 = new Point(5, 3, 6);
		Point p2 = new Point(1, 4, 2);

		float dist = Geometry.distanceSquared(p1, p2);
		assertEquals("test distanceSquared", 33, dist, precis);

	}

	@Test
	public void testProductPointFloat() {
		Point p = new Point(10, 5, 6);

		Point p2 = Geometry.productPointFloat(3, p);
		assertTrue(p2.getX() == 30);
		assertTrue(p2.getY() == 15);
		assertTrue(p2.getZ() == 18);

	}

	@Test
	public void testProductNormFloat() {
		Normal p = new Normal(10, 5, 6);

		Normal p2 = Geometry.productNormFloat(3, p);

		assertTrue(p2.getX() == 30);
		assertTrue(p2.getY() == 15);
		assertTrue(p2.getZ() == 18);

	}

	@Test
	public void testNormalizeNorm() throws Exception {
		Normal v1 = new Normal(-1, -2, -3);

		Normal res = Geometry.normalize(v1);

		assertEquals("test Normalize", 1.f, res.length(), precis);
	}

	@Test
	public void testDotNormalVector() {
		Normal n = new Normal(-1, -2, -3);
		Vector v = new Vector(4, 6, 2);

		float res = Geometry.dot(n, v);

		assertTrue(res == -22);
	}

	@Test
	public void testDotNormalNormal() {
		Normal n = new Normal(10, 8, 6);
		Normal v = new Normal(2, 1, 0);

		float res = Geometry.dot(n, v);

		assertTrue(res == 28);
	}

	@Test
	public void testAbsDotNormVect() {
		Normal v1 = new Normal(-1, -2, -3);
		Vector v2 = new Vector(4, 6, 2);

		float prodScal = Geometry.absDot(v1, v2);
		assertTrue(prodScal == 22);
	}

	@Test
	public void testAbsDotNormNorm() {
		Normal n = new Normal(10, 8, 6);
		Normal v = new Normal(2, 1, 0);

		float prodScal = Geometry.absDot(n, v);
		assertTrue(prodScal == 28);
	}

	@Test
	public void testZeroDotNormVect() {
		Vector v1 = new Vector(-1, -2, -3);
		Normal v2 = new Normal(4, 6, 2);
		Vector v3 = new Vector(1, 2, 3);

		float prodScal = Geometry.zeroDot(v1, v2);
		assertTrue(prodScal == 0);

		float prodScal2 = Geometry.zeroDot(v3, v2);
		assertTrue(prodScal2 == 22);
	}

	@Test
	public void testZeroDotNormNorm() {
		Normal v1 = new Normal(-1, -2, -3);
		Normal v2 = new Normal(4, 6, 2);
		Normal v3 = new Normal(1, 2, 3);

		float prodScal = Geometry.zeroDot(v1, v2);
		assertTrue(prodScal == 0);

		float prodScal2 = Geometry.zeroDot(v3, v2);
		assertTrue(prodScal2 == 22);
	}

	@Test
	public void testLerp() {
		float lerp = Geometry.lerp(-3, 2, 9);
		assertTrue(lerp == -19);
	}

	@Test
	public void testClampFloat() {
		float clamp = Geometry.clamp((float) -3.5, (float) 2.1, (float) 9.7);
		assertTrue(clamp == (float) 2.1);

		float clamp2 = Geometry.clamp((float) 10.5, (float) 2.1, (float) 9.7);
		assertTrue(clamp2 == (float) 9.7);

		float clamp3 = Geometry.clamp((float) 5.1, (float) 2.3, (float) 9.85);
		assertTrue(clamp3 == (float) 5.1);
	}

	public void testClampInt() {
		float clamp = Geometry.clamp(-3, 2, 9);
		assertTrue(clamp == 2);

		float clamp2 = Geometry.clamp(10, 2, 9);
		assertTrue(clamp2 == 9);

		float clamp3 = Geometry.clamp(5, 2, 9);
		assertTrue(clamp3 == 5);
	}

	@Test
	public void testRadDeg() {

	}

	@Test
	public void testSphericalDrirection() {
		Vector v = Geometry.sphericalDirection((float) 10.5, (float) 12.3, 0);
		assertTrue(v.getX() == (float) 10.5); // 10.5 * cos(0) = 10.5 * 1
		assertTrue(v.getY() == (float) 0); // 12.3 * sin(0) = 12.3 * 0
		assertTrue(v.getZ() == (float) 12.3);

	}

	@Test
	public void testSphericalDrirection2() {
		Vector x = new Vector(1, 1, 1);
		Vector y = new Vector(1, 0, 0);
		Vector z = new Vector(0, 0, 1);

		Vector res = Geometry.sphericalDirection(3, 4, 0, x, y, z);

		assertTrue(res.getX() == 3);
		assertTrue(res.getY() == 3);
		assertTrue(res.getZ() == 7);

	}

	@Test
	public void testSphericalTheta() {
		Vector v = new Vector(3, 4, 0);
		float res = Geometry.sphericalTheta(v);
		res = Geometry.radians(res);
		assertEquals(0, res, 0.1);

	}

	@Test
	public void testSphericalPhi() {
		Vector v = new Vector(2, 6, 8);
		assertEquals((float) 1.24, Geometry.sphericalPhi(v), 0.1);
	}

	@Test
	public void testRound(){
		assertTrue(1.f == Geometry.round(0.99999f));
		assertTrue(1.f == Geometry.round(1.00001f));
		assertTrue(.93f == Geometry.round(0.92999f));
		assertTrue(.93f == Geometry.round(0.930001f));
		assertTrue(.9342f == Geometry.round(0.934201f));
		assertTrue(.9342f == Geometry.round(0.934199f));
	}
}
