package lp.iem.mesh;

public class Vertex
{
	private int[] indices = new int[4];
	
	public Vertex() 
	{
		
	}
    
    public Vertex( int m, int p, int n, int t )
    {
    	indices[0] = m;
    	indices[1] = p;
    	indices[2] = n;
    	indices[3] = t;
    }

    //! comparaison de 2 sommets pour l'insertion dans une std::map.
    public static Boolean less(Vertex a, Vertex b )
    {
        // definit un ordre lexicographique sur la matiere + les 3 indices : position, normale, texcoord
        for ( int i = 0; i < 4; i++ )
        {
            if ( a.indices[i] < b.indices[i] )
                // renvoie vrai lorsque a < b
                return true;
            else if ( a.indices[i] > b.indices[i] )
                return false;
        }
        
        return false;
    }
    
    //! renvoie l'indice de la matiere du sommet.
    int material( ) 
    {
        return indices[0];
    }
    
    //! renvoie l'indice de la position du sommet.
    int position( ) 
    {
        return indices[1];
    }
    
    //! renvoie l'indice de la normale du sommet.
    int normal( ) 
    {
        return indices[2];
    }
    
    //! renvoie l'indice de la coordonnee de texture du sommet.
    int texcoord( ) 
    {
        return indices[3];
    }

    static public Boolean superieur(Vertex a, Vertex b )
    {
        return less( a, b );
    }
}
