package lp.iem.gk.test;

import static org.junit.Assert.*;
import lp.iem.gk.BBox;
import lp.iem.gk.Geometry;
import lp.iem.gk.Matrix4x4;
import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.gk.Ray;
import lp.iem.gk.Transform;
import lp.iem.gk.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransformTest {

	static final float PRECISION = 0.0001f;
	
	static Transform trans;
	static Matrix4x4 a, ainv;
	
	@BeforeClass
	public static void setUpClass(){
		a = new Matrix4x4(1.f, 3.f, 6.f, 1.f,  5.f, 2.f, 3.f, 12.f,   2.f, 0.f, 15.f, 5.f,  2.f,7.f,0.f,11.f);
		try{ trans = new Transform(a); ainv = a.getInverse(); }catch(Exception e){ }
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception{
	}

	@Before
	public void setUp(){
	}

	@After
	public void tearDown(){ setUpClass(); }

	
	
	@Test
	public void testConstructor(){
		trans = new Transform();
		Matrix4x4 witness = new Matrix4x4(); // identity matrix
		assertEquals(witness, trans.getGLTransposeMatrix());
		assertEquals(witness, trans.inverseMatrix());
		
		try {
			trans = new Transform(a);
			assertEquals(a, trans.getGLTransposeMatrix());
			assertEquals(ainv, trans.inverseMatrix());
			
			trans = new Transform(a.getMatrix());
			assertEquals(a, trans.getGLTransposeMatrix());
			assertEquals(ainv, trans.inverseMatrix());
			
			trans = new Transform(a, ainv);
			assertEquals(a, trans.getGLTransposeMatrix());
			assertEquals(ainv, trans.inverseMatrix());
		} catch (Exception e) { fail("Exception throw"); }
	}
	
	@Test
	public void testIdentity(){
		trans = new Transform();
		Matrix4x4 witness = new Matrix4x4(); // identity matrix
		assertEquals(witness, trans.getGLTransposeMatrix());
		assertEquals(witness, trans.inverseMatrix());
	}

	@Test
	public void testTranspose(){
		trans = new Transform();
		assertEquals(trans.getGLTransposeMatrix().transpose(), trans.transposeMatrix());
		assertEquals(trans.inverseMatrix().transpose(), trans.normalMatrix());
	}
	
	@Test
	public void testGetInverse(){
		Transform t = trans.getInverse();
		assertEquals(ainv, t.getGLTransposeMatrix());
		assertEquals(a, t.inverseMatrix());
	}

	@Test
	public void testTransform(){
		Vector v = new Vector(1.f, 2.f, 3.f);
		Vector actual = trans.transform(v);
		Vector expected = new Vector(25.f, 18.f, 47.f);
		assertEquals(expected, actual);
		
		Point p = new Point(1.f, 2.f, 3.f);
		try {
			Point ac = trans.transform(p);
			Point ex = new Point(.96296f, 1.11111f, 1.9259f);
			assertEquals(ex.getX(), ac.getX(), PRECISION);
			assertEquals(ex.getY(), ac.getY(), PRECISION);
			assertEquals(ex.getZ(), ac.getZ(), PRECISION);
		} catch (Exception e) {	fail("Exception throw"); }
		
		Normal n = new Normal(1.f, 2.f, 3.f);
		Normal act = trans.transform(n);
		Normal exp = new Normal(17.f, 7.f, 57.f);
		assertEquals(exp, act);
		
		BBox b = new BBox(new Point(1.f,1.f,1.f), new Point(2.f,2.f,2.f));
		BBox c = trans.transform(b);
		assertEquals(c.getMin().getX(), .7241379f, PRECISION);	assertEquals(c.getMin().getY(), 1.1034483f, PRECISION);
		assertEquals(c.getMin().getZ(), 1.3448275f, PRECISION);	assertEquals(c.getMax().getX(), 0.7241379f, PRECISION);
		assertEquals(c.getMax().getY(), 1.1034483f, PRECISION);	assertEquals(c.getMax().getZ(), 1.3448275f, PRECISION);
	}
	
	@Test
	public void testInverse(){
		Vector v = new Vector(1.f, 2.f, 3.f);
		Vector actual = trans.inverse(v);
		Vector expected = new Vector(0.3599999f, -0.09279999f,0.15413333f);
		assertEquals(expected, actual);
		
		Point p = new Point(1.f, 2.f, 3.f);
		try {
			Point ac = trans.inverse(p);
			Point ex = new Point(.7936496f, -0.38095224f, 1.5449733f);
			assertEquals(ex.getX(), ac.getX(), PRECISION);
			assertEquals(ex.getY(), ac.getY(), PRECISION);
			assertEquals(ex.getZ(), ac.getZ(), PRECISION);
		} catch (Exception e) {	fail("Exception throw"); }
		
		Normal n = new Normal(1.f, 2.f, 3.f);
		Normal act = trans.inverse(n);
		Normal exp = new Normal(.9472f, 0.1616f, -0.21120001f);
		assertEquals(exp, act);
		
		BBox b = new BBox(new Point(1.f,1.f,1.f), new Point(2.f,2.f,2.f));
		BBox c = trans.inverse(b);
		assertEquals(c.getMin().getX(), -3.4172661f, PRECISION);	assertEquals(c.getMin().getY(), -1.2374101f, PRECISION);
		assertEquals(c.getMin().getZ(), -0.4772182f, PRECISION);	assertEquals(c.getMax().getX(), -3.4172661f, PRECISION);
		assertEquals(c.getMax().getY(), -1.2374101f, PRECISION);		assertEquals(c.getMax().getZ(), -0.4772182f, PRECISION);
	}

	@Test
	public void testTransformRay(){
		Point origin = new Point();
		Vector direction = new Vector(2.f, 1.f, 2.f);
		try{
			Ray r = new Ray(origin, direction);
			Ray ract = trans.transform(r);
			Point pexp = trans.transform(origin);
			Vector vexp = trans.transform(direction);
			assertEquals(pexp, ract.getOrigin());
			assertEquals(vexp, ract.getDirection());
		}catch(Exception e){ fail("Exception throw"); }
	}

	@Test
	public void testInverseRay(){
		Point origin = new Point();
		Vector direction = new Vector(2.f, 1.f, 2.f);
		try{
			Ray r = new Ray(origin, direction);
			Ray ract = trans.inverse(r);
			Point pexp = trans.inverse(origin);
			Vector vexp = trans.inverse(direction);
			assertEquals(pexp, ract.getOrigin());
			assertEquals(vexp, ract.getDirection());
		}catch(Exception e){ fail("Exception throw"); }
	}

	@Test
	public void testTranslate(){
		Vector delta = new Vector();
		Matrix4x4 m = new Matrix4x4(1.f, 0.f, 0.f, delta.getX(),
									0.f, 1.f, 0.f, delta.getY(),
									0.f, 0.f, 1.f, delta.getZ(),
									0.f, 0.f, 0.f, 1.f);
		Matrix4x4 minv = new Matrix4x4(1.f, 0.f, 0.f, -delta.getX(),
									   0.f, 1.f, 0.f, -delta.getY(),
									   0.f, 0.f, 1.f, -delta.getZ(),
									   0.f, 0.f, 0.f, 1.f);
		Transform t = Transform.translate(delta);
		assertEquals(m, t.getGLTransposeMatrix());
		assertEquals(minv, t.inverseMatrix());
	}

	@Test
	public void testScale(){
		float x = 1.f, y = 2.f, z = 4.f;
		Matrix4x4 m = new Matrix4x4(x,   0.f, 0.f, 0.f,
									0.f, y,   0.f, 0.f,
									0.f, 0.f, z,   0.f,
									0.f, 0.f, 0.f, 1.f);
		Matrix4x4 minv = new Matrix4x4(1.f/x, 0.f,   0.f,   0.f,
									   0.f,   1.f/y, 0.f,   0.f,
									   0.f,   0.f,   1.f/z, 0.f,
									   0.f,   0.f,   0.f,   1.f);
		Transform t = Transform.scale(x, y, z);
		assertEquals(m, t.getGLTransposeMatrix());
		assertEquals(minv, t.inverseMatrix());
		
		m = new Matrix4x4(x,   0.f, 0.f, 0.f,
						0.f, x,   0.f, 0.f,
						0.f, 0.f, x,   0.f,
						0.f, 0.f, 0.f, 1.f);
		minv = new Matrix4x4(1.f/x, 0.f,   0.f,   0.f,
					   0.f,   1.f/x, 0.f,   0.f,
					   0.f,   0.f,   1.f/x, 0.f,
					   0.f,   0.f,   0.f,   1.f);
		
		t = Transform.scale(x);
		assertEquals(m, t.getGLTransposeMatrix());
		assertEquals(minv, t.inverseMatrix());
	}

	@Test
	public void testRotateXYZ(){
		float angle = 45.f;
		float sint = (float) Math.sin(Geometry.radians(angle));
		float cost = (float) Math.cos(Geometry.radians(angle));
		Matrix4x4 m = new Matrix4x4(1.f, 0.f,  0.f,   0.f,
									0.f, cost, -sint, 0.f,
									0.f, sint, cost,  0.f,
									0.f, 0.f,  0.f,   1.f);
		Transform t = Transform.rotateX(angle);
		assertEquals(m, t.getGLTransposeMatrix());
		assertEquals(m.transpose(), t.inverseMatrix());

		m = new Matrix4x4(cost,  0.f,  sint, 0.f,
				0.f,   1.f,  0.f,  0.f,
				-sint, 0.f,  cost, 0.f,
				0.f,   0.f,  0.f,  1.f);
		t = Transform.rotateY(angle);
		assertEquals(m, t.getGLTransposeMatrix());
		assertEquals(m.transpose(), t.inverseMatrix());
		
		m = new Matrix4x4(cost, -sint, 0.f, 0.f,
				sint, cost,  0.f, 0.f,
				0.f,   0.f,  1.f, 0.f,
				0.f,   0.f,  0.f, 1.f);
		t = Transform.rotateZ(angle);
		assertEquals(m, t.getGLTransposeMatrix());
		assertEquals(m.transpose(), t.inverseMatrix());
	}

	@Test
	public void testRotate(){
		try {
			float angle = 45;
			Vector v = new Vector(1.f,1.f,1.f);
			Vector a = Geometry.normalize(v);
			float s = (float) Math.sin(Geometry.radians(angle));
			float c = (float) Math.cos(Geometry.radians(angle));
			float[][] m = new float[4][4];
			
			m[0][0] = a.getX() * a.getX() + (1.f - a.getX() * a.getX()) * c;
			m[0][1] = a.getX() * a.getY() * (1.f - c) - a.getZ() * s;
			m[0][2] = a.getX() * a.getZ() * (1.f - c) - a.getY() * s;
			m[0][3] = 0;
			
			m[1][0] = a.getX() * a.getY() * ( 1.f - c ) + a.getZ() * s;
		    m[1][1] = a.getY() * a.getY() + ( 1.f - a.getY() * a.getY() ) * c;
		    m[1][2] = a.getY() * a.getZ() * ( 1.f - c ) - a.getX() * s;
		    m[1][3] = 0;
		    
		    m[2][0] = a.getX() * a.getZ() * ( 1.f - c ) - a.getY() * s;
		    m[2][1] = a.getY() * a.getZ() * ( 1.f - c ) + a.getX() * s;
		    m[2][2] = a.getZ() * a.getZ() + ( 1.f - a.getZ() * a.getZ() ) * c;
		    m[2][3] = 0;
		    
		    m[3][0] = 0;
		    m[3][1] = 0;
		    m[3][2] = 0;
		    m[3][3] = 1;
		    
		    Matrix4x4 mat = new Matrix4x4(m);
		    
		    Transform t = Transform.rotate(angle, v);
		    
		    assertEquals(mat, t.getGLTransposeMatrix());
		    assertEquals(mat.transpose(), t.inverseMatrix());
		} catch (Exception e) {
			fail("Exception throw");
		}
	}

	@Test
	public void testLookAt(){
		try{
			Point pos = new Point(5.f, 4.f, 6.f);
			Point look = new Point(15.f, 12.f, 9.f);
			Vector up = new Vector(25.f, 20.f, 15.f);
			float[][] m = new float[4][4];
			Vector dir = Geometry.normalize(new Vector(pos, look));
			Vector right = Geometry.normalize(Geometry.cross(dir, Geometry.normalize(up)));
			Vector newUp = Geometry.normalize(Geometry.cross(right, dir));
			
		    m[0][0] = right.getX();	m[1][0] = right.getY(); m[2][0] = right.getZ();	m[3][0] = 0.f;
		    m[0][1] = newUp.getX();	m[1][1] = newUp.getY();	m[2][1] = newUp.getZ();	m[3][1] = 0.f;
		    m[0][2] = -dir.getX();   // opengl convention, look down the negative z axis
		    m[1][2] = -dir.getY();	m[2][2] = -dir.getZ();	m[3][2] = 0.f;
		    
		    // Initialize fourth column of viewing matrix
		    m[0][3] = pos.getX();	m[1][3] = pos.getY();	m[2][3] = pos.getZ();	m[3][3] = 1.f;
	
		    Matrix4x4 ma = new Matrix4x4(m);
		    //return new Transform(camToWorld.getInverse(), camToWorld);
		    Transform t = Transform.lookAt(pos, look, up);
		    assertEquals(ma.getInverse(), t.getGLTransposeMatrix());
		    assertEquals(ma, t.inverseMatrix());
		}catch(Exception e){ fail("Exception throw : "+e.getMessage()); }
	}

	@Test
	public void testProduct(){
		try {
			Matrix4x4 m = new Matrix4x4(1.f, 2.f, 0.f, 5.f,   5.f, 6.f, 1.f, 3.f,   8.f, 9.f, 1.f, 1.f,  6.f, 4.f, 7.f, 9.f);
			Transform t = new Transform(m);
			Transform ta = trans.product(t);
			assertEquals(Matrix4x4.product(trans.getGLTransposeMatrix(), t.getGLTransposeMatrix()), ta.getGLTransposeMatrix());
			assertEquals(Matrix4x4.product(t.inverseMatrix(), trans.inverseMatrix()), ta.inverseMatrix());
		} catch (Exception e) { fail("Exception Throw : " + e.getMessage()); }
	}

	@Test
	public void testSwapsHandedness(){
		assertTrue(trans.swapsHandedness());
		try {
			Matrix4x4 m = new Matrix4x4(1.f, 2.f, 0.f, 5.f,   5.f, 6.f, 1.f, 3.f,   8.f, 9.f, 1.f, 1.f,  6.f, 4.f, 7.f, 9.f);
			Transform t = new Transform(m);
			assertFalse(t.swapsHandedness());
		} catch (Exception e) { fail("Exception Throw : " + e.getMessage()); }
	}

	@Test
	public void testOrtographic(){
		float zfar = 99.f;
		float znear = 1.f;
		float zscale = 1.f / (zfar - znear);
		Transform scale = Transform.scale(1.f, 1.f, zscale);
		Transform transform = Transform.translate(new Vector(0.f, 0.f, -znear));
		Transform expected = scale.product(transform);
		Transform actual = Transform.orthographic(znear, zfar);
		assertEquals(expected, actual);
		
		try {
			float right = -10.f, left = 10.f, top = 10.f, bottom = -10.f;
			Matrix4x4 ortho = new Matrix4x4(2.f / (right - left), 	0.f                 , 	0.f                  , 	-(right + left) / (right - left),
					0.f,					2.f / (top - bottom), 	0.f                  , 	-(top + bottom) / (top - bottom),
					0.f, 					0.f                 , 	-2.f / (zfar - znear), 	-(zfar + znear) / (zfar - znear),
					0.f,					0.f, 					0.f, 					1.f);
			expected = new Transform(ortho);
			actual = Transform.orthographic(left, right, bottom, top, znear, zfar);
			assertEquals(expected, actual);
		} catch (Exception e) { fail("Exception Throw : " + e.getMessage()); }
	}

	@Test
	public void testPerspective(){
		try {
			float znear = 1.f, zfar = 99.f, fov = 12.f, aspect = 5.f;
			float invTan = (float) (1.f / Math.tan(Geometry.radians(fov) / 2));
			float invDenom = 1.f / (znear - zfar);
			Matrix4x4 persp = new Matrix4x4(invTan / aspect,	0,      0,                      	0,
			                     			0, 					invTan,	0,                      	0,
			                     			0,       			0, 		(zfar + znear) * invDenom,	2.f*zfar*znear*invDenom,
			                     			0,       			0,      -1,                      	0);
			Transform expected = new Transform(persp);
			Transform actual = Transform.perspective(fov, aspect, znear, zfar);
			assertEquals(expected, actual);
		} catch (Exception e) { fail("Exception Throw : " + e.getMessage()); }
	}

	@Test
	public void testViewport(){
		try{
			Matrix4x4 viewport = new Matrix4x4(2.f, 0.f, 0.f, 2.f,    0.f, .5f, 0.f, .5f,    0.f, 0.f, .5f, .5f,     0.f, 0.f, 0.f, 1.f);
			Transform expected = new Transform(viewport);
			Transform actual = Transform.viewport(4.f, 1.f);
			assertEquals(expected, actual);
		} catch (Exception e) { fail("Exception Throw : " + e.getMessage()); }
	}
}
