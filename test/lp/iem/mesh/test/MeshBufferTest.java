package lp.iem.mesh.test;

import static org.junit.Assert.*;

import java.util.List;

import lp.iem.gk.*;
import lp.iem.mesh.MeshBuffer;
import lp.iem.mesh.Name;


import org.junit.BeforeClass;
import org.junit.Test;

public class MeshBufferTest
{
	private static MeshBuffer meshBuff;
	private static int size = 5;
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		Name test = new Name("test");
		meshBuff = new MeshBuffer(test, size);
	}
	
	@Test
	public void testConstructeur()
	{
		assertNotNull(meshBuff);
	}
	
	@Test
	public void testPush()
	{
		float data[] = new float [size];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = i;
		}
		meshBuff.push(data);
		
		List<Float> list = meshBuff.getData();
		for (int i = 0; i < data.length; i++)
		{
			assertTrue(data[i] == list.get(i));
		}
	}
	
	@Test
	public void testPush2()
	{
		MeshBuffer mesh2 = new MeshBuffer(new Name("mesh2"),1);
		
		int res = mesh2.push(2);
		
		assertTrue(res == 0);
		assertTrue(mesh2.getData().get(0) == 2);
	}
	
	@Test
	public void testPushPoint2() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),2) ;
		int res = mesh3.push(new Point2(14.2f,16.55f));
		
		assertTrue(res == 0);
		assertTrue(mesh3.getData().get(0) == 14.2f);
		assertTrue(mesh3.getData().get(1) == 16.55f);
	}
	
	@Test
	public void testPushPoint() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),3) ;
		int res = mesh3.push(new Point(14.2f,16.55f,19));
		
		assertTrue(res == 0);
		assertTrue(mesh3.getData().get(0) == 14.2f);
		assertTrue(mesh3.getData().get(1) == 16.55f);
		assertTrue(mesh3.getData().get(2) == 19);
	}

	@Test
	public void testPushVect() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),3) ;
		int res = mesh3.push(new Vector(14.2f,16.55f,19));
		
		assertTrue(res == 0);
		assertTrue(mesh3.getData().get(0) == 14.2f);
		assertTrue(mesh3.getData().get(1) == 16.55f);
		assertTrue(mesh3.getData().get(2) == 19);
	}

	@Test
	public void testPushNorm() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),3) ;
		int res = mesh3.push(new Normal(14.2f,16.55f,19));
		
		assertTrue(res == 0);
		assertTrue(mesh3.getData().get(0) == 14.2f);
		assertTrue(mesh3.getData().get(1) == 16.55f);
		assertTrue(mesh3.getData().get(2) == 19);
	}

	@Test
	public void testPushColor() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),4) ;
		int res = mesh3.push(new Color(14.2f,16.55f,19,12.258f));
		
		assertTrue(res == 0);
		assertTrue(mesh3.getData().get(0) == 14.2f);
		assertTrue(mesh3.getData().get(1) == 16.55f);
		assertTrue(mesh3.getData().get(2) == 19);
		assertTrue(mesh3.getData().get(3) == 12.258f);
	}

	@Test
	public void testCount() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),3) ;
		mesh3.push(new Point(14.2f,16.55f,19));
		mesh3.push(new Point(17.2f,14.55f,13));
		mesh3.push(new Normal(4.2f,1.55f,9));
		mesh3.push(new Vector(.2f,55,1));
		
		assertTrue(mesh3.attributeCount() == 4);
	}
	
	@Test
	public void testAsColor() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),4) ;
		int res = mesh3.push(new Color(14.2f,16.55f,19,12.258f));
		int res2 = mesh3.push(new Color(14.2f,16.55f,19,12.258f));
		
		Color col = mesh3.asColor(1);
	}
	
	@Test
	public void testAs() throws Exception
	{
		MeshBuffer mesh3 = new MeshBuffer(new Name("mesh 3"),3) ;
		MeshBuffer mesh2 = new MeshBuffer(new Name("mesh 2"),2) ;
		
		mesh3.push(new Point(14.2f,16.55f,19));
		mesh3.push(new Normal(0,0.55f,1));
		
		Point col = mesh3.asPoint(1);
		Vector col1 = mesh3.asVector(1);
		Normal n = mesh3.asNormal(1);
		
		mesh2.push(new Point2(10,25));
		Point2 test = mesh2.asPoint2(0);
	}
}
