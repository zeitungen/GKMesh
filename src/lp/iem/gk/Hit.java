package lp.iem.gk;

public class Hit
{
	   private Point pt;    //!< point d'intersection
	   private Normal norm;   //!< normale
	   private Normal tang1, tang2;      //!< repere tangent
	   private  float tmin;    //!< abscisse min le long du rayon
	   private float t;    //!< abscisse le long du rayon
	   private  float u, v; //!< coordonnees de textures / parametres de surface
	   private int object_id;      //!< identifiant de l'objet/triangle intersecte
	   private  int node_id;      //!< identifiant du noeud / pere du noeud intersecte
	   private int child_id;       //! identifiant du fils intersecte
	   private float user_data;    //!< donnee collectee pendant le suivi de rayon
	   
	   public Hit()
	   {
		   tmin = Geometry.RAY_EPSILON;
		   t = Geometry.HUGE_VAL;
		   object_id = -1; 
	       node_id = -1;
	       child_id = -1;
	       user_data = 0.f;
	   }
	   
	   public Hit( Ray ray)
	   {
		   tmin = Geometry.RAY_EPSILON;
		   t = ray.getTmax();
		   object_id = -1; 
	       node_id = -1;
	       child_id = -1;
	       user_data = 0.f;
	   }
}
