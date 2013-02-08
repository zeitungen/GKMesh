package lp.iem.mesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lp.iem.io.IOFileSystem;
import lp.iem.io.IOManager;

import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.gk.Point2;
import lp.iem.gk.Vector;

import static lp.iem.mesh.Parser.EOF;

public class MeshIO extends IOManager<Mesh> {
	
	private static final String GK_DEFAULT_MATERIAL = "gk_default_material";
	private static final String mtllib = "mtllib";
	private static final String usemtl = "usemtl";
	
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
		Mesh mesh = manager().find(filename, name);
		if(mesh != null) return mesh;
		
		mesh = new Mesh();
		if(!isMeshOBJ(filename)) return null;
		try {
			mesh = meshLoadFromOBJ(filename, mesh);
			return manager().insert(mesh, filename, name);
		} catch (Exception e) { return null; }
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
	
	public static Mesh meshLoadFromOBJ(String filename, Mesh mesh) throws Exception{
		Parser parser = new Parser(filename);
		if(!parser.isValid()) throw new Exception("file is not valid");
		
		List<Point> positions = new ArrayList<Point>();
		List<Normal> normals = new ArrayList<Normal>();
		List<Point2> texcoords = new ArrayList<Point2>();
		Map<Vertex, Integer> mvertices = new HashMap<Vertex, Integer>();
		List<Vertex> vertices = new ArrayList<Vertex>();
		List<TriangleOBJ> triangles = new ArrayList<TriangleOBJ>();
		List<Integer> indices = new ArrayList<Integer>();
		
		int nvertices = 0;
		boolean hasPositions = false;
		boolean hasNormals = false;
		boolean hasTexcoords = false;
		
		Map<String, Integer> mmaterials = new HashMap<String, Integer>();
		List<MeshMaterial> materials = new ArrayList<MeshMaterial>();
		int materialid = -1;
		String materiallib = "";
		
		// save the default material by default, if necessary
		MeshMaterial defaultMaterial = MeshMaterialIO.manager().find("", GK_DEFAULT_MATERIAL);
		if(defaultMaterial == null){
			defaultMaterial = new MeshMaterial(GK_DEFAULT_MATERIAL);
			MeshMaterialIO.manager().insert(defaultMaterial, "", defaultMaterial.getName());
		}
		
		Map<String, Integer> msmooth = new HashMap<String, Integer>();
		int smoothgrp = -1;
		
		while(true){
			int code = parser.readToken();
			if(code == EOF) break;
			else if(code == (int)'\n') continue;

			String token = parser.getToken();
			
			if(token.equals(mtllib)){
				if(parser.readString() < 0) throw new Exception("Error parser.readString()");
				materiallib = IOFileSystem.pathname(filename) + parser.getToken();
				MeshMaterialIO.read(materiallib);
			}else if(token.equals(usemtl)){
				if(parser.readString() < 0) throw new Exception("Error parser.readString()");
				materialid = -1;
				// material search in the list of materials already referenced by the mesh
				int found = mmaterials.get(parser.getToken());
				if(found == mmaterials.size()-1){
					// Search the material in the manager, if it does not use a default material
					MeshMaterial material = MeshMaterialIO.manager().find(materiallib, parser.getToken());
					if(material == null){
						// default material
						found = mmaterials.get(GK_DEFAULT_MATERIAL);
						if(found == mmaterials.size()-1){
							material = MeshMaterialIO.manager().find("", GK_DEFAULT_MATERIAL);
							if(material == null) throw new Exception("material " + GK_DEFAULT_MATERIAL + " not found");
							materialid = materials.size();
							mmaterials.put(material.getName(), materialid);
							materials.add(material);
						}else materialid = found;
					}else{ // reference the material
						materialid = materials.size();
						mmaterials.put(material.getName(), materialid);
						materials.add(material);
					}
				}else materialid = found;
				if(materialid == -1) throw new Exception("Material don't add");
			}else if(token.equals("v")){
				Vector v = parser.readVector3();
				positions.add(new Point(v));
			}else if(token.equals("vn")){
				Vector n;
				try{ 
					n = parser.readVector3();
				}catch(Exception e){
					n = new Vector();
				}
				normals.add(new Normal(n));
			}else if(token.equals("vt")){
				Point2 t;
				try{ 
					t = parser.readVector2();
				}catch(Exception e){
					t = new Point2();
				}
				texcoords.add(t);
			}else if(token.equals("f")){
				int i = 0;
				indices = new ArrayList<Integer>();
				boolean again = true;
				while(again){
					if(parser.readToken() == '\n') again = false;
					if(parser.getToken().length() == 0) continue;
					
					// read position/texcoord/normal attributs
					int positionid = -1, normalid = -1, texcoordid = -1;
					if(Parser.getAttributeIndex(parser, positionid, positions.size()) != 1)
						throw new Exception("error Parser.getAttribute position");
					positionid = Parser.getAttribute(parser, positionid, positions.size());
					hasPositions = true;
					if(parser.getLastChar() == '/'){
						//if(parser.readToken() == '\n') throw new Exception("error read part 'f'");
						if(!again) throw new Exception("error read part 'f'");
						if(parser.readToken() == '\n') again = false;
						int texcoordcode = Parser.getAttributeIndex(parser, texcoordid, texcoords.size());
						texcoordid = Parser.getAttribute(parser, texcoordid, texcoords.size());
						if(texcoordcode < 0) throw new Exception("error Parser.getAttribute texcoord");
						else if(texcoordcode > 0) hasTexcoords = true;
						if(parser.getLastChar() == '/'){
							//if(parser.readToken() == '\n') throw new Exception("error read part 'f'");
							if(!again) throw new Exception("error read part 'f'");
							if(parser.readToken() == '\n') again = false;
							int normalcode = Parser.getAttributeIndex(parser, normalid, normals.size());
							normalid = Parser.getAttribute(parser, normalid, normals.size());
							if(normalcode < 0) throw new Exception("error Parser.getAttribute normal");
							else if(normalcode > 0) hasNormals = true;
						}
					}
					Vertex vertex = new Vertex(materialid, positionid, normalid, texcoordid);
					// insert into the vertex map
					if(!mvertices.containsKey(vertex)){
						mvertices.put(vertex, nvertices);
						vertices.add(vertex);
						indices.add(nvertices);
						nvertices++;
					}else indices.add(mvertices.get(vertex));
					
					i++;
					if(i > 2){
						TriangleOBJ triangle = new TriangleOBJ(indices.get(0), indices.get(i-2), indices.get(i-1));
						if(materialid < 0){ // use default material
							Integer foundI = mmaterials.get(GK_DEFAULT_MATERIAL);
							int found = mmaterials.size()-1;
							if(foundI != null) found = foundI.intValue();
							if(found == mmaterials.size()-1){
								MeshMaterial material = MeshMaterialIO.manager().find("", GK_DEFAULT_MATERIAL);
								if(material == null) throw new Exception("material " + GK_DEFAULT_MATERIAL + " not found");
								materialid = materials.size();
								mmaterials.put(material.getName(), materialid);
								materials.add(material);
							}else materialid = found;
						}
						if(materialid == -1) throw new Exception("Material don't add");
						triangle.setMaterialId(materialid);
						triangle.setSmoothId(smoothgrp);
						triangles.add(triangle);
					}
				}
			}else if(token.equals("s")){
				if(parser.readToken() != '\n'){
					if(parser.getToken().equals("off") || parser.getToken() == "0"){
						smoothgrp = -1;
					}else{
						msmooth.put(parser.getToken(), msmooth.size());
						smoothgrp = msmooth.size()-1;
					}
					parser.skipLine();
				}
			}else parser.skipLine();
		}
		// step 2 : build the mesh + resort positions / normals / texcoords
		for(int i=0; i < vertices.size(); i++){
			if(vertices.get(i).position() < 0) throw new Exception("vertex position < 0");
			mesh.pushPosition(positions.get(vertices.get(i).position()));
			if(hasNormals){
				if(vertices.get(i).normal() < 0) mesh.pushNormal(new Normal());
				else mesh.pushNormal(normals.get(vertices.get(i).normal()));
			}
			if(hasTexcoords){
				if(vertices.get(i).texcoord() < 0) mesh.pushTexcoord(new Point2());
				else mesh.pushTexcoord(texcoords.get(vertices.get(i).texcoord()));
			}
		}
		// step 3 : sort triangle by material, build submeshes
		Collections.sort(triangles);
		
		// insert triangles
		int n = triangles.size();
		for(int i = 0; i < n; i++){
			TriangleOBJ t = triangles.get(i);
			mesh.pushTriangle(t.a(), t.b(), t.c(), t.material(), t.smoothGroup());
		}
		
		// identify sequences of identical materials
		int submesh = 0;
		materialid = triangles.get(0).material();
		for(int i=1; i < n; i++){
			if(triangles.get(i).material() != materialid){
				mesh.pushSubMesh(submesh, 3*i, materialid);
				materialid = triangles.get(i).material();
				submesh = 3*i;
			}
			mesh.pushSubMesh(submesh, 3*n, materialid);
		}
		mesh.attachMaterials(materials);
		return mesh;
	}

}
