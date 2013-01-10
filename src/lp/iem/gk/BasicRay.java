package lp.iem.gk;

public class BasicRay
{

	Point o;    //!< origine.
    float tmax;   //!< intervalle valide le long du rayon.
    Vector d;   //!< direction.
    int id;    //!< identifiant du rayon
    
    public BasicRay()
    {
    	tmax = Geometry.HUGE_VAL;
    	id = -1;
    }
    
    public BasicRay(int _id)
    {
    	tmax = Geometry.HUGE_VAL;
    	id = _id;
    }
    
    //! constructeur (origine, direction). direction est un vecteur unitaire.
    public BasicRay( Point origin, Vector direction, int _id )
    {
    	o = origin;
    	tmax = Geometry.HUGE_VAL;
    	d = direction;
    	id = _id;
    }
   
    //! constructeur (origine, direction). direction est un vecteur unitaire.
    public BasicRay( Point origin, Vector direction)
    {
    	o = origin;
    	tmax = Geometry.HUGE_VAL;
    	d = direction;
    	id = -1;
    }

  //! constructeur (origine, destination).
    public BasicRay(Point origin, Point destination, int _id)
    {
    	o = origin;
    	tmax = 1.f - Geometry.RAY_EPSILON;
    	d = new Vector(origin, destination);
    	id = _id;
    }
    
  //! constructeur (origine, destination).
    public BasicRay(Point origin, Point destination)
    {
    	o = origin;
    	tmax = 1.f - Geometry.RAY_EPSILON;
    	d = new Vector(origin, destination);
    	id = -1;
    }

    //! construit le point a l'abscisse 't'.
    public Point abscisse(float t ) 
    {
    	d.product(t);
        return o.additionVector(d); 
    }
}
