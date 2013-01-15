package lp.iem.gk.test.mesh;

import static org.junit.Assert.*;
import lp.iem.mesh.Vertex;

import org.junit.Test;

public class testVertex
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
