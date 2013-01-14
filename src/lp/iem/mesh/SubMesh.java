package lp.iem.mesh;

//! representation d'une partie d'un maillage associee a une matiere unique identifiee par son indice 'material_id'.

public class SubMesh
{
	private int begin;        //!< premier vertex du submesh.
    private int end;  //!< dernier vertex du submesh.
    private int material_id;  //!< indice de la matiere associee au submesh, cf. gk::Mesh::m_materials.
	
    public SubMesh()
    {
    	begin = 0;
    	end = 0;
    	material_id = 0;
    }
    
    public SubMesh(int _beg, int _end, int _matId)
    {
    	begin = _beg;
    	end = _end;
    	material_id = _matId;
    }
    
    
    public int getBegin()
	{
		return begin;
	}
	public void setBegin(int begin)
	{
		this.begin = begin;
	}
	public int getEnd()
	{
		return end;
	}
	public void setEnd(int end)
	{
		this.end = end;
	}
	public int getMaterial_id()
	{
		return material_id;
	}
	public void setMaterial_id(int material_id)
	{
		this.material_id = material_id;
	}

}
