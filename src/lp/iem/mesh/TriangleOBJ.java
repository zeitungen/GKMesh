package lp.iem.mesh;

public class TriangleOBJ{
	
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
}
