package lp.iem.gk;

public class Vector {
	private float x;
	private float y;
	private float z;
	
	public Vector(){
		x = 0.f;
		y = 0.f;
		z = 0.f;
	}
	
	public Vector(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Point p){
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}

	public Vector(Normal n){
		this.x = n.getX();
		this.y = n.getY();
		this.z = n.getZ();
	}
		
	public Vector(Point p, Point q){
		this.x = q.getX() - p.getX();
		this.y = q.getY() - p.getY();
		this.z = q.getZ() - p.getZ();
	}
	
	@Override
	public String toString(){
		// TODO : arrondire au dixieme
		return "Vector{" + x +";" + y +";" + z + "}";
	}

	public float getX(){ return x; }
	public float getY() { return y; }
	public float getZ() { return z; }

	public float get(int index) throws Exception{
		if(index < 0) throw new Exception("index < 0");
		else if(index > 3) throw new Exception("index > 3");	
		else if(index == 0) return this.getX();
		else if(index == 1) return this.getY();
		else return this.getZ();
	}
	
	public void setX(float x){ this.x = x; }
	public void setY(float y){ this.y = y; }
	public void setZ(float z){ this.z = z; }
	
	public void set(int index, float f) throws Exception{
		if(index < 0) throw new Exception("index < 0");
		else if(index > 3) throw new Exception("index > 3");
		if(index == 0) setX(f);
		else if(index == 1) setY(f);
		else if(index == 2) setZ(f);
	}
	
	public void setPosition(float x, float y, float z){
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	/**
	 * addition of 2 vectors, w = u + v
	 * @param v
	 * @return w
	 */
	public Vector addition(Vector v){
		return new Vector(this.x + v.getX(), this.y + v.getY(), this.z + v.getZ());
	}
	
	/**
	 * addition of two vectors, u = u + v
	 * @param v
	 */
	public void add(Vector v){
		this.x += v.getX();
		this.y += v.getY();
		this.z += v.getZ();
	}

	/**
	 * subtraction of 2 vectors, w = u - v
	 * @param v
	 * @return w
	 */
	public Vector subtraction(Vector v){
		return new Vector(this.x - v.getX(), this.y - v.getY(), this.z - v.getZ());
	}
	
	/**
	 * subtraction of 2 vectors, u = u - v
	 * @param v
	 */
	public void substract(Vector v){
		this.x -= v.getX();
		this.y -= v.getY();
		this.z -= v.getZ();
	}
	
	/**
	 * product by a real, w = u * f
	 * @param f
	 * @return w
	 */
	public Vector productVector(float f){
		return new Vector(this.x * f, this.y * f, this.z * f);
	}
	
	/**
	 * product by a real, u = u * f
	 * @param f
	 */
	public void product(float f){
		this.x *= f;
		this.y *= f;
		this.z *= f;
	}
	
	/**
	 * division by a real, w = u / f
	 * @param f
	 * @return w
	 * @throws Exception
	 */
	public Vector division(float f) throws Exception{
		if(f == 0) throw new Exception("Divid by zero");
		float inv = 1.f / f;
		return this.productVector(inv);
	}
	
	/**
	 * division by a real, u = u * f
	 * @param f
	 * @throws Exception
	 */
	public void divid(float f) throws Exception{
		if(f == 0) throw new Exception("Divid by zero");
		float inv = 1.f / f;
		this.product(inv);
	}
	
	/**
	 * negation of a vector, w = - u
	 * @return w
	 */
	public Vector negation(){ return new Vector(-this.getX(), -this.getY(), -this.getZ()); }


	/**
	 * calcul the squared length
	 * @return the squared length
	 */
	public float lengthSquared(){ return this.x * this.x + this.y * this.y + this.z * this.z; }
	
	/**
	 * calcul the vector length
	 * @return the length
	 */
	public float length(){ return (float)Math.sqrt(this.lengthSquared()); }
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final Vector p = (Vector) o;
        return this.x == p.getX() && this.y == p.getY() && this.z == p.getZ();
	}
}
