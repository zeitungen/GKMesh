package lp.iem.mesh;

import lp.iem.gk.Color;
import lp.iem.gk.Geometry;
import lp.iem.gk.Vector;
import lp.iem.io.IOFileSystem;
import lp.iem.io.IOManager;

import static lp.iem.mesh.Parser.EOF;

public class MeshMaterialIO extends IOManager<MeshMaterial> {

	private static MeshMaterialIO INSTANCE = null;

	public MeshMaterialIO() {
		super();
	}

	/**
	 * singleton
	 * 
	 * @return
	 */
	public static MeshMaterialIO manager() {
		if (INSTANCE == null)
			INSTANCE = new MeshMaterialIO();
		return INSTANCE;
	}

	public static MeshMaterial read(String filename, String name)throws Exception {
		System.out.println(filename);
		MeshMaterial material = manager().find(filename, name);
		if (material != null)
			return material;
		// importer le fichier et referencer toutes les matieres dans le manager
		MaterialLoadFromMTL(filename);
		return manager().find(filename, name);
	}

	public static MeshMaterial read(String filename) throws Exception {
		return read(filename, "");
	}

	public static void MaterialLoadFromMTL(String filename) throws Exception {
		Parser parser = new Parser(filename);

		if (!parser.isValid())
			throw new Exception("file is not valid");

		MeshMaterial material = null;

		while (true) {
			int code = parser.readToken();
			if (code == EOF) break;
			if (code == '\n') continue;

			String token = parser.getToken();

			if (token == "newmtl") {
				if (parser.readString() < 0) throw new Exception("error readString");

				material = MeshMaterialIO.manager().find(filename, parser.getToken());
				if (material == null) {
					// la matiere n'existe pas, la creer
					material = new MeshMaterial(parser.getToken());
					MeshMaterialIO.manager().insert(material, filename, parser.getToken());
				}

				// passe a la ligne suivante
				continue;
			}

			if (material == null) continue;

			if (token == "Kd"){ // diffuse
				Vector v = parser.readVector3();

				float k = v.length();
				if (k > 0.f) v = Geometry.normalize(v);
				material.setDiffuse(new Color(v.getX(), v.getY(), v.getZ()));
				material.setKd(k);
			} else if (token == "kd"){ // diffuse
				if (parser.readToken() < 0) throw new Exception("error readToken");
				float v = parser.getFloat();

				material.setKd(v);
			}

			else if (token == "map_Kd"){ // diffuse color texture
				if (parser.readString() < 0) throw new Exception("error readString");

				// construire le chemin d'acces de la texture
				material.setDiffuseTexture(IOFileSystem.pathname(filename) + parser.getToken());
			} else if (token == "Ks"){ // specular color
				Vector v = parser.readVector3();
				float k = v.length();

				if (k > 0.f) v = Geometry.normalize(v);
				material.setSpecular(new Color(v.getX(), v.getY(), v.getZ()));
				material.setKd(k);
			} else if (token == "ks"){ // specular
				if (parser.readToken() < 0) throw new Exception("error readToken");
					
				float v = parser.getFloat();
				material.setKs(v);
			} else if (token == "map_Ks"){ // specular/glossy color texture
				if (parser.readString() < 0) throw new Exception("error readString");

				// construire le chemin d'acces de la texture
				material.setSpecularTexture(IOFileSystem.pathname(filename) + parser.getToken());
			} else if (token == "Ns"){ // phong exp
				if (parser.readToken() < 0) throw new Exception("error readString");
				material.setN(parser.getFloat());
				if (material.getN() < 0) throw new Exception("error getN");
			} else if (token == "Ni"){ // indice de refraction
				if (parser.readToken() < 0) throw new Exception("error readToken");
				material.setNi(parser.getFloat());
				if (material.getNi() < 0) throw new Exception("error getNi");
			} else if (token == "Le"){ // emission d'une source
				Vector v  = parser.readVector3();
				material.setEmission(new Color(v.getX(), v.getY(), v.getZ()));
			} else parser.skipLine(); // commande non reconnue
		}

	}

}
