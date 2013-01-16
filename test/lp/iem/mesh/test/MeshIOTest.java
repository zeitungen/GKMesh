package lp.iem.mesh.test;

import static org.junit.Assert.*;
import lp.iem.mesh.Mesh;
import lp.iem.mesh.MeshIO;

import org.junit.Test;

public class MeshIOTest {

	@Test
	public void testRead(){
		String path = "src/lp/iem/mesh/test/bigguy.obj";
		Mesh m = MeshIO.read(path, "bigguy");
	}

}
