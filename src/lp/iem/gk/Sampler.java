package lp.iem.gk;

import java.util.Random;

public class Sampler {

	private Random rand;

	public Sampler() {
		rand = new Random();
	}

	public float uniformFloat() {
		return rand.nextFloat();
	}

	// ! genere un entier entre 0 et max.
	public int uniformInt(int max) {
		return rand.nextInt(max);
	}

	// ! initialisation du germe du generateur aleatoire.
	public void init(int seed) {
		if (seed != 0)
			rand.setSeed(seed);
	}

}
