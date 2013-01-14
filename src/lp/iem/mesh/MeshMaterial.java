package lp.iem.mesh;

import lp.iem.gk.Color;
import lp.iem.gk.Point;
import lp.iem.io.IOResource;

public class MeshMaterial extends IOResource
{
	private float kd;   //!< diffus.
	private float ks;   //!< reflechissant.
	private float n;    //!< puissance du reflet.
	private float ni;   //!< indice de refraction.
    
	private Color diffuse;     //!< couleur diffuse.
	private Color specular;    //!< couleur du reflet.
	private Color transmission;        //!< couleur du 'reflet' refracte.
	private Color emission;            //!< flux emis par la surface, en watt.
    
	private String diffuse_texture;        //!< nom de la texture.
	private String specular_texture;       //!< nom de la texture.
	private String name;   //!< nom de la matiere.

	 //! construction d'une matiere nommee.
	public MeshMaterial(String _name)
	{
		kd = 0.5f;
		ks = 0.f;
		n = 0.f;
		ni = 0.f;
		
		diffuse = new Color(1.f, 1.f, 1.f);
        specular  = new Color(0.f, 0.f, 0.f);
        transmission = new Color();
        emission = new Color();
        diffuse_texture = "";
        specular_texture = "";
        name = _name;
	}
	
		

	public float getKd()
	{
		return kd;
	}

	public float getKs()
	{
		return ks;
	}

	public float getN()
	{
		return n;
	}

	public float getNi()
	{
		return ni;
	}

	public Color getDiffuse()
	{
		return diffuse;
	}

	public Color getSpecular()
	{
		return specular;
	}

	public Color getTransmission()
	{
		return transmission;
	}

	public Color getEmission()
	{
		return emission;
	}

	public String getDiffuse_texture()
	{
		return diffuse_texture;
	}

	public String getSpecular_texture()
	{
		return specular_texture;
	}

	public String getName()
	{
		return name;
	}

}
