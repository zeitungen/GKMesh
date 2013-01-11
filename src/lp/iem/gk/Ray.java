package lp.iem.gk;

public class Ray extends BasicRay
{
	private Vector inv_d;       			// 1 / direction
	private char sign_d[] = new char[4];    //!< vrai (==1) si direction[i] < 0.
	
	public Ray(){ super(); }
	
	//! constructeur (origine, direction). direction est un vecteur unitaire.
	public Ray( Point origin,  Vector direction) throws Exception
	{
		super(origin, direction, -1);
		inv_d = new Vector(1.f / getDirection().getX(), 1.f / getDirection().getY(), 1.f / getDirection().getZ());
		sign_d[0] = (char) ((inv_d.get(0) < 0.f) ?  1 : 0);
		sign_d[1] = (char) ((inv_d.get(1) < 0.f) ?  1 : 0);
		sign_d[2] = (char) ((inv_d.get(2) < 0.f) ?  1 : 0);
		sign_d[3] = 0;
	}
	
	//! constructeur (origine, direction). direction est un vecteur unitaire.
	public Ray( Point origin,  Vector direction, int id) throws Exception
	{
		super(origin, direction, id);
		inv_d = new Vector(1.f / getDirection().getX(), 1.f / getDirection().getY(), 1.f / getDirection().getZ());
		sign_d[0] = (char) ((inv_d.get(0) < 0.f) ?  1 : 0);
		sign_d[1] = (char) ((inv_d.get(1) < 0.f) ?  1 : 0);
		sign_d[2] = (char) ((inv_d.get(2) < 0.f) ?  1 : 0);
		sign_d[3] = 0;
	}
	
	//! renvoie vrai si la direction du rayon est < 0 pour l'axe 'axis'.
	public Boolean isBackward(int axis ) 
    {
		if (sign_d[axis] == 1)
			return true;
		else
			return false;
    }

	public Vector getInv_d()
	{
		return inv_d;
	}

	public void setInv_d(Vector inv_d)
	{
		this.inv_d = inv_d;
	}

	public char[] getSign_d()
	{
		return sign_d;
	}

	public void setSign_d(char[] sign_d)
	{
		this.sign_d = sign_d;
	}

}
