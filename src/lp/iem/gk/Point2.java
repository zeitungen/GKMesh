package lp.iem.gk;

public class Point2 {
	
	private float x;
	private float y;
	
	public Point2(){
		this.x = 0.f;
		this.y = 0.f;
	}
	
	public Point2(float x, float y){
		this.x = x;
		this.y = y;
	}

	public float getX() { return x; }
	public float getY() { return y; }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	
	/**
	 * addition of two vectors, w = u + p
	 * @param p
	 * @return w
	 */
	public Point2 addition(Point2 p){
		return new Point2(this.x + p.getX(), this.y + p.getY());
	}
	
	/**
	 * addition of two vectors, u = u + p
	 * @param p
	 */
	public void add(Point2 p){
		this.x += p.getX();
		this.y += p.getY();
	}
	
	/**
	 * subtraction of two vectors, w = u - p
	 * @param p
	 * @return w
	 */
	public Point2 subtraction(Point2 p){
		return new Point2(this.x - p.getX(), this.y - p.getY());
	}
	
	/**
	 * subtraction of two vectors, u = u - p
	 * @param p
	 */
	public void substract(Point2 p){
		this.x -= p.getX();
		this.y -= p.getY();
	}
	
	/**
	 * product by a real, w = f * u
	 * @param f
	 * @return w
	 */
	public Point2 productPoint2(float f){
		return new Point2(this.x * f, this.y * f);
	}
	
	/**
	 * product by a real, u = f * u
	 * @param f
	 */
	public void product(float f){
		this.x *= f;
		this.y *= f;
	}
	
	/**
	 * division by a real, w = u / f
	 * @param f
	 * @return w
	 * @throws Exception : division by zero
	 */
	public Point2 division(float f) throws Exception{
		if(f == 0) throw new Exception("Division by zero");
		float inv = 1.f / f;
		return this.productPoint2(inv);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final Point2 p = (Point2) o;
        return this.x == p.getX() && this.y == p.getY();
	}

	@Override
	public String toString() {
		return "Point2{" + this.x + ";" + this.y + "}";
	}
	
}
