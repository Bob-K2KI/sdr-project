package it.uniroma3.signals.processing;

public abstract class Utils {
	
	/**
	 * Metodo per il calcolo del massimo comun divisore di due numeri interi
	 * utilizzando l'algoritmo binario che risulta essere il 60% più veloce
	 * 
	 * Fonti:
	 * http://cs.stackexchange.com/questions/1447/what-is-most-efficient-for-gcd
	 * http://en.wikipedia.org/wiki/Binary_GCD_algorithm
	 * 
	 * @param int x1
	 * @param int x2
	 * @return int gcd
	 */
	public static int gcd(int x1, int x2) {
		if (x1 < 0 || x2 < 0)
			throw new IllegalArgumentException(
					"I parametri non possono essere essere negativi");

		int shift;

		/* GCD(0,v) == v; GCD(u,0) == u, GCD(0,0) == 0 */
		if (x1 == 0)
			return x2;
		if (x2 == 0)
			return x1;

		for (shift = 0; ((x1 | x2) & 1) == 0; ++shift) {
			x1 >>= 1;
			x2 >>= 1;
		}

		while ((x1 & 1) == 0)
			x1 >>= 1;

		do {
			while ((x2 & 1) == 0)
				x2 >>= 1;
			if (x1 > x2) {
				int t = x2;
				x2 = x1;
				x1 = t;
			}
			x2 = x2 - x1;
		} while (x2 != 0);

		return x1 << shift;
	}

}
