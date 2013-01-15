package lp.iem.mesh.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import lp.iem.gk.*;
import lp.iem.mesh.Mesh;
import lp.iem.mesh.MeshBuffer;
import lp.iem.mesh.Name;
import lp.iem.mesh.MeshMaterial;
import lp.iem.mesh.MeshTriangle;
import lp.iem.mesh.SubMesh;
import lp.iem.mesh.material_less;

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
		mesh.M_normals(pts);
		Normal p = mesh.normal(0);
		assertEquals(new Normal(3,2,1), p);
	}

	@Test
	public void testTextCoord()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point2> pts = new ArrayList<Point2>();
		pts.add(new Point2(3,2));
		mesh.M_texcoords(pts);
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
		assertTrue(mesh.M_positions().size() == 3);
		assertEquals(new Point(1,0,2), mesh.M_positions().get(0));
		assertEquals(new Point(1,2,5), mesh.M_positions().get(2));
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
		assertTrue(mesh.M_normals().size() == 3);
		assertEquals(new Normal(1,0,2), mesh.M_normals().get(0));
		assertEquals(new Normal(1,2,5), mesh.M_normals().get(2));
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
		assertTrue(mesh.M_texcoords().size() == 3);
		assertEquals(new Point2(1,0), mesh.M_texcoords().get(0));
		assertEquals(new Point2(2,5), mesh.M_texcoords().get(2));
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
	
	@Test
	public void attachTexcoordBuffer()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point2> pts = new ArrayList<Point2>();
		pts.add(new Point2(1,0));
		pts.add(new Point2(8,9));
		pts.add(new Point2(2,5));
		
		assertTrue(mesh.M_texcoords().size() == 0);
		mesh.attachTexcoordBuffer(2,pts);
		assertTrue(mesh.M_texcoords().size() == 2);
	}

	@Test
	public void attachNormalBuffer()
	{
		Mesh mesh = new Mesh();
		ArrayList<Normal> pts = new ArrayList<Normal>();
		pts.add(new Normal(1,0,4));
		pts.add(new Normal(8,9,9));
		pts.add(new Normal(2,5,7));
		
		assertTrue(mesh.M_normals().size() == 0);
		mesh.attachNormalBuffer(1,pts);
		assertTrue(mesh.M_normals().size() == 1);
	}

	@Test
	public void attachNormalPoint()
	{
		Mesh mesh = new Mesh();
		ArrayList<Point> pts = new ArrayList<Point>();
		pts.add(new Point(1,0,4));
		pts.add(new Point(8,9,9));
		pts.add(new Point(2,5,7));
		
		assertTrue(mesh.M_positions().size() == 0);
		mesh.attachPositionBuffer(3,pts);
		
		assertTrue(mesh.M_positions().size() == 3);
		
	}

	@Test
	public void testbufferCount()
	{
		Mesh mesh = new Mesh();
		
		mesh.attachAttributeBuffer(new Name("test"), new Point(1,1,1));
		mesh.attachAttributeBuffer(new Name("test"), new Point2(1,1));
		mesh.attachAttributeBuffer(new Name("test"), new Color(1,1,0,14));
		mesh.attachAttributeBuffer(new Name("test"), new Vector(1,1,0));
		mesh.attachAttributeBuffer(new Name("test"), new Normal(1,1,0));
		
		assertTrue(mesh.bufferCount() == 5);
	}

	@Test
	public void testGetbuffer()
	{
		Mesh mesh = new Mesh();
		
		mesh.attachAttributeBuffer(new Name("test"), new Point(1,1,1));
		
		MeshBuffer Buff = mesh.attachAttributeBuffer(new Name("test"), new Point2(1,1));
		
		mesh.attachAttributeBuffer(new Name("test"), new Color(1,1,0,14));
		mesh.attachAttributeBuffer(new Name("test"), new Vector(1,1,0));
		mesh.attachAttributeBuffer(new Name("test"), new Normal(1,1,0));
		
		
		assertEquals(Buff, mesh.buffer(1));
	}

	@Test
	public void PushTriangle()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 5);
		assertTrue(mesh.M_indices().get(0) == 1);
		assertTrue(mesh.M_indices().get(1) == 2);
		assertTrue(mesh.M_indices().get(2) == 3);
		
		assertTrue(mesh.M_materials_id().get(0) == 5);
		assertTrue(mesh.M_smooth_groups().get(0) == -1);
	}
	
	@Test
	public void testTriangleCount()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 5);
		mesh.pushTriangle(7,8,9,4,2);
		mesh.pushTriangle(4,2,6,3,1);
		
		assertTrue(mesh.triangleCount() == 3);
	}
	
	@Test
	public void testIndiceCount()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 5);
		mesh.pushTriangle(7,8,9,4,2);
		mesh.pushTriangle(4,2,6,3,1);
		
		assertTrue(mesh.indiceCount() == 9);
	}

	@Test
	public void testPushSubMesh()
	{
		Mesh mesh = new Mesh();
		assertTrue(mesh.M_submeshes().size() == 0);
		
		mesh.pushSubMesh(4, 3, 12);
		assertTrue(mesh.M_submeshes().size() == 1);
		
		mesh.pushSubMesh(54, 5, 72);
		assertTrue(mesh.M_submeshes().size() == 2);
	
		
	}

	@Test
	public void testSubmeshCount()
	{
		Mesh mesh = new Mesh();
		mesh.pushSubMesh(4, 3, 12);
		mesh.pushSubMesh(54, 5, 72);
		
		assertTrue(mesh.subMeshCount() == 2);
		
	}
	
	@Test
	public void testGetSubmesh()
	{		Mesh mesh = new Mesh();
		mesh.pushSubMesh(4, 3, 12);
		mesh.pushSubMesh(54, 5, 72);
	
		assertEquals(new SubMesh(4, 3, 12),mesh.subMesh(0));		
	}
	
	@Test
	public void testSubmeshMaterial()
	{		
		Mesh mesh = new Mesh();
		mesh.pushSubMesh(4, 3, 0);
		mesh.pushSubMesh(54, 5, 0);
		
		ArrayList<MeshMaterial> mat = new ArrayList<MeshMaterial>();
		mat.add(new MeshMaterial("material"));
		mesh.M_materials(mat);
		
		MeshMaterial Meshmat = mesh.subMeshMaterial(1);
		
		assertNotNull(Meshmat);	
		
		assertTrue(Meshmat.getName() == "material");
	}

	@Test
	public void testPushDefMaterial()
	{		
		Mesh mesh = new Mesh();
		int res = mesh.pushDefaultMaterial();
		int res2 = mesh.pushDefaultMaterial();
		
		assertTrue(res2 == 1);
	}

	@Test
	public void testPushMaterial()
	{		
		Mesh mesh = new Mesh();
		int res = mesh.pushMaterial(new MeshMaterial("mesh"));
		int res2 = mesh.pushMaterial(new MeshMaterial("mesh"));
		
		assertTrue(res2 == 1);
	}

	@Test
	public void testAttachMat()
	{
		Mesh mesh = new Mesh();
		assertTrue(mesh.M_materials().size() == 0);
		
		ArrayList<MeshMaterial> mat = new ArrayList<MeshMaterial>();
		mat.add(new MeshMaterial("test"));
		mat.add(new MeshMaterial("test2"));
		mat.add(new MeshMaterial("test3"));
		
		mesh.attachMaterials(mat);
		assertTrue(mesh.M_materials().size() == 3);
		
	}
	
	@Test
	public void testMatCount()
	{
		Mesh mesh = new Mesh();
		assertTrue(mesh.materialCount() == 0);
		
		ArrayList<MeshMaterial> mat = new ArrayList<MeshMaterial>();
		mat.add(new MeshMaterial("test"));
		mat.add(new MeshMaterial("test2"));
		mat.add(new MeshMaterial("test3"));
		
		mesh.attachMaterials(mat);
		assertTrue(mesh.materialCount() == 3);
		
	}

	@Test
	public void material()
	{
		Mesh mesh = new Mesh();
		MeshMaterial mat = mesh.material(0);
		
		assertTrue(mat.getKd() == mesh.M_default_material().getKd());
		assertTrue(mat.getName() == mesh.M_default_material().getName());
		
		mesh.pushMaterial(new MeshMaterial("mesh"));
		MeshMaterial mat2 = mesh.material(0);
		
		assertTrue(mat2.getName() == "mesh");
	}

	@Test
	public void materialTriangle()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(1, 2, 3, 2);
		
		mesh.pushMaterial(new MeshMaterial("mesh0"));
		mesh.pushMaterial(new MeshMaterial("mesh1"));
		mesh.pushMaterial(new MeshMaterial("mesh2"));
		
		MeshMaterial mat0 = mesh.triangleMaterial(0);
		MeshMaterial mat1 = mesh.triangleMaterial(1);
		MeshMaterial mat2 = mesh.triangleMaterial(2);
		
		assertTrue(mat0.getName() == "mesh0");
		assertTrue(mat1.getName() == "mesh1");
		assertTrue(mat2.getName() == "mesh2");
	}

	@Test
	public void getTriMatID()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(1, 2, 3, 58);
		
		mesh.pushMaterial(new MeshMaterial("mesh0"));
		mesh.pushMaterial(new MeshMaterial("mesh1"));
		mesh.pushMaterial(new MeshMaterial("mesh2"));
				
		assertTrue(mesh.getTriangleMaterialId(0) == 0);
		assertTrue(mesh.getTriangleMaterialId(2) == 58);
	}
	
	@Test
	public void getMeshTri()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		MeshTriangle tri = mesh.getMeshTriangle(2);
		
		assertNotNull(tri);
		assertEquals(new MeshTriangle(mesh,7,2,8), tri);
	}
	
	@Test
	public void getTriangle()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		Triangle tri = mesh.getTriangle(1);
		assertEquals(tri.getA(),new Point(1,1,1));
		assertEquals(tri.getB(),new Point(0,0,1));
		assertEquals(tri.getC(),new Point(1,0,0));

	}

	@Test
	public void getPNTriangle() throws Exception
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		mesh.pushNormal(new Normal(10,5,6));
		mesh.pushNormal(new Normal(1,5,6));
		mesh.pushNormal(new Normal(10,51,68));

				
		PNTriangle tri = mesh.getPNTriangle(1);
		assertEquals(tri.getA(),new Point(1,1,1));
		assertEquals(tri.getB(),new Point(0,0,1));
		assertEquals(tri.getC(),new Point(1,0,0));
	}

	@Test
	public void getTriangleBBox()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		BBox box = mesh.getTriangleBBox(1);
		assertNotNull(box);
	}

	@Test
	public void getTriangleArea()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		float area = mesh.getTriangleArea(1);
		assertTrue(0.866f == area);
	}

	@Test
	public void getTriangleNorm() throws Exception
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		Normal nor = mesh.getTriangleNormal(1);

		assertEquals(nor.getX(), 0.577f, 0.001f);
		assertEquals(nor.getY(), -0.577f, 0.001f);
		assertEquals(nor.getZ(), 0.577f, 0.001f);
	}

	@Test
	public void getUVPoint()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		Point p1 = mesh.getUVPoint(1, 0, 0);
		assertEquals(p1, new Point(1,1,1));
	}

	@Test
	public void getUVNormal() throws Exception
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		Normal p1 = mesh.getUVNormal(1, 0, 0);
		assertEquals(p1.getX(), 0.577f, 0.001f);
		assertEquals(p1.getY(), -0.577f, 0.001f);
		assertEquals(p1.getZ(), 0.577f, 0.001f);
	}

	@Test
	public void getUVPoint2()
	{
		Mesh mesh = new Mesh();
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);		
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		mesh.pushTexcoord(new Point2(1,0));
		mesh.pushTexcoord(new Point2(9,0));
		mesh.pushTexcoord(new Point2(1,5));
		mesh.pushTexcoord(new Point2(1,5));
		
		Point2 p1 = mesh.getUVTexcoord(1, 1, 1);
		assertEquals(new Point2(-7,10), p1);
	}

	@Test
	public void getSmoothGroup()
	{
		Mesh mesh = new Mesh();
		assertTrue(mesh.getTriangleSmoothGroup(0) == -1);
		
		mesh.pushTriangle(1, 2, 3, 0,2);
		mesh.pushTriangle(1, 2, 3, 1,3);
		mesh.pushTriangle(7, 2, 8, 58,8);		
		mesh.pushTriangle(7, 2, 8, 58);
		
		assertTrue(mesh.getTriangleSmoothGroup(0) == 2);
		assertTrue(mesh.getTriangleSmoothGroup(1) == 3);
		assertTrue(mesh.getTriangleSmoothGroup(2) == 8);
		assertTrue(mesh.getTriangleSmoothGroup(3) == -1);
	}

	@Test
	public void testattachAttributeBuffer()
	{
		Mesh mesh = new Mesh();
		
		ArrayList <Point> pts = new ArrayList<Point>();
		pts.add(new Point(1,1,2));
		pts.add(new Point(1,1,2));
		pts.add(new Point(1,1,2));
		
		mesh.attachAttributeBuffer(new Name("test"), pts);
	}

	@Test
	public void testattachAttributeBuffer2()
	{
		Mesh mesh = new Mesh();
		
		ArrayList <Point> pts = new ArrayList<Point>();
		pts.add(new Point(1,1,2));
		pts.add(new Point(1,1,2));
		pts.add(new Point(1,1,2));
		
		mesh.attachAttributeBuffer(new Name("test"), pts);
		//mesh.attachAttributeBuffer(new Name("test"), pts, 1);
		
	}

	@Test
	public void testBuildAngency()
	{
		Mesh mesh = new Mesh();
		
		int build = mesh.buildAdjacency();
		assertTrue(build == 0);
	}
	
	@Test
	public void testBuildNormal() throws Exception
	{
		Mesh mesh = new Mesh();
		
		int build = mesh.buildNormals();
		assertTrue(build == 0);
	}
	
	@Test
	public void testMaterial_lessSup()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		material_less mat = new material_less(list);
		assertTrue(mat.superieur(0, 1) == true);
	}
	
	@Test
	public void buildSubMeshes()
	{
		Mesh mesh = new Mesh();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		mesh.pushTriangle(1, 2, 3, 0);
		mesh.pushTriangle(1, 2, 3, 1);
		mesh.pushTriangle(7, 2, 8, 58);		
		
		mesh.pushPosition(new Point(1,0,1));
		mesh.pushPosition(new Point(1,1,1));
		mesh.pushPosition(new Point(0,0,1));
		mesh.pushPosition(new Point(1,0,0));
		
		mesh.pushTexcoord(new Point2(1,0));
		mesh.pushTexcoord(new Point2(9,0));
		mesh.pushTexcoord(new Point2(1,5));
		mesh.pushTexcoord(new Point2(1,5));
		
		assertTrue(mesh.buildSubMeshes(list) == 1);
	}

}
