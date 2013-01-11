package lp.iem.gk;

import java.math.BigDecimal;

public class Geometry {

	public final static float HUGE_VAL = 999999;
	
	public final static float RAY_EPSILON = 0.0001f;
	public final static float EPSILON = 0.00001f;
	
	public final static float ROUND_PRECISION = 0.0001f;
			
	/**
	 * cross product of two vectors
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector cross(Vector v1, Vector v2) {
		return new Vector((v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY()),
				(v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ()),
				(v1.getX() * v2.getY()) - (v1.getY() * v2.getX()));
	}

	/**
	 * cross product of a vector and a normal
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector cross(Vector v1, Normal v2) {
		return new Vector((v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY()),
						  (v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ()),
						  (v1.getX() * v2.getY()) - (v1.getY() * v2.getX()));
	}

	/**
	 * cross product of a normal and a vector
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vector cross(Normal v1, Vector v2) {
		return new Vector((v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY()),
						  (v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ()),
						  (v1.getX() * v2.getY()) - (v1.getY() * v2.getX()));
	}

	/**
	 * cross product of two vectors
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float dot(Vector v1, Vector v2) { return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ(); }

	/**
	 * absolute value of a cross product of 2 vectors
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float absDot(Vector v1, Vector v2) { return Math.abs(dot(v1, v2)); }

	// ! max(0, dot) du produit scalaire de 2 vecteurs.
	public static float zeroDot(Vector v1, Vector v2) { return Math.max(0.f, dot(v1, v2)); }

	// ! renvoie un vecteur de longueur 1 de meme direction que v.
	public static Vector normalize(Vector v) throws Exception { return v.division(v.length()); }

	// ! construit un repere orthogonal dont la normale est aligne sur un
	// vecteur v1, v2 et v3 sont les 2 tangentes, retourn vecteur.
	public static Vector[] coordinateSystem(Vector v1, Vector v2, Vector v3) {
		if (Math.abs(v1.getX()) > Math.abs(v1.getY())) {
			float invLen = (float) (1.f / Math.sqrt(v1.getX() * v1.getX()
					+ v1.getZ() * v1.getZ()));
			v2 = new Vector(-v1.getZ() * invLen, 0.f, v1.getX() * invLen);
		} else {
			float invLen = (float) (1.f / Math.sqrt(v1.getY() * v1.getY()
					+ v1.getZ() * v1.getZ()));
			v2 = new Vector(0.f, v1.getZ() * invLen, -v1.getY() * invLen);
		}

		v3 = cross(v1, v2);
		Vector vect[] = { v1, v2, v3 };

		return vect;
	}

	// ! renvoie la distance entre 2 points.
	public static float distance(Point p1, Point p2) { return (p1.substractionPoint(p2)).length(); }

	// renvoie la distanceSquared entre 2 points
	public static float distanceSquared(Point p1, Point p2) { return (p1.substractionPoint(p2)).lengthSquared(); }

	// ! scalaire * point.
	public static Point productPointFloat(float f, Point p) { return p.productFloat(f); }

	// ! scalaire * normale.
	public static Normal productNormFloat(float f, Normal n) { return n.productFloat(f); }

	// ! renvoie une normale de meme direction, mais de longeur 1.
	public static Normal normalize(Normal v) throws Exception { return v.dividByFloat(v.length()); }

	public static Vector vector(Normal n) { return new Vector(n.getX(), n.getY(), n.getZ()); }

	// ! produit scalaire d'une normale et d'un vecteur.
	public static float dot(Normal n1, Vector v2) { return n1.getX() * v2.getX() + n1.getY() * v2.getY() + n1.getZ() * v2.getZ(); }

	// ! produit scalaire de 2 normales.
	public static float dot(Normal n1, Normal n2) { return n1.getX() * n2.getX() + n1.getY() * n2.getY() + n1.getZ() * n2.getZ(); }

	// valeur absolue produit scalaire normal * vector
	public static float absDot(Normal n1, Vector v2) { return Math.abs(dot(n1, v2)); }

	// valeur absolue produit scalaire de 2 normales
	public static float absDot(Normal n1, Normal n2) { return Math.abs(dot(n1, n2)); }

	// ! max(0, dot) du produit scalaire de 2 vecteurs.
	public static float zeroDot(Vector v1, Normal v2) { return Math.max(0.f, dot(v2, v1)); }

	// ! max(0, dot) du produit scalaire de 2 vecteurs.
	public static float zeroDot(Normal v1, Normal v2) { return Math.max(0.f, dot(v1, v2)); }

	// ! interpolation lineaire entre 2 reels, x= (1 - t) v1 + t v2
	public static float lerp(float t, float v1, float v2) { return (1.f - t) * v1 + t * v2; }

	// ! limite une valeur entre un min et un max.
	public static float clamp(float value, float low, float high) {
		if (value < low) return low;
		else if (value > high) return high;
		else return value;
	}

	// ! limite une valeur entre un min et un max.
	public static int clamp(int value, int low, int high) {
		if (value < low) return low;
		else if (value > high) return high;
		else return value;
	}

	// ! conversion degres vers radians.
	public static float radians(float deg) { return ((float) Math.PI / 180.f) * deg; }

	// ! conversion radians vers degres.
	public static float degrees(float rad) { return (180.f / (float) Math.PI) * rad; }

	// ! renvoie un vecteur dont la direction est representee en coordonness
	// polaires.
	public static Vector sphericalDirection(float sintheta, float costheta, float phi){ return new Vector(sintheta * (float) Math.cos(phi), sintheta * (float) Math.sin(phi), costheta); }

	// ! renvoie les composantes d'un vecteur dont la direction est representee
	// en coordonnees polaires, dans la base x, y, z.
	public static Vector sphericalDirection(float sintheta, float costheta, float phi, Vector x, Vector y, Vector z) {
		Vector v1 = x.productVector((float) Math.cos(phi) * sintheta);
		Vector v2 = y.productVector(sintheta * (float) Math.sin(phi));
		Vector v3 = z.productVector(costheta);

		v1.add(v2);
		v1.add(v3);

		return v1;
	}

	// ! renvoie l'angle theta d'un vecteur avec la normale, l'axe Z,
	// (utilisation dans un repere local).
	public static float sphericalTheta(Vector v) { return (float) Math.acos(clamp(v.getZ(), -1.f, 1.f)); }

	// ! renvoie l'angle phi d'un vecteur avec un vecteur tangent, l'axe X,
	// (utilisation dans un repere local).
	public static float sphericalPhi(Vector v) {
		float p = (float) Math.atan2(v.getY(), v.getX());
		return (float) ((p < 0.f) ? p + 2.f * Math.PI : p);
	}

	public static float round(float val, float precision){
		BigDecimal bval = new BigDecimal(val);
		BigDecimal bp = new BigDecimal(1/precision);
		BigDecimal bip = new BigDecimal((int)val);
		BigDecimal bdp = bval.subtract(bip);
		
		BigDecimal bnval = bdp.multiply(bp);
		BigDecimal bnip = new BigDecimal((int)bnval.floatValue());
		BigDecimal bndp = bnval.subtract(bnip);
		
		BigDecimal one = new BigDecimal(1);
		BigDecimal compare = new BigDecimal(.5f);
		BigDecimal diff = one.subtract(bndp);
		if(diff.compareTo(compare) == -1) bnip = bnip.add(one);
		
		BigDecimal res = bip.add(bnip.divide(bp));
		return res.floatValue();
	}
	
	public static float round(float val){ return round(val, ROUND_PRECISION); }
}
