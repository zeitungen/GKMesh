package lp.iem.mesh;

public class TriangleOBJ implements Comparable<TriangleOBJ>{
	
	private int[] indices = new int[3];
	private int materialId;
	private int smoothId;
	
	public TriangleOBJ(){
		materialId = -1;
		smoothId = -1;
	}
	
	public TriangleOBJ(int a, int b, int c){
		indices[0] = a;
		indices[1] = b;
		indices[2] = c;
		materialId = -1;
		smoothId = -1;
	}

	public void setMaterialId(int materialId) { this.materialId = materialId; }
	public void setSmoothId(int smoothId) { this.smoothId = smoothId; }
	
	public int a(){ return indices[0]; }
	public int b(){ return indices[1]; }
	public int c(){ return indices[2]; }
	public int material(){ return materialId; }
	public int smoothGroup(){ return smoothId; }
	
	public static boolean materialLess(TriangleOBJ a, TriangleOBJ b){
		return a.materialId < b.materialId;
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final TriangleOBJ t = (TriangleOBJ) o;
        return indices[0] == t.indices[0] && indices[1] == t.indices[1] && indices[2] == t.indices[2]
        		&& materialId == t.materialId && smoothId == t.smoothId;
	}

	@Override
	public int compareTo(TriangleOBJ another) {
		if(equals(another)) return 0;
		else if(materialLess(this, another)) return -1;
		else return 1; 
	}
	
	@Override
	public String toString(){
		return "TriangleOBJ{" + a() + "," + b() + "," + c() + ",mtl=" + material() + ",smooth=" + smoothId + "}";
	}
}
