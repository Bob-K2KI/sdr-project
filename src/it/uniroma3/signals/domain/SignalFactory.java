package it.uniroma3.signals.domain;

import java.util.Random;

public final class SignalFactory {
	
	// Prevengo la possibilità di istanziare la factory
	private SignalFactory() {}

	/**
	 * Restituisce l'n-esimo campione di una sinc di banda band
	 * 
	 * @param n
	 * @param band
	 * @return valore campione
	 */
	public static double sinc(double n, double band){
		if(n == 0D)
			return 1;
		
		double range = 1D/(2D * band);
		if (n % range == 0D)
			return 0D;
		
		double sincArgument = Math.PI * 2D * band * n;
		return Math.sin(sincArgument) / sincArgument;
	}
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni dati in input
	 * e lo interpola con un interpolatore
	 * 
	 * @param double band
	 * @param int numCampioni
	 * @param DiscreteInterpolator<Double> interpolator
	 * @return Segnale discreto
	 */
	public static Signal lowPassFilter(double band, int numCampioni, DiscreteInterpolator<Double> interpolator) {
		Complex[] values = new Complex[numCampioni];
		int simmetria = (numCampioni) / 2;
		
		for(int n = 0; n <= simmetria; n++){
			double realval = 2 * band * SignalFactory.sinc(n, band);
			realval = interpolator.calc(realval, n);
			values[n + simmetria] = new Complex(realval);
			values[-n + simmetria] = new Complex(realval);
		}
		
		return new ArraySignal(values);
	}
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni dati in input
	 * 
	 * @param band
	 * @param numCampioni
	 * @return Segnale discreto
	 */
	
	public static Signal lowPassFilter(double band, int numCampioni) {
		return SignalFactory.lowPassFilter(band, numCampioni,
				// Il terzo parametro è una classe anonima che implementa
				// l'interfaccia DiscreteInterpolator che definisce il calcolo.
				// In questo caso non esegue calcoli.
				new DiscreteInterpolator<Double>() {
					public Double calc(Double oldValue, int index) {
						return oldValue;
					}
				});
	}

	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni calcolati
	 * 
	 * @param band
	 * @return Segnale discreto
	 */
	
	public static Signal lowPassFilter(double band) {
		double ampiezzaConsiderata = 5D / (2D * band);
		int numCampioni = ((int)ampiezzaConsiderata)*2 + 1;
		
		return SignalFactory.lowPassFilter(band, numCampioni);
	}
	
	/**
	 * Crea un nuovo segnale rappresentante il filtro passa-basso
	 * con numero di campioni calcolati e interpolato secondo un DiscreteInterpolator
	 * 
	 * @param band
	 * @param DiscreteInterpolator<Double> interpolator
	 * @return Segnale discreto
	 */
	
	public static Signal lowPassFilter(double band, DiscreteInterpolator<Double> interpolator) {
		double ampiezzaConsiderata = 5D / (2D * band);
		int numCampioni = ((int)ampiezzaConsiderata)*2 + 1;
		
		return SignalFactory.lowPassFilter(band, numCampioni, interpolator);
	}

	/**
	 * Crea un nuovo segnale rappresentante il filtro passa banda
	 * centrato ad una frequenza portante
	 * 
	 * @param Double band
	 * @param Double portante
	 * @return Segnale discreto
	 */
	public static Signal bandPassFilter (double band, final double portante) {
		double ampiezzaConsiderata = 5D / (2D * band);
		int numCampioni = ((int)ampiezzaConsiderata)*2 +1;
		
		return SignalFactory.lowPassFilter(band, numCampioni,
				// Il terzo parametro è una classe anonima che implementa
				// l'interfaccia DiscreteInterpolator che definisce il calcolo.
				//
				// In questo caso trasla il valore del filtro passa basso
				// alla banda portante.
				new DiscreteInterpolator<Double>() {
					public Double calc(Double oldValue, int index) {
						return oldValue * 2 * Math.cos(2 * Math.PI * portante * index);
					}
				});
	}
	
	
	public static Signal RandomQPSK(int length) {
		Complex[] values = new Complex[length];
		double divisore = Math.sqrt(length);
		
		for (int i = 0; i < length; i++)
			values[i] = new Complex(
				(Math.random()-1) / divisore,
				(Math.random()-1) / divisore
			);
		
		return new ArraySignal(values);
	}
	
	public static Signal Noise(double snr, int length, double potenza) {
		double varianza = potenza / Math.pow(10, (snr / 10));
		double moltiplicatore = Math.sqrt(varianza / 2);
		
		Complex[] values = new Complex[length];
		
		for (int i = 0; i < length; i++)
			values[i] = new Complex(
				(new Random()).nextGaussian() * moltiplicatore,
				(new Random()).nextGaussian() * moltiplicatore
			);
		
		return new ArraySignal(values);
	}
}
