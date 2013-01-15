package lp.iem.mesh;

import java.util.ArrayList;

public class material_less
{
	private ArrayList<Integer> map = new ArrayList<Integer>();

	
	public material_less(ArrayList<Integer> _map)
	{
		map = _map;
	}
	
	public Boolean superieur(int a, int b)
	{
	     return (map.get(a) < map.get(b));	    
	}
	
	public ArrayList<Integer> getMap()
	{
		return map;
	}
	

}
