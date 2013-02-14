package lp.iem.mesh;

public class Vertex {
	
	private int[] indices = new int[4];

	public Vertex() { }

	public Vertex(int m, int p, int n, int t) {
		indices[0] = m;
		indices[1] = p;
		indices[2] = n;
		indices[3] = t;
	}

	/**
	 * comparison of two vertex
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean less(Vertex a, Vertex b) {
		// defines a lexicographic order on the materials + 3 indices : position, normal, texcoord
		for (int i = 0; i < 4; i++) {
			if (a.indices[i] < b.indices[i]) // return true when a < b
				return true;
			else if (a.indices[i] > b.indices[i])
				return false;
		}
		return false;
	}

	/**
	 * 
	 * @return material index of thsi vertex
	 */
	int material() { return indices[0]; }

	/**
	 * 
	 * @return the index of the position of the vertex
	 */
	int position() {
		return indices[1];
	}

	/**
	 * 
	 * @return the index of the normal vertex
	 */
	int normal() {
		return indices[2];
	}

	/**
	 * 
	 * @return  the index of the texture coordinate of vertex
	 */
	int texcoord() {
		return indices[3];
	}

	static public boolean superieur(Vertex a, Vertex b) {
		return less(a, b);
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final Vertex v = (Vertex) o;
        return this.indices[0] == v.indices[0] && this.indices[1] == v.indices[1] && this.indices[2] == v.indices[2]
        		&& this.indices[3] == v.indices[3];
	}
	
	@Override
	public String toString(){
		return "Vertex{m=" + indices[0] + "; p=" + indices[1] + "; n=" + indices[2] + "; t=" + indices[3] + "}";		
	}
}
