package lp.iem.gk;

public class Hit {
	private Point pt; 				// intersect point
	private Normal norm; 			// normal
	private Normal tang1, tang2; 	// tangent benchmark
	private float tmin; 			// min abscissa along the radius
	private float t; 				// abscissa along the radius
	private float u, v; 			// coordinates of texture / surface parameters
	private int objectId; 			// object identifier / intersect triangle 
	private int nodeId; 			// identifier of the node / intersect father node
	private int childId; 			// intersect son identifier 
	private float userData; 		// collected data during ray tracing

	public Hit() {
		tmin = Geometry.RAY_EPSILON;
		t = Geometry.HUGE_VAL;
		objectId = -1;
		nodeId = -1;
		childId = -1;
		userData = 0.f;
	}

	public Hit(Ray ray) {
		tmin = Geometry.RAY_EPSILON;
		t = ray.getTmax();
		objectId = -1;
		nodeId = -1;
		childId = -1;
		userData = 0.f;
	}
	
	public Point getPoint() { return pt; }
	public Normal getNormal() { return norm; }
	public Normal getTangent1() { return tang1; }
	public Normal getTangent2() { return tang2; }
	public float getTmin() { return tmin; }
	public float getT() { return t; }
	public float getU() { return u; }
	public float getV() { return v; }
	public int getObjectId() { return objectId; }
	public int getNodeId() { return nodeId; }
	public int getChildId() { return childId; }
	public float getUserData() { return userData; }
}
