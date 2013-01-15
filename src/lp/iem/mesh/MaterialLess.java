package lp.iem.mesh;

import java.util.ArrayList;
import java.util.List;

public class MaterialLess {
	
	private List<Integer> map = new ArrayList<Integer>();

	public MaterialLess(List<Integer> _map) {
		map = _map;
	}

	public Boolean superieur(int a, int b) {
		return (map.get(a) < map.get(b));
	}

	public List<Integer> getMap() {
		return map;
	}

}
