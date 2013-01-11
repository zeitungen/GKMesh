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
                r[i][j] = m1.m[i][0] * m2.m[0][j] +
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

	/**
	 * Invert a 4 x 4 matrix using Cramer's Rule
	 * @return
	 * @throws Exception
	 */
	public Matrix4x4 getInverse() throws Exception{
		// adaptation from the google's algorithm in android.opengl.matrix.invertM
        // transpose matrix
        final float src0  = m[0][0]; final float src4  = m[0][1]; final float src8  = m[0][2]; final float src12 = m[0][3];
        final float src1  = m[1][0]; final float src5  = m[1][1]; final float src9  = m[1][2]; final float src13 = m[1][3];
        final float src2  = m[2][0]; final float src6  = m[2][1]; final float src10 = m[2][2]; final float src14 = m[2][3];
        final float src3  = m[3][0]; final float src7  = m[3][1]; final float src11 = m[3][2]; final float src15 = m[3][3];

        // calculate pairs for first 8 elements (cofactors)
        final float atmp0  = src10 * src15; final float atmp1  = src11 * src14; final float atmp2  = src9  * src15; 
        final float atmp3  = src11 * src13; final float atmp4  = src9  * src14; final float atmp5  = src10 * src13;
        final float atmp6  = src8  * src15; final float atmp7  = src11 * src12; final float atmp8  = src8  * src14; 
        final float atmp9  = src10 * src12; final float atmp10 = src8  * src13; final float atmp11 = src9  * src12;

        // calculate first 8 elements (cofactors)
        final float dst0  = (atmp0 * src5 + atmp3 * src6 + atmp4  * src7) - (atmp1 * src5 + atmp2 * src6 + atmp5  * src7);
        final float dst1  = (atmp1 * src4 + atmp6 * src6 + atmp9  * src7) - (atmp0 * src4 + atmp7 * src6 + atmp8  * src7);
        final float dst2  = (atmp2 * src4 + atmp7 * src5 + atmp10 * src7) - (atmp3 * src4 + atmp6 * src5 + atmp11 * src7);
        final float dst3  = (atmp5 * src4 + atmp8 * src5 + atmp11 * src6) - (atmp4 * src4 + atmp9 * src5 + atmp10 * src6);
        final float dst4  = (atmp1 * src1 + atmp2 * src2 + atmp5  * src3) - (atmp0 * src1 + atmp3 * src2 + atmp4  * src3);
        final float dst5  = (atmp0 * src0 + atmp7 * src2 + atmp8  * src3) - (atmp1 * src0 + atmp6 * src2 + atmp9  * src3);
        final float dst6  = (atmp3 * src0 + atmp6 * src1 + atmp11 * src3) - (atmp2 * src0 + atmp7 * src1 + atmp10 * src3);
        final float dst7  = (atmp4 * src0 + atmp9 * src1 + atmp10 * src2) - (atmp5 * src0 + atmp8 * src1 + atmp11 * src2);

        // calculate pairs for second 8 elements (cofactors)
        final float btmp0  = src2 * src7; final float btmp1  = src3 * src6; final float btmp2  = src1 * src7;
        final float btmp3  = src3 * src5; final float btmp4  = src1 * src6; final float btmp5  = src2 * src5;
        final float btmp6  = src0 * src7; final float btmp7  = src3 * src4; final float btmp8  = src0 * src6;
        final float btmp9  = src2 * src4; final float btmp10 = src0 * src5; final float btmp11 = src1 * src4;

        // calculate second 8 elements (cofactors)
        final float dst8  = (btmp0  * src13 + btmp3  * src14 + btmp4  * src15) - (btmp1  * src13 + btmp2  * src14 + btmp5  * src15);
        final float dst9  = (btmp1  * src12 + btmp6  * src14 + btmp9  * src15) - (btmp0  * src12 + btmp7  * src14 + btmp8  * src15);
        final float dst10 = (btmp2  * src12 + btmp7  * src13 + btmp10 * src15) - (btmp3  * src12 + btmp6  * src13 + btmp11 * src15);
        final float dst11 = (btmp5  * src12 + btmp8  * src13 + btmp11 * src14) - (btmp4  * src12 + btmp9  * src13 + btmp10 * src14);
        final float dst12 = (btmp2  * src10 + btmp5  * src11 + btmp1  * src9 ) - (btmp4  * src11 + btmp0  * src9  + btmp3  * src10);
        final float dst13 = (btmp8  * src11 + btmp0  * src8  + btmp7  * src10) - (btmp6  * src10 + btmp9  * src11 + btmp1  * src8 );
        final float dst14 = (btmp6  * src9  + btmp11 * src11 + btmp3  * src8 ) - (btmp10 * src11 + btmp2  * src8  + btmp7  * src9 );
        final float dst15 = (btmp10 * src10 + btmp4  * src8  + btmp9  * src9 ) - (btmp8  * src9  + btmp11 * src10 + btmp5  * src8 );

        // calculate determinant
        final float det = src0 * dst0 + src1 * dst1 + src2 * dst2 + src3 * dst3;

        if (det == 0.0f) throw new Exception("Determinant = 0. Matrix no inversible.");

        // calculate matrix inverse
        final float invdet = 1.0f / det;
        float[][] mInv = new float[4][4];
        
        mInv[0][0] = dst0  * invdet; mInv[0][1] = dst1  * invdet; mInv[0][2] = dst2  * invdet; mInv[0][3] = dst3  * invdet;
        mInv[1][0] = dst4  * invdet; mInv[1][1] = dst5  * invdet; mInv[1][2] = dst6  * invdet; mInv[1][3] = dst7  * invdet;
        mInv[2][0] = dst8  * invdet; mInv[2][1] = dst9  * invdet; mInv[2][2] = dst10 * invdet; mInv[2][3] = dst11 * invdet;
        mInv[3][0] = dst12 * invdet; mInv[3][1] = dst13 * invdet; mInv[3][2] = dst14 * invdet; mInv[3][3] = dst15 * invdet;

        return new Matrix4x4(mInv);
	}
}
