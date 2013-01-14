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
    
    // TODO implementation IOResource
    public Mesh()
    {
    	super();
    	// m_default_material("default");
    }
    
    //! declare un ensemble d'attributs point 2d.
    public MeshBuffer attachAttributeBuffer( Name semantic,  Point2 attribute_tag )
    {
    	MeshBuffer buffer = findBuffer(semantic);
        if(buffer != null)
            return null;        // deja attache
        
        buffer= new MeshBuffer(semantic, 2);
        m_attributes_buffer.add(buffer);
        return buffer;
    }
    
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
    
  //! TODO ajoute un ensemble de sommets.
    public void attachPositionBuffer(int n, Point positions )
    {
       // m_position s= ArrayList<Point>(positions[0], positions[n]);
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
    
    //! TODO  ajoute un ensemble de normales
    public void attachNormalBuffer(int n, Normal normals )
    {
        //m_normals=  ArrayList<Normal>(normals[0], normals[n]);
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
    
    //! TODO ajoute un ensemble de coordonnees de texture.
    public void attachTexcoordBuffer( int n, Point2 texcoords )
    {
        //m_texcoords = ArrayList<Point2>(texcoords[0], texcoords[n]);
    }
    
  //! renvoie le nombre de coordonnees de textures des sommets du maillage.
    public int texcoordCount()
    {
        return (int) m_texcoords.size();
    }
    
    //TODO  template< class T >
    //int attachAttributeBuffer( const Name& semantic, const std::vector<T>& attributes )
    
    //! renvoie un buffer d'apres son nom / semantique.
    public MeshBuffer findBuffer( Name semantic )
    {
        int n= (int) m_attributes_buffer.size();
        for(int i= 0; i < n; i++)
            if(m_attributes_buffer.get(i).getName() == semantic)
                return m_attributes_buffer.get(i);
        
        return null;
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
