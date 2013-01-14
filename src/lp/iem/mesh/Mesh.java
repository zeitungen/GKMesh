package lp.iem.mesh;

import java.util.ArrayList;

import lp.iem.gk.*;
import lp.iem.io.IOResource;

//! representation d'un maillage triangule.

//! un mesh est un ensemble de triangles.
//! un triangle est un triplet <abc> d'indices permettant de retrouver la position, la normale et les coordonnees de texture de chaque sommet a, b, c.
//! un triangle (cf. gk::MeshMaterial) a egalement une matiere associee.
//! les triangles partageants la meme matiere peuvent etre groupes dans un gk::SubMesh pour un affichage efficace.

public class Mesh extends IOResource
{
	private ArrayList<Point> m_positions = new ArrayList <Point>(); 
	private ArrayList<Normal> m_normals = new ArrayList<Normal>();
	private ArrayList<Point2> m_texcoords = new ArrayList<Point2>();	    
	private ArrayList<Integer> m_indices = new ArrayList<Integer>();  //!< 3*triangles.size()
	private ArrayList<Integer> m_materials_id = new ArrayList<Integer>();     //!< triangles.size()
	private ArrayList<Integer> m_smooth_groups = new ArrayList<Integer>();   //!< triangles.size()
	private ArrayList<Integer> m_position_adjacency = new ArrayList<Integer>();        //!< positions.size(), premier element de la liste d'adjacence du sommet.
	private ArrayList<Integer> m_adjacency = new ArrayList<Integer>();       //!< 2* positions.size() ? liste globale m_adjacency[m_position_adjacency[id]] .. -1
	private ArrayList<SubMesh> m_submeshes = new ArrayList<SubMesh>();  
	private ArrayList<MeshMaterial> m_materials = new ArrayList<MeshMaterial>();
	private ArrayList<MeshBuffer> m_attributes_buffer = new ArrayList<MeshBuffer>();
	
	private MeshMaterial m_default_material;
	private BBox m_bbox = new BBox();;
    
    // Constructeur
    public Mesh()
    {
    	super();
    	m_default_material = new MeshMaterial("default");
    }
    
    //! declare un ensemble d'attributs point 2d.
    public MeshBuffer attachAttributeBuffer( Name semantic,  Object attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 2);
        m_attributes_buffer.add(buffer);
        return buffer;
    }
        
    //! declare un ensemble d'attributs point 2d.
/*    public MeshBuffer attachAttributeBuffer( Name semantic,  Point2 attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 2);
        m_attributes_buffer.add(buffer);
        return buffer;
    }*/
    /*
    //! declare un ensemble d'attributs point 3d.
    public MeshBuffer attachAttributeBuffer( Name semantic, Point attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 3);
        m_attributes_buffer.add(buffer);
        return buffer;
    }
   
    //! declare un ensemble d'attributs vecteur 3d.
    public MeshBuffer attachAttributeBuffer( Name semantic, Vector attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 3);
        m_attributes_buffer.add(buffer);
        return buffer;
    }

    //! declare un ensemble d'attributs normale 3d.
    public MeshBuffer attachAttributeBuffer( Name semantic, Normal attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 3);
        m_attributes_buffer.add(buffer);
        return buffer;
    }
    
    //! declare un ensemble d'attributs couleur rgba.
    public MeshBuffer attachAttributeBuffer( Name semantic, Color attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 4);
        m_attributes_buffer.add(buffer);
        return buffer;
    }

    //! declare un ensemble d'attributs float.
    public MeshBuffer attachAttributeBuffer( Name semantic, float attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 1);
        m_attributes_buffer.add(buffer);
        return buffer;
    }
    */
    //! renvoie la position d'un sommet.
    
    public Point position(int id )
    {
        if(id >=0 && id < m_positions.size())
        	return m_positions.get(id);
        else
        	return null;
    }
   
    //! renvoie la normale d'un sommet.
    public Normal normal(int id )
    {
    	if(id >=0 && id < m_normals.size())
    		return m_normals.get(id);
    	else
    		return null;
    }
    
    //! renvoie la coordonnee de texture d'un sommet.
    public Point2 texcoords(int id )
    {
    	if(id >=0 && id < m_texcoords.size())
    		return m_texcoords.get(id);
    	else
    		return null;
    }
    
    //! ajoute un sommet.
    public int pushPosition(Point point )
    {
        m_positions.add( point );
        m_bbox.union(point);
        
        return m_positions.size() -1;
    }
    
  //! ajoute un ensemble de sommets.
    public void attachPositionBuffer( ArrayList<Point> positions )
    {
        m_positions= positions;
    }
    
  //! ajoute un ensemble de sommets.
    public void attachPositionBuffer(int n, ArrayList<Point> positions )
    {
    	if(n < positions.size() +1)
    	{
	    	ArrayList<Point> listPoint = new ArrayList<Point>();
	    	for (int i = 0; i < n; i++)
			{
	    		listPoint.add(positions.get(i));
			}
	    	m_positions = listPoint;
    	}
    }

    //! renvoie le nombre de sommets du maillage.
    public int positionCount( )
    {
        return (int) m_positions.size();
    }
    
    //! ajoute une normale.
    public int pushNormal(Normal normal )
    {
        m_normals.add( normal );
        return m_normals.size() -1;
    }

    //! ajoute un ensemble de normales
    public void attachNormalBuffer( ArrayList<Normal> normals )
    {
        m_normals= normals;
    }
    
    //! ajoute un ensemble de normales
    public void attachNormalBuffer(int n, ArrayList<Normal> normals )
    {
    	if(n < normals.size() + 1)
    	{
	    	ArrayList<Normal> listNormal = new ArrayList<Normal>();
	    	for (int i = 0; i < n; i++)
			{
	    		listNormal.add(normals.get(i));
			}
	    	m_normals = listNormal;
    	}
    }
    
    //! renvoie le nombre de normales du maillage.
    public int normalCount( )
    {
        return (int) m_normals.size();
    }
    
    //! ajoute une coordonnee de texture.
    public int pushTexcoord(Point2 texcoord )
    {
        m_texcoords.add( texcoord );
        return m_texcoords.size() -1;
    }
    
    //! ajoute un ensemble de coordonnees de texture.
    public void attachTexcoordBuffer(ArrayList<Point2> texcoords )
    {
        m_texcoords = texcoords;
    }
    
    //!  ajoute un ensemble de coordonnees de texture.
    public void attachTexcoordBuffer( int n, ArrayList<Point2> texcoords )
    {
    	if(n < texcoords.size() + 1)
    	{
	    	ArrayList<Point2> listPoint2 = new ArrayList<Point2>();
	    	for (int i = 0; i < n; i++)
			{
	    		listPoint2.add(texcoords.get(i));
			}
	    	m_texcoords = listPoint2;
    	}
    	
    }
    
  //! renvoie le nombre de coordonnees de textures des sommets du maillage.
    public int texcoordCount()
    {
        return (int) m_texcoords.size();
    }
    
   
   public int attachAttributeBuffer(Name semantic, ArrayList<Object> attributes)
    {
	   MeshBuffer buffer = attachAttributeBuffer(semantic, new Object());
	   
	   if(buffer == null)
           return -1;
       
       int n = (int) attributes.size();
       for(int i= 0; i < n; i++)
           buffer.push(attributes.get(i));
       return 0;
    }
    
     /*
    // TODO template< class T >
    public int attachAttributeBuffer( Name semantic, int n,  T attributes )
    {
        
        return 0;
    }*/
  
    //! renvoie un buffer d'apres son nom / semantique.
    public MeshBuffer findBuffer( Name semantic )
    {
        int n= (int) m_attributes_buffer.size();
        for(int i= 0; i < n; i++)
            if(m_attributes_buffer.get(i).getName() == semantic)
                return m_attributes_buffer.get(i);
        return null;
    }

    //! renvoie le nombre de buffers d'attributs.
    public int bufferCount( ) 
    {
        return (int) m_attributes_buffer.size();
    }
    
    //! renvoie un buffer.
    public MeshBuffer buffer( int id ) 
    {
    	if(id < m_attributes_buffer.size() && id >-1)
    		return m_attributes_buffer.get(id);
    	else
    		return null;
    }
    
    //! TODO insere un attribut dans l'ensemble associe. 
    /* void pushAttribute( Name semantic, T attribute )
    {
        MeshBuffer *buffer= findBuffer(semantic);
        if(buffer == NULL)
            return;
        buffer->push(attribute);
    }
     * */
     
    //! ajoute un triangle
    public void pushTriangle( int a, int b, int c, int material_id, int smooth_group )
    {
        m_indices.add(a);
        m_indices.add(b);
        m_indices.add(c);
        
        m_materials_id.add(material_id);
        m_smooth_groups.add(smooth_group);
    }
    
    //! ajoute un triangle
    public void pushTriangle( int a, int b, int c, int material_id)
    {
        m_indices.add(a);
        m_indices.add(b);
        m_indices.add(c);
        
        m_materials_id.add(material_id);
        m_smooth_groups.add(-1);
    }

    //! renvoie le nombre de triangles du maillage.
    public int triangleCount( ) 
    {
        return (int) m_indices.size() / 3;
    }
    
    //! renvoie le nombre d'indices du maillage.
    public int indiceCount( ) 
    {
        return (int) m_indices.size();
    }

  //! ajoute un submesh.
    public void pushSubMesh(int begin, int end, int material_id )
    {
        m_submeshes.add( new SubMesh(begin, end, material_id) );
    }
    
    //! renvoie le nombre de submesh.
    public int subMeshCount()
    {
        return (int) m_submeshes.size();
    }
    
    //! renvoie un submesh.
    public SubMesh subMesh(int submesh_id )
    {
    	if( submesh_id < m_submeshes.size())
    		return m_submeshes.get(submesh_id);
    	else
    		return null;
    }
    
    //! renvoie la matiere d'un submesh.
    public MeshMaterial subMeshMaterial( int id ) 
    {
        int material_id = m_submeshes.get(id).getMaterial_id();
        
        if(material_id < 0)
            return m_default_material;
        else
            return m_materials.get(material_id);
    }

    //! construit les submeshes, sequences de triangles utilisant la meme matiere.
    //! tri les triangles en fonction de leur indice de matiere, eventuellement utilise un autre tableau d'indices, cf map).
    int buildSubMeshes( ArrayList<Integer> map){return 0;}
    
    //! definit la matiere par defaut.
    public int pushDefaultMaterial( )
    {
        m_materials.add(m_default_material);
        
        return (int) m_materials.size() - 1;
    }
    
    //! insere une matiere.
    public int pushMaterial(MeshMaterial material)
    {
        m_materials.add(material);
        return (int) m_materials.size() - 1;
    }
    
    //! remplace l'ensemble de matieres.
    public void attachMaterials(ArrayList<MeshMaterial> materials )
    {
        m_materials = materials;
    }
    
    //! renvoie le nombre de matieres.
    public int materialCount( ) 
    {
        return (int) m_materials.size();
    }
    
    //! renvoie une matiere.
    public MeshMaterial material(int material_id ) 
   {
        if(m_materials.isEmpty())
            return m_default_material;
        else
            return m_materials.get(material_id);
    }
    
    //! renvoie la matiere d'un triangle.
    public MeshMaterial triangleMaterial(int id )
    {
        int material_id = m_materials_id.get(id);
        
        if(material_id < 0)
            return m_default_material;
        else
            return material(material_id);
    }

    //! renvoie l'indice de la matiere d'un triangle.
    public int getTriangleMaterialId(int id ) 
    {
        return m_materials_id.get(id);
    }
    
    //! renvoie un triangle 'indexe', les 3 indices des sommets du triangle.
    public MeshTriangle getMeshTriangle( int id ) 
    {
        if (id >=0 && id < triangleCount())
        	return new MeshTriangle(this, m_indices.get(3*id), m_indices.get(3*id +1), m_indices.get(3*id +2));
        else
        	return null;
    }

  //! renvoie un triangle 'geometrique' pour le calcul d'intersection avec un rayon.
    //! utiliser les resultats de l'intersection pour calculer la normale et les texcoords interpolees au point d'intersection.
    //! cf. Mesh::getUVPoint(), Mesh::getUVNormal(), Mesh::getUVTexcoord().
    public Triangle getTriangle( int id ) 
    {
         Point a = position(m_indices.get(3*id));
         Point b = position(m_indices.get(3*id +1));
         Point c = position(m_indices.get(3*id +2));
        
        return new Triangle(a, b, c);
    }
    
    //! renvoie un pn triangle.
    public PNTriangle getPNTriangle( int id ) throws Exception 
    {
        if(m_normals.size() != m_positions.size())
        {
            // pas de normales associees aux sommets, calcule la normale geometrique.
            Point a= position(m_indices.get(3*id));
            Point b= position(m_indices.get(3*id +1));
            Point c= position(m_indices.get(3*id +2));
            Vector ab = new Vector(a, b);
            Vector ac = new Vector (a, c);
            Normal nn = new Normal(Geometry.normalize(Geometry.cross(ab, ac)));
            
            return new PNTriangle(getTriangle(id), nn, nn, nn);
        }
        
        // renvoie les normales associees aux sommets du triangle
        Normal na= normal(m_indices.get(3*id));
        Normal nb= normal(m_indices.get(3*id +1));
        Normal nc= normal(m_indices.get(3*id +2));
        
        return new PNTriangle(getTriangle(id), na, nb, nc);
    }
    
    //! renvoie la boite englobante d'un triangle.
    public BBox getTriangleBBox(int id )
    {
        Point a = position(m_indices.get(3*id));
        Point b = position(m_indices.get(3*id +1));
        Point c = position(m_indices.get(3*id +2));
        
        BBox bbox = new BBox();
        bbox.union(a);
        bbox.union(b);
        bbox.union(c);
        
        return bbox;
    }
    
    //! renvoie l'aire d'un triangle.
    public float getTriangleArea(int id )
    {
        Point a = position(m_indices.get(3*id));
        Point b = position(m_indices.get(3*id +1));
        Point c = position(m_indices.get(3*id +2));
        Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        
        return Geometry.round(.5f * Geometry.cross(ab, ac).length());
    }

    //! calcule et renvoie la normale geometrique d'un triangle.
    public Normal getTriangleNormal( int id ) throws Exception 
    {
    	Point a = position(m_indices.get(3*id));
        Point b = position(m_indices.get(3*id +1));
        Point c = position(m_indices.get(3*id +2));
        Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        
        Vector n= Geometry.cross(Geometry.normalize(ab), Geometry.normalize(ac));
        
        return new Normal(Geometry.normalize(n));
    }

    //! interpole une position a l'interieur d'un triangle, connaissant les coordonnees barycentriques du point.
    //! convention p(u, v)= (1 - u - v) * a + u * b + v * c
    public Point getUVPoint(int id,float u, float v ) 
    {
    	Point a = position(m_indices.get(3*id));
        Point b = position(m_indices.get(3*id +1));
        Point c = position(m_indices.get(3*id +2));
        
        float w = 1.f - u - v;
        Point p1 = a.productFloat(w);
        Point p2 = b.productFloat(u);
        Point p3 = c.productFloat(v);
        
        p1.additionPoint(p2);
        p1.additionPoint(p3);
        
        return p1;
    }

    //! interpole une normale a l'interieur d'un triangle, connaissant ses coordonnees barycentriques.
    //! convention n(u, v)= (1 - u - v) * a + u * b + v * c
    public Normal getUVNormal( int id, float u, float v ) throws Exception 
    {
        if(m_normals.isEmpty())
            // renvoie la normale geometrique, si les normales des sommets n'existent pas
            return getTriangleNormal(id);
        
        Normal a = normal(m_indices.get(3*id));
        Normal b = normal(m_indices.get(3*id +1));
        Normal c = normal(m_indices.get(3*id +2));
        
        float w= 1.f - u - v;
        Normal n1 = a.productFloat(w);
        Normal n2 = b.productFloat(u);
        Normal n3 = c.productFloat(v);
        
        n1.additionNormal2(n2);
        n1.additionNormal2(n3);
        
        return Geometry.normalize(n1);
    }
    
  //! interpole une coordonnee de texture a l'interieur du triangle, connaissant ses coordonnees barycentriques.
    //! convention t(u, v)= (1 - u - v) * a + u * b + v * c
    public Point2 getUVTexcoord(int id,float u, float v ) 
    {
        if(m_texcoords.isEmpty())
            // pas de coordonnee de textures dans le maillage.
            return new Point2();
        
        Point2 a = texcoords(m_indices.get(3*id));
        Point2 b = texcoords(m_indices.get(3*id +1));
        Point2 c = texcoords(m_indices.get(3*id +2));

        float w= 1.f - u - v;
        
        Point2 p1 = a.productPoint2(w);
        Point2 p2 = b.productPoint2(u);
        Point2 p3 = c.productPoint2(v);
        
        p1.add(p2);
        p1.add(p3);
        
        return p1;
    }
    
    //! renvoie le smooth group du triangle.
    public int getTriangleSmoothGroup(int id )
    {
        if(m_smooth_groups.isEmpty())
            return -1;
        return m_smooth_groups.get(id);
    }
    
    public ArrayList<Point> getM_positions()
	{
		return m_positions;
	}

	public void setM_positions(ArrayList<Point> m_positions)
	{
		this.m_positions = m_positions;
	}

	public ArrayList<Normal> getM_normals()
	{
		return m_normals;
	}

	public void setM_normals(ArrayList<Normal> m_normals)
	{
		this.m_normals = m_normals;
	}

	public ArrayList<Point2> getM_texcoords()
	{
		return m_texcoords;
	}

	public void setM_texcoords(ArrayList<Point2> m_texcoords)
	{
		this.m_texcoords = m_texcoords;
	}

	public ArrayList<Integer> getM_indices()
	{
		return m_indices;
	}

	public void setM_indices(ArrayList<Integer> m_indices)
	{
		this.m_indices = m_indices;
	}

	public ArrayList<Integer> getM_materials_id()
	{
		return m_materials_id;
	}

	public void setM_materials_id(ArrayList<Integer> m_materials_id)
	{
		this.m_materials_id = m_materials_id;
	}

	public ArrayList<Integer> getM_smooth_groups()
	{
		return m_smooth_groups;
	}

	public void setM_smooth_groups(ArrayList<Integer> m_smooth_groups)
	{
		this.m_smooth_groups = m_smooth_groups;
	}

	public ArrayList<Integer> getM_position_adjacency()
	{
		return m_position_adjacency;
	}

	public void setM_position_adjacency(ArrayList<Integer> m_position_adjacency)
	{
		this.m_position_adjacency = m_position_adjacency;
	}

	public ArrayList<Integer> getM_adjacency()
	{
		return m_adjacency;
	}

	public void setM_adjacency(ArrayList<Integer> m_adjacency)
	{
		this.m_adjacency = m_adjacency;
	}

	public ArrayList<SubMesh> getM_submeshes()
	{
		return m_submeshes;
	}

	public void setM_submeshes(ArrayList<SubMesh> m_submeshes)
	{
		this.m_submeshes = m_submeshes;
	}

	public ArrayList<MeshMaterial> getM_materials()
	{
		return m_materials;
	}

	public void setM_materials(ArrayList<MeshMaterial> m_materials)
	{
		this.m_materials = m_materials;
	}

	public ArrayList<MeshBuffer> getM_attributes_buffer()
	{
		return m_attributes_buffer;
	}

	public void setM_attributes_buffer(ArrayList<MeshBuffer> m_attributes_buffer)
	{
		this.m_attributes_buffer = m_attributes_buffer;
	}

	public MeshMaterial getM_default_material()
	{
		return m_default_material;
	}

	public void setM_default_material(MeshMaterial m_default_material)
	{
		this.m_default_material = m_default_material;
	}

	public BBox getM_bbox()
	{
		return m_bbox;
	}

	public void setM_bbox(BBox m_bbox)
	{
		this.m_bbox = m_bbox;
	}
	
}