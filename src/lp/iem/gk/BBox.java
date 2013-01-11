package lp.iem.gk;

public class BBox {
	
	private Point min;
	private Point max;
	
	public BBox(){
		this.clear();
	}
	
	public BBox(Point p1, Point p2){
		float minx, miny, minz;
		float maxx, maxy, maxz;
		
		if(p1.getX() > p2.getX()){
			maxx = p1.getX();
			minx = p2.getX();
		}else {
			maxx = p2.getX();
			minx = p1.getX();
		}
		
		
		if(p1.getY() > p2.getY()){
			maxy = p1.getY();
			miny = p2.getY();
		}else {
			maxy = p2.getY();
			miny = p1.getY();
		}
		
		if(p1.getZ() > p2.getZ()){
			maxz = p1.getZ();
			minz = p2.getZ();
		}else {
			maxz = p2.getZ();
			minz = p1.getZ();
		}
		
		this.max = new Point(maxx, maxy, maxz);
		this.min = new Point(minx, miny, minz);
	}
	
	public BBox(Point p){
		this.min = p;
		this.max = p;
	}
	
	public Point getMin() { return min; }
	public Point getMax() { return max; }
	public Vector getVector(){ return new Vector(this.min, this.max); }
	
	public Point get(int index) throws Exception{
		if(index < 0) throw new Exception("index < 0");
		else if(index > 1) throw new Exception("index > 1");
		else if(index == 0) return this.min;
		else return this.max;
	}
	
	/**
	 * reinit the ends
	 */
	public void clear(){
		this.min = new Point(-Geometry.HUGE_VAL, -Geometry.HUGE_VAL, -Geometry.HUGE_VAL);
		this.max = new Point(Geometry.HUGE_VAL, Geometry.HUGE_VAL, Geometry.HUGE_VAL);
	}
	
	@Override
	public String toString(){
		Vector d = this.getVector();
		
		String str = "BBox{";
		str += "[" + this.min.getX() + ";" + this.min.getY() + ";" + this.min.getZ() + "]";
		str += " x ";
		str += "[" + this.max.getX() + ";" + this.max.getY() + ";" + this.max.getZ() + "]";
		str += " extends ";
		str += "[" + d.getX() + ";" + d.getY() + ";" + d.getZ() + "]";
		str += "}";
		
		return str;
	}
	
	/**
	 * add a point into the aabox
	 * @param sensation
	 * @param p
	 * @return the new aabox
	 */
	public static BBox union(BBox sensation, Point p){
	    BBox ret = sensation;
	    
	    if(sensation.getMin().getX() > p.getX()) ret.getMin().setX(p.getX());
	    if(sensation.getMin().getY() > p.getY()) ret.getMin().setY(p.getY());
	    if(sensation.getMin().getZ() > p.getZ()) ret.getMin().setZ(p.getZ());
	    
	    if(sensation.getMax().getX() < p.getX()) ret.getMax().setX(p.getX());
	    if(sensation.getMax().getY() < p.getY()) ret.getMax().setY(p.getY());
	    if(sensation.getMax().getZ() < p.getZ()) ret.getMax().setZ(p.getZ());
	       
	    return ret;
	}

	/**
	 * add a aabox into other aabox
	 * @param b
	 * @param c
	 * @return the new aabox
	 */
	public static BBox union(BBox b, BBox c){
	    BBox ret = new BBox();
	    
	    if(b.getMin().getX() < c.getMin().getX()) ret.getMin().setX(b.getMin().getX());
	    else ret.getMin().setX(c.getMin().getX());
	    
	    if(b.getMin().getY() < c.getMin().getY()) ret.getMin().setY(b.getMin().getY());
	    else ret.getMin().setY(c.getMin().getY());
	    
	    if(b.getMin().getZ() < c.getMin().getZ()) ret.getMin().setZ(b.getMin().getZ());
	    else ret.getMin().setZ(c.getMin().getZ());
	    
	    if(b.getMax().getX() > c.getMax().getX()) ret.getMax().setX(b.getMax().getX());
	    else ret.getMax().setX(c.getMax().getX());
	    
	    if(b.getMax().getY() > c.getMax().getY()) ret.getMax().setY(b.getMax().getY());
	    else ret.getMax().setY(c.getMax().getY());
	    
	    if(b.getMax().getZ() > c.getMax().getZ()) ret.getMax().setZ(b.getMax().getZ());
	    else ret.getMax().setZ(c.getMax().getZ());
	        
	    return ret;
	}

	/**
	 * add a aabox
	 * @param b
	 */
	public void union(BBox b){
        if(b.getMin().getX() < this.min.getX()) this.min.setX(b.getMin().getX());
        if(b.getMin().getY() < this.min.getY()) this.min.setY(b.getMin().getY());
        if(b.getMin().getZ() < this.min.getZ()) this.min.setZ(b.getMin().getZ());
            
        if(b.getMax().getX() > this.max.getX()) this.max.setX(b.getMax().getX());
        if(b.getMax().getY() > this.max.getY()) this.max.setY(b.getMax().getY());
        if(b.getMax().getZ() > this.max.getZ()) this.max.setZ(b.getMax().getZ());
	}
	
	/**
	 * add a point to a aabox
	 * @param p
	 */
	public void union(Point p){
        if(p.getX() < this.min.getX()) this.min.setX(p.getX());
        if(p.getY() < this.min.getY()) this.min.setY(p.getY());
        if(p.getZ() < this.min.getZ()) this.min.setZ(p.getZ());
        
        if(p.getX() > this.max.getX()) this.max.setX(p.getX());
        if(p.getY() > this.max.getY()) this.max.setY(p.getY());
        if(p.getZ() > this.max.getZ()) this.max.setZ(p.getZ());
	}

	/**
	 * check the intersect of two aabox
	 * @param b
	 * @return
	 */
	public boolean overlaps(BBox b){
        boolean x = ( this.max.getX() >= b.getMin().getX() ) && ( this.min.getX() <= b.getMax().getX() );
        boolean y = ( this.max.getY() >= b.getMin().getY() ) && ( this.min.getY() <= b.getMax().getY() );
        boolean z = ( this.max.getZ() >= b.getMin().getZ() ) && ( this.min.getZ() <= b.getMax().getZ() );
        return ( x && y && z );
	}
	
	/**
	 * intersect with an aabox
	 * @param b
	 */
	public void intersection(BBox b){
		if(this.min.getX() < b.getMin().getX()) this.min.setX(b.getMin().getX());
		if(this.min.getY() < b.getMin().getY()) this.min.setY(b.getMin().getY());
		if(this.min.getZ() < b.getMin().getZ()) this.min.setZ(b.getMin().getZ());
		
		if(this.max.getX() > b.getMax().getX()) this.max.setX(b.getMax().getX());
		if(this.max.getY() > b.getMax().getY()) this.max.setY(b.getMax().getY());
		if(this.max.getZ() > b.getMax().getZ()) this.max.setZ(b.getMax().getZ());
	}
	
	/**
	 * intersect of two aaboxes
	 * @param b
	 * @param c
	 * @return the intersect aabox
	 */
	public static BBox intersection(BBox b, BBox c){
	    BBox ret = new BBox();
	    
	    if(b.getMin().getX() > c.getMin().getX()) ret.getMin().setX(b.getMin().getX());
	    else ret.getMin().setX(c.getMin().getX());
	    
	    if(b.getMin().getY() > c.getMin().getY()) ret.getMin().setY(b.getMin().getY());
	    else ret.getMin().setY(c.getMin().getY());
	    
	    if(b.getMin().getZ() > c.getMin().getZ()) ret.getMin().setZ(b.getMin().getZ());
	    else ret.getMin().setZ(c.getMin().getZ());
	    
	    if(b.getMax().getX() < c.getMax().getX()) ret.getMax().setX(b.getMax().getX());
	    else ret.getMax().setX(c.getMax().getX());
	    
	    if(b.getMax().getY() < c.getMax().getY()) ret.getMax().setY(b.getMax().getY());
	    else ret.getMax().setY(c.getMax().getY());
	    
	    if(b.getMax().getZ() < c.getMax().getZ()) ret.getMax().setZ(b.getMax().getZ());
	    else ret.getMax().setZ(c.getMax().getZ());
	        
	    return ret;
	}
	
	/**
	 * check if the aabox is empty
	 * @return
	 */
	public boolean isEmpty(){
		boolean x = this.max.getX() < this.min.getX();
		boolean y = this.max.getY() < this.min.getY();
		boolean z = this.max.getZ() < this.min.getZ();
		return x && y && z;
	}

	/**
	 * check if the Point p is into the aabox
	 * @param p
	 * @return
	 */
	public boolean inside(Point p){
		return p.getX() >= this.min.getX() && p.getX() <= this.max.getX()
			&& p.getY() >= this.min.getY() && p.getY() <= this.max.getY()
			&& p.getZ() >= this.min.getZ() && p.getZ() <= this.max.getZ();
	}

	/**
	 * expand the aabox of delta into all directions
	 * @param delta
	 */
	public void expand(float delta){
		Vector v = new Vector(delta, delta, delta);
		this.min.substraction(v);
		this.max.addition(v);
	}
	
	/**
	 * get center of the aabox on the axis axis
	 * @param axis
	 * @return center
	 * @throws Exception
	 */
	public float getCenter(int axis) throws Exception{
		return (this.min.get(axis) + this.max.get(axis)) * .5f;
	}

	/**
	 * 
	 * @return the volume of aabox
	 */
	public float volume(){
		Vector v = this.getVector();
		return v.getX() * v.getY() * v.getZ();
	}

	/**
	 * 
	 * @return area of the aabox
	 */
	public float surfaceArea(){
		Vector v = this.getVector();
		return 2.f * v.getX() * v.getY() + 2.f * v.getX() * v.getZ() + 2.f * v.getY() * v.getZ();
	}

	/**
	 * return the index of the longest axis
	 * @return 0 for x, 1 for y, 2 for z
	 */
	public int maxExtent(){
		Vector v = this.getVector();
		if(v.getX() > v.getY() && v.getX() > v.getZ()) return 0;
		else if(v.getY() > v.getZ()) return 1;
		else return 2;
	}

}
