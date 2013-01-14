package lp.iem.mesh;

import lp.iem.gk.Point2;

public class Name {

	public static final boolean GK_NAME_STRING = true;

	public static final int GK_NAME_MAX = 40;
	public static final int GK_NAME_HASH_SEED = 1;

	private String name;
	private int hash;
	private int id;

	public Name() {
		this.hash = 0;
		this.id = -1;
		this.name = null;
	}

	public Name(int id) {
		this.hash = 0;
		this.id = id;
		this.name = null;
	}

	public Name(String str) {
		this.hash = 0;
		this.id = -1;
		if (GK_NAME_STRING) {
			if (str.length() > GK_NAME_MAX) this.name = str.substring(0, GK_NAME_MAX);
			else this.name = str;
			this.hash(this.name);
		} else {
			String name = str.substring(0, GK_NAME_MAX);
			this.hash(name);
			this.name = name;
		}
	}

	public Name(String str, int id){
		this.hash = 0;
		this.id = id;
		if (GK_NAME_STRING) {
			if (str.length() > GK_NAME_MAX) this.name = str.substring(0, GK_NAME_MAX);
			else this.name = str;
			this.hash(this.name);
		} else {
			String name = str.substring(0, GK_NAME_MAX);
			this.hash(name);
			this.name = name;
		}
	}

	public Name(Name n){
		if(GK_NAME_STRING) name = n.name;
		hash = n.hash;
		id = n.id;
	}
	
	/**
	 * calcul of hash value by murmurhash2 algorythm cf
	 * http://sites.google.com/site/murmurhash/
	 * 
	 * @param str
	 * @param length
	 * @param seed
	 * @return
	 */
	private static int murmur2(String str, int length, int seed) {
		// 'm' and 'r' are mixing constants generated offline.
		// They're not really 'magic', they just happen to work well.
		int m = 0x5bd1e995;
		int r = 24;

		// Initialize the hash to a 'random' value
		int h = seed ^ length;
		int len = length;

		// Mix 4 bytes at a time into the hash
		byte[] data = str.getBytes();
		int i = 0;
		while (len >= 4) {
			int k = data[i + 0] & 0xFF;
			k |= (data[i + 1] & 0xFF) << 8;
			k |= (data[i + 2] & 0xFF) << 16;
			k |= (data[i + 3] & 0xFF) << 24;

			k *= m;
			k ^= k >> r;
			k *= m;

			h *= m;
			h ^= k;

			i += 4;
			len -= 4;
		}

		// Handle the last few bytes of the input array
		switch (len) {
		case 3:
			h ^= data[2] << 16;
		case 2:
			h ^= data[1] << 8;
		case 1:
			h ^= data[0];
			h *= m;
		}
		;

		// Do a few final mixes of the hash to ensure the last few
		// bytes are well-incorporated.
		h ^= h >> 13;
		h *= m;
		h ^= h >> 15;

		return h;
	}

	private void hash(String str) {
		this.hash = Name.murmur2(str, str.length(), GK_NAME_HASH_SEED);
	}

	@Override
	public String toString() {
		return "name : " + name + " hash : " + hash + " id :" + id;
	}
	
	public int hash(){ return hash; }
	public int id(){ return id; }
	public String getName(){ return name; }
	
	public static int getHash(String str){
		return murmur2(str, str.length(), GK_NAME_HASH_SEED);
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final Name n = (Name) o;
        if(id < 0 || n.id < 0){
        	if(GK_NAME_STRING) return hash == n.hash && name.equals(n.name);
        	else return hash == n.hash;
        }else return id == n.id;
	}
	
	public int compareTo(Name n){
		if(equals(n)) return 0;
		if(id < 0 || n.id < 0){
			if(hash < n.hash) return -1;
			else return 1;
		}else{
			if(id < n.id) return -1;
			else return 1;
		}
	}
}
