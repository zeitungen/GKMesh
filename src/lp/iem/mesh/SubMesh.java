package lp.iem.mesh;

/**
 * representation of a part of a mesh associated with a single matter identified by its index, material_id
 * @author Maxime Journaux and Maxence Trinquet
 */
public class SubMesh {
	private int begin; 			// first vertex submesh
	private int end; 			// last vertex submesh
	private int material_id; 	// index of the submesh associated material, cf. Mesh.materialsId.

	public SubMesh() {
		begin = 0;
		end = 0;
		material_id = 0;
	}

	public SubMesh(int _beg, int _end, int _matId) {
		begin = _beg;
		end = _end;
		material_id = _matId;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(int material_id) {
		this.material_id = material_id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final SubMesh p = (SubMesh) o;
		return (this.begin == p.getBegin() && this.end == p.getEnd() && this.material_id == p
				.getMaterial_id());
	}

}
