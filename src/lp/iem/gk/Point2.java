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
	
	public Point2 additionVector(Point2 p){
		return new Point2(this.x + p.getX(), this.y + p.getY());
	}
	
	public void addition(Point2 p){
		this.x += p.getX();
		this.y += p.getY();
	}
	
}
