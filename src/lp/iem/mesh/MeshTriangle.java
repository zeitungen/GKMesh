package lp.iem.mesh;


/**
 * Indexed representation vertices of a triangle
 * @author Maxime Journaux and Maxence Trinquet
 */
public class MeshTriangle {

	Mesh mesh;
	int a, b, c;

	public MeshTriangle() {
		mesh = null;
		a = -1;
		b = -1;
		c = -1;
	}

	public MeshTriangle(Mesh _mesh, int _a, int _b, int _c) {
		mesh = _mesh;
		a = _a;
		b = _b;
		c = _c;
	}

	/**
	 * 
	 * @param index
	 * @return vertex k index
	 * @throws Exception
	 */
	public int vertex(int index) throws Exception {
		if (index < 0)
			throw new Exception("index <0");
		else if (index > 2)
			throw new Exception("index > 2");
		else if (index == 0)
			return this.a;
		else if (index == 1)
			return this.b;
		else
			return this.c;
	}

	/**
	 * 
	 * @param index
	 * @return vertex k index
	 * @throws Exception
	 */
	public int get(int index) throws Exception {
		return vertex(index);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final MeshTriangle p = (MeshTriangle) o;
		return (this.mesh == p.mesh && this.a == p.a && this.b == p.b && this.c == p.c);
	}

}
