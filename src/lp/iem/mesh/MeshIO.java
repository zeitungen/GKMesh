package lp.iem.mesh;

import java.util.List;

import lp.iem.io.IOFileSystem;
import lp.iem.io.IOManager;

import lp.iem.gk.Point;

public class MeshIO extends IOManager<Mesh> {
	
	private static MeshIO INSTANCE;
	
	public MeshIO(){
		super();
	}
	
	/**
	 * singleton
	 * @return
	 */
	public static MeshIO manager(){
		if(INSTANCE == null) INSTANCE = new MeshIO();
		return INSTANCE;
	}
	
	public static Mesh read(String filename, String name){
		//TODO
		return null;
	}

	public static Mesh read(String filename){
		return read(filename, "");
	}

	public static int write(Mesh mesh, String filename){
		//TODO
		return -1;
	}
	
	public static boolean isMeshOBJ(String filename){
		return IOFileSystem.isType(filename, ".obj");
	}
	
	/*
	public static Mesh meshLoadFromOBJ(String filename) throws Exception{
		Parser parser = new Parser(filename);
		if(!parser.isValid()) throw new Exception("file is not valid");
		
		List<Point> positions;
	}
	//*/
}
