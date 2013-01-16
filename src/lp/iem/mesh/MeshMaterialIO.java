package lp.iem.mesh;

import lp.iem.io.IOManager;

public class MeshMaterialIO extends IOManager<MeshMaterial>{
	
	private static MeshMaterialIO INSTANCE = null;
	
	public MeshMaterialIO(){
		super();
	}
	
	/**
	 * singleton
	 * @return
	 */
	public static MeshMaterialIO manager(){
		if(INSTANCE == null) INSTANCE = new MeshMaterialIO();
		return INSTANCE;
	}
	
	public static MeshMaterial read(String filename, String name) throws Exception{
	
		MeshMaterial material = manager().find(filename, name);
		 if(material != null)
				return material;
		 // importer le fichier et referencer toutes les matieres dans le manager
		if(MaterialLoadFromMTL(filename) < 0)
		{
		   // printf("'%s' failed.\n", filename.c_str());
			System.out.println("failed : "+ filename);
			return null;
		} 
		
		return manager().find(filename, name);
	}
	
	public static MeshMaterial read(String filename) throws Exception{
		return read(filename, "");
	}
	
	public static  int MaterialLoadFromMTL( String filename ) throws Exception	
	{
		Parser parser = new Parser(filename);
		
		if(!parser.isValid())
			return -1;
		
		MeshMaterial material = null;
		
		for(;;)
		{
			int code = parser.readToken();
			if (code == EOF)
				break;
			if(code == '\n')
				continue;
			
			String token = parser.getToken();
			
			if (token == "newmtl")
			{
				if ( parser.readString() < 0 )
	                return -1;
	            
	            material = MeshMaterialIO.manager().find( filename, parser.getToken() );
	            if ( material == null )
	            {
	                // la matiere n'existe pas, la creer
	                material = new MeshMaterial( parser.getToken() );
	                MeshMaterialIO.manager().insert( material, filename, parser.getToken() );
	            }
	            
	            // passe a la ligne suivante
	            continue;				
			}
			
			 if ( material == null )
		            continue;
			 
			 if ( token == "Kd" ) // diffuse
		        {
		            Vector v;
		            try
					{
						v = parser.readVector3();
					} catch (Exception e)
					{
						return -1;
					} 	         
		            
		            float k= v.length();
		            if(k > 0.f)
		                v = Geometry.normalize(v);
		            material.setDiffuse( new Color( v.getX(), v.getY(), v.getZ() ));
		            material.setKd(k);
		        }
		        else if ( token == "kd" ) // diffuse
		        {
		            if ( parser.readToken() < 0 )
		                return -1;
		            float v;
		            v = parser.getFloat();
		            if ( v < 0 )
		                return -1;
		            
		            material.setKd(v);
		         }
			 
		        else if ( token == "map_Kd" ) // diffuse color texture
		        {
		            if ( parser.readString() < 0 )
		                return -1;
		                
		            // construire le chemin d'acces de la texture
		            material.setDiffuse_texture(IOFileSystem.pathname( filename ) + parser.getToken());
		        }			 
		        else if ( token == "Ks" ) // specular color
		        {
		            Vector v;
		            try
					{
						v = parser.readVector3();
					} catch (Exception e)
					{
						return -1;
					} 	         
		            float k = v.length();
		            
		            if(k > 0.f)
		                v= Geometry.normalize(v);
		            material.setSpecular(new Color( v.getX(), v.getY(), v.getZ() ));
		            material.setKd(k);
		        }			 
		        else if ( token == "ks" ) // specular
		        {
		            if ( parser.readToken() < 0 )
		                return -1;
		            float v = parser.getFloat( );
		            if (  v < 0 )
		                return -1;
		            material.setKs(v);
		        }
		        else if ( token == "map_Ks")    // specular/glossy color texture
		        {
		            if ( parser.readString() < 0 )
		                return -1;
		                
		            // construire le chemin d'acces de la texture
		            material.setSpecular_texture(IOFileSystem.pathname( filename ) + parser.getToken());
		        }
		        else if ( token == "Ns" ) // phong exp
		        {
		            if ( parser.readToken() < 0 )
		                return -1;
		            material.setN(parser.getFloat());
		            if (material.getN() < 0 )
		                return -1;
		        }
		        else if ( token == "Ni" ) // indice de refraction
		        {
		            if ( parser.readToken() < 0 )
		                return -1;
		            material.setNi(parser.getFloat());
		            if ( material.getNi() < 0 )
		                return -1;
		        }
		        else if ( token == "Le" ) // emission d'une source
		        {
		        	 Vector v;
		            try
					{
						v = parser.readVector3();
					} catch (Exception e)
					{
						return -1;
					} 	         
		            
		            material.setEmission(new Color( v.getX(), v.getY(), v.getZ()));
		        }
		        else
		            // commande non reconnue
		            parser.skipLine();		
		}	
		
		return 0;
		
	}
	
}
