package lp.iem.gk;

import java.util.List;

public class Normal {
	
	private float x;
	private float y;
	private float z;

	public Normal() {
		this.x = 0.f;
		this.y = 0.f;
		this.z = 0.f;
	}

	public Normal(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Normal(Vector v) {
		x = v.getX();
		y = v.getY();
		z = v.getZ();
	}

	/**
	 * 
	 * @return the opposite
	 */
	public Normal opposite() {
		return new Normal(-x, -y, -z);
	}

	/**
	 * addition of two normals, q = this + n
	 * @param n
	 * @return q
	 */
	public Normal addition(Normal n) {
		return new Normal(x + n.getX(), y + n.getY(), z + n.getZ());
	}

	/**
	 * addition of two normals, this = this + n
	 * @param n
	 */
	public void add(Normal n) {
		x += n.getX();
		y += n.getY();
		z += n.getZ();
	}

	/**
	 * subtraction of two normals, q = this - n
	 * @param n
	 * @return q
	 */
	public Normal substraction(Normal n) {
		return new Normal(x - n.getX(), y - n.getY(), z - n.getZ());
	}

	/**
	 *  subtraction of two normals, this = this - n
	 * @param n
	 */
	public void substract(Normal n) {
		x -= n.getX();
		y -= n.getY();
		z -= n.getZ();
	}

	/**
	 * product of a normal wiha real, q = this * f
	 * @param f
	 * @return q
	 */
	public Normal productFloat(float f) {
		return new Normal(x * f, y * f, z * f);
	}

	/**
	 * product of a normal wiha real, this = this * f
	 * @param f
	 */
	public void product(float f) {
		x = x * f;
		y = y * f;
		z = z * f;
	}

	/**
	 * division of a normal by a real, q = this / f
	 * @param f
	 * @return q
	 */
	public Normal division(float f) {
		if (f != 0) {
			float inv = 1.f / f;
			return new Normal(x * inv, y * inv, z * inv);
		}
		return null;
	}

	/**
	 *  division of a normal by a real, this = this / f
	 * @param f
	 */
	public void divide(float f) {
		if (f != 0) {
			float inv = 1.f / f;
			x *= inv;
			y *= inv;
			z *= inv;
		}
	}

	/**
	 * @return the squared length of the vector
	 */
	public float lengthSquared() {
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	/** 
	 * @return the length of the vector
	 */
	public float length() {
		return (float) Math.sqrt(this.lengthSquared());
	}

	public float get(int index) throws Exception {
		if (index < 0)
			throw new Exception("index <0");
		else if (index > 2)
			throw new Exception("index > 2");
		else if (index == 0)
			return this.x;
		else if (index == 1)
			return this.y;
		else
			return this.z;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final Normal p = (Normal) o;
		return (this.x == p.getX() && this.y == p.getY() && this.z == p.getZ());
	}

	@Override
	public String toString() {
		// TODO : round the tenth
		return "Normal{" + x + ", " + y + ", " + z + "}";
	}

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }

	public static float[] getFloatArray(List<Normal> normals) {
		float[] f = new float[normals.size() * 3];
		for (int i = 0, j = 0; i < normals.size(); i++) {
			Normal n = normals.get(i);
			f[j++] = n.getX();
			f[j++] = n.getY();
			f[j++] = n.getZ();
		}
		return f;
	}

}
