package lp.iem.gk;

public class Triangle
{
	Point a, b, c;
	float area;
	
	// constructeur vide
	public Triangle()
	{		
	}
	
	public Triangle(Point a, Point b, Point c)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		
		this.area = getArea();
	}

	//! calcule l'aire du triangle.
	public float getArea()
	{
		Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);
        
        return .5f * Geometry.cross(ab, ac).length();
        
	}

	//! calcule la normale du triangle.
	public Normal getNormal() throws Exception 
    {
        Vector ab = new Vector (a, b);
        Vector ac = new Vector (a, c);
        if(area < 0.000001f)
            return new Normal(0,0,0);
        
        return new Normal(Geometry.normalize(Geometry.cross(ab, ac)) );
    }

	//! changement de repere du vecteur 'v' global, renvoie le vecteur dans le repere local (bitangent, tangent, normal).
	public Vector local(Vector v ) throws Exception 
    {
        // construit le changement de repere
        Vector t= Geometry.normalize(new Vector(a, b) );
        Vector n= Geometry.normalize( Geometry.cross(t, new Vector(a,c)) );
        Vector b= Geometry.cross(t, n);
        
        // repere de l'objet
        return new Vector( Geometry.dot(v, b), Geometry.dot(v, t), Geometry.dot(v, n) );
    }

	//! changement de repere du vecteur 'v' local, renvoie le vecteur dans le repere global.
	public  Vector world(Vector v ) throws Exception 
    {
        // construit le changement de repere
        Vector t= Geometry.normalize( new Vector(a, b) );
        Vector n= Geometry.normalize( Geometry.cross(t, new Vector(a, c)) );
        Vector b= Geometry.cross(t, n);
        
        Vector v1 = b.productVector(v.getX());
        Vector v2 = b.productVector(v.getY());
        Vector v3 = b.productVector(v.getZ());
        
        v1.add(v2);
        v1.add(v3);
        
        return v1;
        
    }
	
	//! calcule la boite englobante du triangle.
	public BBox getBBox( )
    {
        BBox bbox = new BBox(a, b);
        bbox.union(c);
        
        return bbox;
    }

	//! renvoie un triangle transforme par 't'.
    public Triangle transform(Transform t ) throws Exception
    {
        return new Triangle( t.transform(a), t.transform(b), t.transform(c));
    }
	

	//! intersection avec un rayon.
    //! renvoie faux s'il n'y a pas d'intersection valide, une intersection peut exister mais peut ne pas se trouver dans l'intervalle [0 htmax] du rayon. \n
    //! renvoie vrai + les coordonnees barycentriques (ru, rv) du point d'intersection + sa position le long du rayon (rt). \n
    //! convention barycentrique : t(u, v)= (1 - u - v) * a + u * b + v * c \n
    //! utiliser Mesh::getUVNormal() et Mesh::getUVTexCoord() pour interpoler les attributs du point d'intersection. \n
    //! ou PNTriangle::getUVNormal(). \n
    
    
    /*! le parametre  htmax permet de trouver tres facilement l'intersection la plus proche de l'origine du rayon.
    \code
        float t= ray.tmax;      // ou t= HUGE_VAL; la plus grande distance le long du rayon.
        // rechercher le triangle le plus proche de l'origine du rayon
        for(int i= 0; i < n; i++)
        {
            float rt;
            float ru, rv;
            if(triangle[i].Intersect(ray, t, rt, ru, rv))
                t= rt;
        }
    \endcode
    */
	
	public Boolean Intersect(Ray ray, float htmax,float rt, float ru, float rv ) 
    {
		/* begin calculating determinant - also used to calculate U parameter */
        Vector ac = new Vector (a, c);
        Vector pvec = Geometry.cross(ray.getDirection(), ac);
        
        /* if determinant is near zero, ray lies in plane of triangle */
        Vector ab = new Vector(a, b);
        float det= Geometry.dot(ab, pvec);
        if (det > -Geometry.EPSILON && det < Geometry.EPSILON)
            return false;
        
        float inv_det= 1.0f / det;
        
        /* calculate distance from vert0 to ray origin */
         Vector tvec = new Vector(a, ray.getOrigin());
        
        /* calculate U parameter and test bounds */
        float u= Geometry.dot(tvec, pvec) * inv_det;
        if(u < 0.0f || u > 1.0f)
            return false;
        
        /* prepare to test V parameter */
        Vector qvec= Geometry.cross(tvec, ab);
        
        /* calculate V parameter and test bounds */
        float v= Geometry.dot(ray.getDirection(), qvec) * inv_det;
        if(v < 0.0f || u + v > 1.0f)
            return false;
        
        /* calculate t, ray intersects triangle */
        rt= Geometry.dot(ac, qvec) * inv_det;
        ru= u;
        rv= v;
        
        // ne renvoie vrai que si l'intersection est valide (comprise entre tmin et tmax du rayon)
        //~ return (rt < htmax && rt > ray.tmin);
        return (rt < htmax && rt > Geometry.RAY_EPSILON);
    }
	
	
	//! renvoie un point a l'interieur du triangle connaissant ses coordonnees barycentriques.
    //! convention p(u, v)= (1 - u - v) * a + u * b + v * c
    public Point getUVPoint( float u,float v ) 
    {
        float w= 1.f - u - v;
        Point p1 = a.productFloat(w);
        Point p2 = b.productFloat(u);
        Point p3 = c.productFloat(v);
        
        p1.additionPoint(p2);
        p1.additionPoint(p3);
        
        return p1;
    }

  //! choisit un point aleatoirement a la surface du triangle et renvoie la probabilite de l'avoir choisi.
  //! \param u1, u2 valeurs aleatoires entre [0 .. 1] utilisées pour le tirage aleatoire.
    
    public float sampleUniform( float u1,  float u2, Point p ) 
    {
        float s = (float) Math.sqrt(u1);
        float t = u2;
        float u = 1.f - s;
        float v = (1.f - t) * s;
        float w = t * s;
        
        Point ua = a.productFloat(u);
        Point vb = b.productFloat(v);
        Point wc = c.productFloat(w);
        
        
        p.additionPoint(ua);       
        p.additionPoint(vb);      
        p.additionPoint(wc);
        
        return 1.f / area;
    }

  //! choisit un point aleatoirement a la surface du triangle et renvoie la probabilite de l'avoir choisi.
    public float sampleUniform( Sampler sampler, Point p ) 
    {
        return sampleUniform(sampler.uniformFloat(), sampler.uniformFloat(), p);
    }
    
    //! choisit une position aleatoirement a la surface du triangle et renvoie la probabilite de l'avoir choisie.
    public float[] sampleUniformUV( float u1, float u2, float u, float v ) 
    {
        float s= (float) Math.sqrt(u1);
        float t= u2;
        u= 1.f - s;
        v= (1.f - t) * s;
        
        // val : tab contenant u, v et 1.f / area
        float[] val = new float[3];
        val[0] = u;
        val[1] = v;
        val[2] = 1.f / area;
        
        return val;
    }
    
  //! choisit une position aleatoirement a la surface du triangle et renvoie la probabilite de l'avoir choisie.
   public float[] sampleUniformUV( Sampler sampler, float u, float v )
   {
        return sampleUniformUV(sampler.uniformFloat(), sampler.uniformFloat(), u, v);
   }
    
    //! renvoie la probabilite de choisir le point p aleatoirement.
    //! remarque: le point doit appartenir au triangle.
   public float pdfUniform( Point p ) 
   {
	    return 1.f / area;
   }

public Point getA()
{
	return a;
}

public void setA(Point a)
{
	this.a = a;
}

public Point getB()
{
	return b;
}

public void setB(Point b)
{
	this.b = b;
}

public Point getC()
{
	return c;
}

public void setC(Point c)
{
	this.c = c;
}
}
