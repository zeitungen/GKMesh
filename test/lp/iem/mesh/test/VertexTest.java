package lp.iem.mesh.test;

import static org.junit.Assert.*;
import lp.iem.mesh.Vertex;

import org.junit.Test;

public class VertexTest
{
	@Test
	public void testLess()
	{
		Vertex v1 = new Vertex(0,1,2,3);
		Vertex v2 = new Vertex(0,2,5,7);
		
		assertTrue(Vertex.superieur(v1, v2) == true);
		
		assertTrue(Vertex.less(v1, v2) == true);
	}

}
