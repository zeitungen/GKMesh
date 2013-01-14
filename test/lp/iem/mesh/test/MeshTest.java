package lp.iem.mesh.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import lp.iem.gk.*;
import lp.iem.mesh.Mesh;
import lp.iem.mesh.MeshBuffer;
import lp.iem.mesh.Name;

import org.junit.Test;

public class MeshTest
{

	@Test
	public void testAttachAttributeBuffer()
	{
		Mesh mesh = new Mesh();
		
		MeshBuffer buff = mesh.attachAttributeBuffer(new Name("test"), new Point(1,1,1));
		assertNotNull(buff);
		
		MeshBuffer buff2 = mesh.attachAttributeBuffer(new Name("test"), new Point2(1,1));
		assertNotNull(buff2);
		
		MeshBuffer buff3 = mesh.attachAttributeBuffer(new Name("test"), new Color(1,1,0,14));
		assertNotNull(buff3);
		
		MeshBuffer buff4 = mesh.attachAttributeBuffer(new Name("test"), new Vector(1,1,0));
		assertNotNull(buff4);
		
		MeshBuffer buff5 = mesh.attachAttributeBuffer(new Name("test"), new Normal(1,1,0));
		assertNotNull(buff5);
		
		MeshBuffer buff6 = mesh.attachAttributeBuffer(new Name("test"), 10.52f);
		assertNotNull(buff6);
	}

	@Test
	public void testPosition()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point> pts = new ArrayList<Point>();
		pts.add(new Point(1,2,3));
		mesh.attachPositionBuffer(pts);

		Point p = mesh.position(0);
		assertEquals(new Point(1,2,3), p);
	}

	@Test
	public void testNormal()
	{
		Mesh mesh = new Mesh();
		ArrayList<Normal> pts = new ArrayList<Normal>();
		pts.add(new Normal(3,2,1));
		mesh.setM_normals(pts);
		Normal p = mesh.normal(0);
		assertEquals(new Normal(3,2,1), p);
	}

	@Test
	public void testTextCoord()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point2> pts = new ArrayList<Point2>();
		pts.add(new Point2(3,2));
		mesh.setM_texcoords(pts);
		Point2 p = mesh.texcoords(0);
		assertEquals(new Point2(3,2), p);
	}

	@Test
	public void testPushPosition()
	{
		Mesh mesh = new Mesh();
		int res = mesh.pushPosition(new Point(1,0,1));
		//System.out.println(res);
	}
	
	@Test
	public void testAttachPosBuffer()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point> pts = new ArrayList<Point>();
		pts.add(new Point(1,0,2));
		pts.add(new Point(1,8,9));
		pts.add(new Point(1,2,5));
		
		mesh.attachPositionBuffer(pts);
		assertTrue(mesh.getM_positions().size() == 3);
		assertEquals(new Point(1,0,2), mesh.getM_positions().get(0));
		assertEquals(new Point(1,2,5), mesh.getM_positions().get(2));
	}

	@Test
	public void testPosCount()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point> pts = new ArrayList<Point>();
		pts.add(new Point(1,0,2));
		pts.add(new Point(1,8,9));
		pts.add(new Point(1,2,5));
		
		mesh.attachPositionBuffer(pts);
		
		assertEquals(3, mesh.positionCount());
	}

	@Test
	public void testPushNormal()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushPosition(new Point(1,0,1));
		int res = mesh.pushPosition(new Point(1,0,1));	
		assertTrue(res == 1);
	}

	@Test
	public void testAttachNormaBuffer()
	{
		Mesh mesh = new Mesh();
		ArrayList<Normal> pts = new ArrayList<Normal>();
		pts.add(new Normal(1,0,2));
		pts.add(new Normal(1,8,9));
		pts.add(new Normal(1,2,5));
		
		mesh.attachNormalBuffer(pts);
		assertTrue(mesh.getM_normals().size() == 3);
		assertEquals(new Normal(1,0,2), mesh.getM_normals().get(0));
		assertEquals(new Normal(1,2,5), mesh.getM_normals().get(2));
	}

	@Test
	public void testNormCount()
	{
		Mesh mesh = new Mesh();
		ArrayList<Normal> pts = new ArrayList<Normal>();
		pts.add(new Normal(1,0,2));
		pts.add(new Normal(1,8,9));
		pts.add(new Normal(1,2,5));
		
		mesh.attachNormalBuffer(pts);
		
		assertEquals(3, mesh.normalCount());
	}

	@Test
	public void testPushTexcoord()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTexcoord(new Point2(1,0));
		int res = mesh.pushTexcoord(new Point2(0,0));	
		assertTrue(res == 1);
	}

	@Test
	public void testAttachTexcoordBuffer()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point2> pts = new ArrayList<Point2>();
		pts.add(new Point2(1,0));
		pts.add(new Point2(8,9));
		pts.add(new Point2(2,5));
		
		mesh.attachTexcoordBuffer(pts);
		assertTrue(mesh.getM_texcoords().size() == 3);
		assertEquals(new Point2(1,0), mesh.getM_texcoords().get(0));
		assertEquals(new Point2(2,5), mesh.getM_texcoords().get(2));
	}

	@Test
	public void testTexcoordCount()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point2> pts = new ArrayList<Point2>();
		pts.add(new Point2(1,0));
		pts.add(new Point2(8,9));
		pts.add(new Point2(2,5));
		
		mesh.attachTexcoordBuffer(pts);
		
		assertEquals(3, mesh.texcoordCount());
	}

}
