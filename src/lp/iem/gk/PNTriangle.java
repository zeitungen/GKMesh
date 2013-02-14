package lp.iem.gk;

public class PNTriangle extends Triangle {
	private Normal na;
	private Normal nb;
	private Normal nc;

	// ! constructeur par defaut.
	public PNTriangle() {
		super();
	}

	// ! construit un triangle connaissant ses 3 sommets et leur normales.
	public PNTriangle(Point a, Normal _na, Point b, Normal _nb, Point c,
			Normal _nc) {
		super(a, b, c);
		na = _na;
		nb = _nb;
		nc = _nc;
	}

	public PNTriangle(Triangle abc, Normal _na, Normal _nb, Normal _nc) {
		super(abc.getA(), abc.getB(), abc.getC());
		na = _na;
		nb = _nb;
		nc = _nc;
	}

	// ! calcule la normale a l'interieur du triangle connaissant ses
	// coordonnees barycentriques.
	// ! convention n(u, v)= (1 - u - v) * na + u * nb + v * nc.
	public Normal getUVNormal(float u, float v) throws Exception {
		float w = 1.f - u - v;
		Normal v1 = na.productFloat(w);
		Normal v2 = nb.productFloat(u);
		Normal v3 = nc.productFloat(v);

		v1.add(v2);
		v1.add(v3);

		return Geometry.normalize(v1);
	}

	// TODO Transform :

	// ! renvoie un pntriangle transforme par 't'.
	public PNTriangle transform( /* Transform t */) {
		// return PNTriangle( t(a), t(na), t(b), t(nb), t(c), t(nc) );
		return null;
	}

	// ! renvoie le triangle geometrique support du PN triangle.

	public Triangle getBaseTriangle() {
		return new Triangle(super.a, super.b, super.c);
	}
}
