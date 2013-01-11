package lp.iem.gk;

public class Transform {

	private Matrix4x4 m;
	private Matrix4x4 minv;
	
	public Transform(){ this.identity(); }
	
	public Transform(float[][] m) throws Exception{
		this.m = new Matrix4x4(m);
		this.minv = this.m.getInverse();
	}
	
	public Transform(Matrix4x4 m) throws Exception{
		this.m = m;
		this.minv = this.m.getInverse();
	}
	
	public Transform(Matrix4x4 m, Matrix4x4 minv){
		//TODO check if minv is the inverse matrif of m
		this.m = m;
		this.minv = minv;
	}

	@Override
	public String toString(){ return this.m.toString(); }
	
	/**
	 * reinit the transform
	 */
	public void identity(){ 		
		this.m = new Matrix4x4();
		this.minv = new Matrix4x4();
	}
	
	public Matrix4x4 getGLTransposeMatrix(){ return this.m;	}
	public Matrix4x4 matrix(){ return this.m; }
	public Matrix4x4 transposeMatrix(){ return this.m.transpose(); }
	public Matrix4x4 inverseMatrix(){ return this.minv; }
	public Matrix4x4 normalMatrix(){ return this.minv.transpose(); }
	
	public Transform getInverse(){ return new Transform(this.minv, this.m); }
	
	public Point transform(Point p) throws Exception{ return Matrix4x4.transform(this.m, p); }
	public Vector transform(Vector v){ return Matrix4x4.transform(this.m, v); }
	public Normal transform(Normal n){ return Matrix4x4.transform(this.m, n); }
	public BBox transform(BBox b){ return Matrix4x4.transform(this.m, b); }
	public Ray transform(Ray r) throws Exception{ return new Ray(this.transform(r.getOrigin()), this.transform(r.getDirection())); }
	
	public Point inverse(Point p) throws Exception{ return Matrix4x4.transform(this.minv, p); }
	public Vector inverse(Vector v){ return Matrix4x4.transform(this.minv, v); }
	public Normal inverse(Normal n){ return Matrix4x4.transform(this.minv, n); }
	public BBox inverse(BBox b){ return Matrix4x4.transform(this.minv, b); }
	public Ray inverse(Ray r) throws Exception{ return new Ray(this.inverse(r.getOrigin()), this.inverse(r.getDirection())); }
	
	public static Transform translate(Vector delta){
		Matrix4x4 m = new Matrix4x4(1.f, 0.f, 0.f, delta.getX(),
									0.f, 1.f, 0.f, delta.getY(),
									0.f, 0.f, 1.f, delta.getZ(),
									0.f, 0.f, 0.f, 1.f);
		Matrix4x4 minv = new Matrix4x4(1.f, 0.f, 0.f, -delta.getX(),
									   0.f, 1.f, 0.f, -delta.getY(),
									   0.f, 0.f, 1.f, -delta.getZ(),
									   0.f, 0.f, 0.f, 1.f);
		return new Transform(m, minv);
	}
	
	public static Transform scale(float x, float y, float z){
		Matrix4x4 m = new Matrix4x4(x,   0.f, 0.f, 0.f,
									0.f, y,   0.f, 0.f,
									0.f, 0.f, z,   0.f,
									0.f, 0.f, 0.f, 1.f);
		Matrix4x4 minv = new Matrix4x4(1.f/x, 0.f,   0.f,   0.f,
									   0.f,   1.f/y, 0.f,   0.f,
									   0.f,   0.f,   1.f/z, 0.f,
									   0.f,   0.f,   0.f,   1.f);
		return new Transform(m, minv);
	}
	
	public static Transform scale(float v){ return Transform.scale(v,v,v); }
	
	public static Transform rotateX(float angle){
		float sint = (float) Math.sin(Geometry.radians(angle));
		float cost = (float) Math.cos(Geometry.radians(angle));
		Matrix4x4 m = new Matrix4x4(1.f, 0.f,  0.f,   0.f,
									0.f, cost, -sint, 0.f,
									0.f, sint, cost,  0.f,
									0.f, 0.f,  0.f,   1.f);
		return new Transform(m, m.transpose());
	}

	public static Transform rotateY(float angle){
		float sint = (float) Math.sin(Geometry.radians(angle));
		float cost = (float) Math.cos(Geometry.radians(angle));
		Matrix4x4 m = new Matrix4x4(cost,  0.f,  sint, 0.f,
									0.f,   1.f,  0.f,  0.f,
									-sint, 0.f,  cost, 0.f,
									0.f,   0.f,  0.f,  1.f);
		return new Transform(m, m.transpose());
	}

	public static Transform rotateZ(float angle){
		float sint = (float) Math.sin(Geometry.radians(angle));
		float cost = (float) Math.sin(Geometry.radians(angle));
		Matrix4x4 m = new Matrix4x4(cost, -sint, 0.f, 0.f,
									sint, cost,  0.f, 0.f,
									0.f,   0.f,  1.f, 0.f,
									0.f,   0.f,  0.f, 1.f);
		return new Transform(m, m.transpose());
	}
	
	public static Transform rotate(float angle, Vector v) throws Exception{
		Vector a = Geometry.normalize(v);
		float s = (float) Math.sin(Geometry.radians(angle));
		float c = (float) Math.cos(Geometry.radians(angle));
		float[][] m = new float[4][4];
		
		m[0][0] = a.getX() * a.getX() + (1.f - a.getX() * a.getX()) * c;
		m[0][1] = a.getX() * a.getY() * (1.f - c) - a.getZ() * s;
		m[0][2] = a.getX() * a.getZ() * (1.f - c) - a.getY() * s;
		m[0][3] = 0;
		
		m[1][0] = a.getX() * a.getY() * ( 1.f - c ) + a.getZ() * s;
	    m[1][1] = a.getY() * a.getY() + ( 1.f - a.getY() * a.getY() ) * c;
	    m[1][2] = a.getY() * a.getZ() * ( 1.f - c ) - a.getX() * s;
	    m[1][3] = 0;
	    
	    m[2][0] = a.getX() * a.getZ() * ( 1.f - c ) - a.getY() * s;
	    m[2][1] = a.getY() * a.getZ() * ( 1.f - c ) + a.getX() * s;
	    m[2][2] = a.getZ() * a.getZ() + ( 1.f - a.getZ() * a.getZ() ) * c;
	    m[2][3] = 0;
	    
	    m[3][0] = 0;
	    m[3][1] = 0;
	    m[3][2] = 0;
	    m[3][3] = 1;
	    
	    Matrix4x4 mat = new Matrix4x4(m);
	    return new Transform(mat, mat.transpose());
	}
	
	public static Transform lookAt(Point pos, Point look, Vector up) throws Exception{
		float[][] m = new float[4][4];
		Vector dir = Geometry.normalize(new Vector(pos, look));
		Vector right = Geometry.normalize(Geometry.cross(dir, Geometry.normalize(up)));
		Vector newUp = Geometry.normalize(Geometry.cross(right, dir));
		
	    m[0][0] = right.getX();	m[1][0] = right.getY(); m[2][0] = right.getZ();	m[3][0] = 0.f;
	    m[0][1] = newUp.getX();	m[1][1] = newUp.getY();	m[2][1] = newUp.getZ();	m[3][1] = 0.f;
	    m[0][2] = -dir.getX();   // opengl convention, look down the negative z axis
	    m[1][2] = -dir.getY();	m[2][2] = -dir.getZ();	m[3][2] = 0.f;
	    
	    // Initialize fourth column of viewing matrix
	    m[0][3] = pos.getX();	m[1][3] = pos.getY();	m[2][3] = pos.getZ();	m[3][3] = 1.f;

	    Matrix4x4 camToWorld = new Matrix4x4(m);
	    return new Transform(camToWorld.getInverse(), camToWorld);
	}

	public Transform product(Transform t){
		Matrix4x4 m1 = Matrix4x4.product(this.m, t.getGLTransposeMatrix());
		Matrix4x4 m2 = Matrix4x4.product(t.inverseMatrix(), this.minv);
		return new Transform(m1, m2);
	}
	
	public boolean swapsHandedness(){
		try {
			float det = ( m.get(0, 0) * ( m.get(1, 1) * m.get(2, 2) - m.get(1, 2) * m.get(2, 1) ) )
					- ( m.get(0, 1) * ( m.get(1, 0) * m.get(2, 2) - m.get(1, 2) * m.get(2, 0) ) )
					+ ( m.get(0, 2) * ( m.get(1, 0) * m.get(2, 1) - m.get(1, 1) * m.get(2, 0) ) );
			return (det < 0.f);
		} catch (Exception e) {	}
		return false;		
	}
	
	/**
	 * return the transformation associated with an orthographic camera (Projection)
	 * @param znear
	 * @param zfar
	 * @return
	 */
	public static Transform orthographic(float znear, float zfar){
		float zscale = 1.f / (zfar - znear);
		Transform scale = Transform.scale(1.f, 1.f, zscale);
		Transform transform = Transform.translate(new Vector(0.f, 0.f, -znear));
		return scale.product(transform);
	}
	
	/**
	 * return the transformation associated with an orthographic camera (Projection)
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param znear
	 * @param zfar
	 * @return
	 * @throws Exception
	 */
	public static Transform orthographic( float left, float right, float bottom, float top, float znear, float zfar ) throws Exception{
	    // opengl version
	    Matrix4x4 ortho = new Matrix4x4(2.f / (right - left), 	0.f                 , 	0.f                  , 	-(right + left) / (right - left),
	    								0.f,					2.f / (top - bottom), 	0.f                  , 	-(top + bottom) / (top - bottom),
										0.f, 					0.f                 , 	-2.f / (zfar - znear), 	-(zfar + znear) / (zfar - znear),
										0.f,					0.f, 					0.f, 					1.f);
	    return new Transform(ortho);
	}
	
	/**
	 * return the transformation associated with a perspective camera (Projection)
	 * @param fov
	 * @param aspect
	 * @param znear
	 * @param zfar
	 * @return
	 * @throws Exception
	 */
	public static Transform perspective(float fov, float aspect, float znear, float zfar) throws Exception{
		float invTan = (float) (1.f / Math.tan(Geometry.radians(fov) / 2));
		float invDenom = 1.f / (znear - zfar);
		Matrix4x4 persp = new Matrix4x4(invTan / aspect,	0,      0,                      	0,
		                     			0, 					invTan,	0,                      	0,
		                     			0,       			0, 		(zfar + znear) * invDenom,	2.f*zfar*znear*invDenom,
		                     			0,       			0,      -1,                      	0);
		return new Transform(persp);
	}

	public static Transform viewport(float width, float height) throws Exception{
	    float w = width / 2.f;
	    float h = height / 2.f;
	    
	    Matrix4x4 viewport = new Matrix4x4(w, 0.f, 0.f, w,    0.f, h, 0.f, h,    0.f, 0.f, .5f, .5f,     0.f, 0.f, 0.f, 1.f);
	    
	    return new Transform(viewport);
	}

	@Override
	public boolean equals(Object o){
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final Transform t = (Transform) o;
        return this.m.equals(t.m) && this.minv.equals(t.minv);
	}
}
