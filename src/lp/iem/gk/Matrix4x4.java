package lp.iem.gk;

public class Matrix4x4 {

	private float[][] m;
	
	/**
	 * construct an identity matrix by default
	 */
	public Matrix4x4(){
		this.m = new float[4][4];
		
		for(int i=0; i < 4; ++i)
			for(int j=0; j < 4; ++j)
				this.m[i][j] = 0.f;
		
		for(int k=0; k < 4; k++)
			this.m[k][k] = 1.f;
	}
	
	public Matrix4x4(float[][] m) throws Exception{
		if(m.length != 4) throw new Exception("Matrix is not 4x4");
		else if(m[0].length != 4 || m[1].length != 4 || m[2].length != 4 || m[3].length != 4)
			throw new Exception("Matrix is not 4x4");
		this.m = new float[4][4];
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				this.m[i][j] = m[i][j];
	}

	public Matrix4x4(float t00, float t01, float t02, float t03,
		    		 float t10, float t11, float t12, float t13,
		    		 float t20, float t21, float t22, float t23,
		    		 float t30, float t31, float t32, float t33){
		this.m = new float[4][4];
	    m[0][0] = t00; m[0][1] = t01; m[0][2] = t02; m[0][3] = t03;
	    m[1][0] = t10; m[1][1] = t11; m[1][2] = t12; m[1][3] = t13;
	    m[2][0] = t20; m[2][1] = t21; m[2][2] = t22; m[2][3] = t23;
	    m[3][0] = t30; m[3][1] = t31; m[3][2] = t32; m[3][3] = t33;
	}

	public float[][] getMatrix(){ return this.m; }
	
	public float get(int x, int y) throws Exception{
		if(x < 0 || x > 3 || y < 0 || y > 3) throw new Exception("{x,y}={" + x + "," + y + "} not in the matrix");
		return this.m[x][y];
	}
	
	/**
	 * 
	 * @return the transposed matrix
	 */
	public Matrix4x4 transpose(){
		return new Matrix4x4(m[0][0], m[1][0], m[2][0], m[3][0],
		        			 m[0][1], m[1][1], m[2][1], m[3][1],
		        			 m[0][2], m[1][2], m[2][2], m[3][2],
		        			 m[0][3], m[1][3], m[2][3], m[3][3]);
	}

	@Override
	public String toString(){
		String str = "Matrix4x4{[";
		for(int i=0; i < 4; i++){
			str += "[";
			for(int j=0; j < 4; j++){
				str += "" + this.m[i][j];
				if(j != 3) str += ",";
			}
			str += "]";
			if(i != 3) str += ",";
		}
		str += "]}";
		return str;
	}

	public float[] getColumn(int index) throws Exception{
		if(index < 0 || index > 3) throw new Exception("indew < 0 or index > 3");
		float[] f = new float[4];
		for(int i=0; i < 4; i++)
			f[i] = this.m[i][index];
		return f;
	}
	
	public float[] getRow(int index) throws Exception{
		if(index < 0 || index > 3) throw new Exception("indew < 0 or index > 3");
		float[] f = new float[4];
		for(int i=0; i < 4; i++)
			f[i] = this.m[index][i];
		return f;
	}

	public Vector getRotationVector(int index) throws Exception{
		if(index < 0 || index > 2) throw new Exception("index < 0 or index > 2");
		return new Vector(this.m[index][0], this.m[index][1], this.m[index][2]);
	}

	public Vector getTranslationVector(){ return new Vector(m[0][3], m[1][3], m[2][3]); }

	/**
	 * product of two matrix, m = m1 * m2
	 * @param m1
	 * @param m2
	 * @return m
	 */
	public static Matrix4x4 product(Matrix4x4 m1, Matrix4x4 m2){
        float[][] r = new float[4][4];
        
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                r[i][j] = 
                    m1.m[i][0] * m2.m[0][j] +
                    m1.m[i][1] * m2.m[1][j] +
                    m1.m[i][2] * m2.m[2][j] +
                    m1.m[i][3] * m2.m[3][j];
        
        Matrix4x4 m = null;
        try { m = new Matrix4x4(r); } catch (Exception e) {	}
        return m;
	}

	/**
	 * transform a vector by a matrix
	 * @param m
	 * @param v
	 * @return
	 */
	public static Vector transform(Matrix4x4 m, Vector v){
        float x = v.getX(), y = v.getY(), z = v.getZ();
        return new Vector(m.m[0][0]*x + m.m[0][1]*y + m.m[0][2]*z,
        				  m.m[1][0]*x + m.m[1][1]*y + m.m[1][2]*z,
        				  m.m[2][0]*x + m.m[2][1]*y + m.m[2][2]*z );  
	}

	/**
	 * transform a point by a matrix
	 * @param m
	 * @param p
	 * @return
	 * @throws Exception 
	 */
	public static Point transform(Matrix4x4 m, Point p) throws Exception{
        float x = p.getX(), y = p.getY(), z = p.getZ();
        
        float xt = m.m[0][0] * x + m.m[0][1] * y + m.m[0][2] * z + m.m[0][3];
        float yt = m.m[1][0] * x + m.m[1][1] * y + m.m[1][2] * z + m.m[1][3];
        float zt = m.m[2][0] * x + m.m[2][1] * y + m.m[2][2] * z + m.m[2][3];
        float wt = m.m[3][0] * x + m.m[3][1] * y + m.m[3][2] * z + m.m[3][3];
        
        if(wt == 0.f) throw new Exception("wt = 0");
        else if( wt == 1.f ) return new Point( xt, yt, zt );
        else{
        	Point po = new Point(xt, yt, zt);
        	po.dividByFloat2(wt);
        	return po;
        }
	}

	/**
	 * transform a normal by a matrix
	 * @param m
	 * @param n
	 * @return
	 */
	public static Normal transform(Matrix4x4 m, Normal n){
        float x = n.getX(), y = n.getY(), z = n.getZ();
        
        // use the inverse transform ... not a direct transformation.
        float tx = m.m[0][0] * x + m.m[1][0] * y + m.m[2][0] * z;
        float ty = m.m[0][1] * x + m.m[1][1] * y + m.m[2][1] * z;
        float tz = m.m[0][2] * x + m.m[1][2] * y + m.m[2][2] * z;
        
        return new Normal(tx, ty, tz);
	}
	
	/**
	 * transform a aabox by a matrix
	 * @param m
	 * @param b
	 * @return
	 */
	public static BBox transform(Matrix4x4 m, BBox b){
		try {
			BBox a = new BBox(Matrix4x4.transform(m, new Point(b.getMin().getX(), b.getMin().getY(), b.getMin().getZ())));
			a.union(Matrix4x4.transform(m, new Point(b.getMax().getX(), b.getMin().getY(), b.getMin().getZ())));
			a.union(Matrix4x4.transform(m, new Point(b.getMin().getX(), b.getMax().getY(), b.getMin().getZ())));
			a.union(Matrix4x4.transform(m, new Point(b.getMin().getX(), b.getMax().getY(), b.getMax().getZ())));
			a.union(Matrix4x4.transform(m, new Point(b.getMin().getX(), b.getMin().getY(), b.getMin().getZ())));
			a.union(Matrix4x4.transform(m, new Point(b.getMax().getX(), b.getMax().getY(), b.getMin().getZ())));
			a.union(Matrix4x4.transform(m, new Point(b.getMax().getX(), b.getMin().getY(), b.getMax().getZ())));
			a.union(Matrix4x4.transform(m, new Point(b.getMax().getX(), b.getMax().getY(), b.getMax().getZ())));
			return a;
		} catch (Exception e) { }
		return null;
	}

	public Matrix4x4 getInverse() throws Exception{
		int[] indxc = new int[4];
		int[] indxr = new int[4];
		int[] ipiv = {0, 0, 0, 0};
		float[][] minv = new float[4][4];
		for(int i=0; i < 4; i++)
			for(int j=0; j < 4; j++)
				minv[i][j] = this.m[i][j];
		
		for(int i=0; i < 4; i++){
			int irow = -1, icol = -1;
			float big = 0.f;
			
			//Chosse pivot
			for(int j=0; j < 4; j++){
				if(ipiv[j] != 1){
					for(int k=0; k < 4; k++){
						if(ipiv[k] == 0){
							if(Math.abs(minv[j][k]) >= big){
								big = (float)Math.abs(minv[j][k]);
								irow = j;
								icol = k;
							}
						}else if(ipiv[k] > 1) throw new Exception("Singular matrix in MatrixInvert");
						//}else if(ipiv[k] > .9f) throw new Exception("Singular matrix in MatrixInvert");
					}
				}
			}
			
			++ipiv[icol];
			// Swap rows _irow_ and _icol_ for pivot
			if(irow != icol){
				for(int k=0; k < 4; ++k){
					float tmp = minv[irow][k];
					minv[irow][k] = minv[icol][k];
					minv[icol][k] = tmp;
				}				
			}
			
			indxr[i] = irow;
			indxc[i] = icol;
			//if(minv[icol][icol] == 0.f)  throw new Exception("Singular matrix in MatrixInvert");
			if(minv[icol][icol] < .1f || minv[icol][icol] > -.1f)  throw new Exception("Singular matrix in MatrixInvert");
			
			// Set $m[icol][icol]$ to one by scaling row _icol_ appropriately
			float pivinv = 1.f / minv[icol][icol];
			minv[icol][icol] = 1.f;
			for(int j=0; j < 4; j++) minv[icol][j] *= pivinv;
			
			// Subtract this row from others to zero out their columns
			for(int j=0; j < 4; j++){
				if(j != icol){
					float buf = minv[j][icol];
					minv[j][icol] = 0;
					for(int k=0; k < 4; k++) minv[j][k] -= minv[icol][k] * buf;
				}
			}
		}
		
		// Swap columns to reflect permutation
	    for (int j = 3; j >= 0; j--) {
	        if (indxr[j] != indxc[j]) {
	            for (int k = 0; k < 4; k++){
	                float tmp = minv[k][indxr[j]];
	                minv[k][indxr[j]] = minv[k][indxc[j]];
	                minv[k][indxc[j]] = tmp;
	            }
	        }
	    }
	    
	    return new Matrix4x4(minv);
	}
}
