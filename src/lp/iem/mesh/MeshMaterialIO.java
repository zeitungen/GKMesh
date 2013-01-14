package lp.iem.mesh;

import lp.iem.io.IOManager;

public class MeshMaterialIO extends IOManager{
	
	private static MeshMaterialIO INSTANCE = null;
	
	public MeshMaterialIO(){
		super();
	}
	
	public static MeshMaterialIO manager(){
		if(INSTANCE == null) INSTANCE = new MeshMaterialIO();
		return INSTANCE;
	}
	
	public static MeshMaterial read(String filename, String name){
		//TODO
		return null;
	}
	
	public static MeshMaterial read(String filename){
		return read(filename, "");
	}
	
}
