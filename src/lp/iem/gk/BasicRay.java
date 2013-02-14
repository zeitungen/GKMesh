package lp.iem.gk;

public class BasicRay{

	private Point o;    	// origin
	private float tmax;   	// valid interval along the radius
	private Vector d;   	// direction
	private int id;    		// identifying the radius
    
    public BasicRay(){
    	tmax = Geometry.HUGE_VAL;
    	id = -1;
    }
    
    public BasicRay(int _id){
    	tmax = Geometry.HUGE_VAL;
    	id = _id;
    }
    
    /**
     * constructor (origin, direction). direction is a unit vector.
     * @param origin
     * @param direction
     * @param _id
     */
    public BasicRay( Point origin, Vector direction, int _id ){
    	o = origin;
    	tmax = Geometry.HUGE_VAL;
    	d = direction;
    	id = _id;
    }
   
    /**
     * manufacturer (origin, direction). direction is a unit vector.
     * @param origin
     * @param direction
     */
    public BasicRay( Point origin, Vector direction){
    	o = origin;
    	tmax = Geometry.HUGE_VAL;
    	d = direction;
    	id = -1;
    }

    /**
     * constructor (origin, destination).
     * @param origin
     * @param destination
     * @param _id
     */
    public BasicRay(Point origin, Point destination, int _id){
    	o = origin;
    	tmax = 1.f - Geometry.RAY_EPSILON;
    	d = new Vector(origin, destination);
    	id = _id;
    }
    
 	/**
 	 * constructor (origin, destination).
 	 * @param origin
 	 * @param destination
 	 */
    public BasicRay(Point origin, Point destination){
    	o = origin;
    	tmax = 1.f - Geometry.RAY_EPSILON;
    	d = new Vector(origin, destination);
    	id = -1;
    }

    /**
     * construct the a point to the abscissa t
     * @param t
     * @return a
     */
    public Point abscisse(float t){
    	d.product(t);
        return o.addition(d); 
    }

	public Point getOrigin(){ return o; }
	public float getTmax(){ return tmax; }
	public Vector getDirection(){ return d; }
	public int getId(){ return id; }

	public void setOrigin(Point o){ this.o = o; }
	public void setTmax(float tmax){ this.tmax = tmax; }
	public void setDirection(Vector d){ this.d = d; }
	public void setId(int id){ this.id = id; }
}
