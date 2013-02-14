package lp.iem.gk;

import java.util.List;

public class Point {
	
	private float x;
	private float y;
	private float z;

	public Point() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Point(float v) {
		x = v;
		y = v;
		z = v;
	}

	public Point(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(Vector v) {
		x = v.getX();
		y = v.getY();
		z = v.getZ();
	}

	@Override
	public String toString() {
		// TODO : round the tenth
		return "Point{" + x + "," + y + "," + z + "}";
	}

	/**
	 * addition of a point and a vector, q = this + v
	 * @param v
	 * @return q
	 */
	public Point addition(Vector v) {
		return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
	}

	/**
	 * addition of a point and a vector, this = this + v
	 * @param v
	 */
	public void add(Vector v) {
		x += v.getX();
		y += v.getY();
		z += v.getZ();

	}

	/**
	 * subtraction of two points, v = this - p
	 * @param p
	 * @return v
	 */
	public Vector subtraction(Point p) {
		return new Vector(x - p.getX(), y - p.getY(), z - p.getZ());
	}

	/**
	 * subtraction of a point and a vector, p = this - v
	 * @param v
	 * @return p
	 */
	public Point subtraction(Vector v) {
		return new Point(x - v.getX(), y - v.getY(), z - v.getZ());
	}

	/**
	 * subtraction of a point and a vector, this = this - v
	 * @param v
	 */
	public void subtract(Vector v) {
		x -= v.getX();
		y -= v.getY();
		z -= v.getZ();
	}

	/**
	 * addition of two points, this = this + p
	 * @param p
	 */
	public void add(Point p) {
		x += p.getX();
		y += p.getY();
		z += p.getZ();
	}

	/**
	 * addition of two points, q = this + p
	 * @param p
	 * @return q
	 */
	public Point addition(Point p) { return new Point(x + p.getX(), y + p.getY(), z + p.getZ()); }

	/**
	 * product of a point by a real, q = this * n
	 * @param n
	 * @return q
	 */
	public Point productPoint(float n) { return new Point(x * n, y * n, z * n); }

	/**
	 * product of a point by a real, this = this * n
	 * @param n
	 */
	public void product(float n) {
		x = x * n;
		y = y * n;
		z = z * n;
	}

	/**
	 * division of point by a float, q = this / n
	 * @param n
	 * @return q
	 */
	public Point division(float n) {
		if (n != 0) {
			float inv = 1 / n;
			return new Point(inv * x, inv * y, inv * z);
		}
		return null;
	}

	/**
	 * division of point by a float, this = this / n
	 * @param n
	 */
	public void divide(float n) {
		if (n != 0) {
			float inv = 1 / n;
			x = x * inv;
			y = y * inv;
			z = z * inv;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final Point p = (Point) o;
		return (this.x == p.getX() && this.y == p.getY() && this.z == p.getZ());
	}

	public float get(int index) throws Exception {
		if (index < 0)
			throw new Exception("index < 0");
		else if (index > 2)
			throw new Exception("index > 2");
		else if (index == 0)
			return this.x;
		else if (index == 1)
			return this.y;
		else
			return this.z;
	}

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }

	public static float[] getFloatArray(List<Point> points) {
		float[] f = new float[points.size() * 3];
		for (int i = 0, j = 0; i < points.size(); i++) {
			Point p = points.get(i);
			f[j++] = p.getX();
			f[j++] = p.getY();
			f[j++] = p.getZ();
		}
		return f;
	}
}
