package it.uniroma3.signals.domain;

import it.uniroma3.signals.processing.SignalWriter;

public abstract class Signal {
	private int SNR;
	
	public abstract Complex getSample(int i);
	
	public abstract int getLength();

	public int getSNR() {
		return this.SNR;
	}

	public void setSNR(int SNR) {
		this.SNR = SNR;
	}
	
	/**
	 * Operazione di convoluzione fra segnali:
	 * implementa un'operazione di convoluzione discreta fra il segnale contesto
	 * e uno passato come parametro.
	 * Presuppone che il segnale d'ingresso abbia parte reale e immaginaria non nulle 
	 * e che il filtro abbia solo parte reale.
	 * @param segnale2
	 * @return
	 */
	public Signal convoluzione(Signal s2) {
		int lunghezzaFinale = this.getLength() + s2.getLength() - 1;
		int upperBound, lowerBound;
		
		Complex[] result = new Complex[lunghezzaFinale];
		
		for(int k = 0; k < lunghezzaFinale; k++){
			upperBound = Math.min(k, s2.getLength() -1);
			lowerBound = Math.max(0, k - this.getLength() + 1);
			result[k] = new Complex(0, 0);
			for(int j = lowerBound; j <= upperBound; j++)
				result[k] = result[k].somma(
						this.getSample(k-j)
							.prodotto(s2.getSample(j)) );
		}
		
		return new ArraySignal(result);
	}

	
	/**
	 * Operazione di espansione di un segnale
	 * Dato un fattore di espansione viene creato un nuovo segnale,
	 * a partire da quello contesto, espanso con valori nulli.
	 *
	 * @param Signal segnaleIn
	 * @param int fattore
	 * @return Signal espanso
	 */

	public Signal espandi(int fattore) {
		// TODO: verificare side effect
		// sto tornando lo stesso oggetto e non un oggetto uguale ma diverso
		// mi va bene?
		if(fattore == 1)
			return this;

		Complex[] newValues = new Complex[ this.getLength() * fattore ];
		
		// indice di iterazione su values
		int j = 0;

		for(int i = 0; i < newValues.length; i++)
			newValues[i] = i % fattore == 0 ? this.getSample(j++) : new Complex();

		return new ArraySignal(newValues);
	}
	
	/**
	 * Filtro interpolatore in base al parametro F1
	 * @param segnaleIn
	 * @param F1
	 * @return segnale interpolato
	 */
	public Signal interpola(final int F1){
		if(F1 == 1)
			return this;
		
		double band = 1D/ (2D * F1);
		
		Signal lpf = SignalFactory.lowPassFilter(band, new DiscreteInterpolator<Double>() {
			public Double calc(Double oldValue, int index) {
				return oldValue * F1;
			}
		});
				
		
		int n = (lpf.getLength() -1)/2, j = 0;
		Complex[] val = new Complex[this.getLength()];
		
		Signal si = this.convoluzione(lpf);
		
		for(int i = n; i < si.getLength() - n; i++)
			val[j++] = si.getSample(i);
		
		
		return new ArraySignal(val);
	}
	
	
	/**
	 * Operazione di decimazione di un segnale
	 * Dato un fattore di decimazione viene creato un nuovo segnale
	 * decimato.
	 *
	 * @param Signal segnaleIn
	 * @param int fattore
	 * @return Signal decimato
	 */
	public Signal decima(int fattore) {

		// TODO: controllare side effect
		if(fattore == 1)
			return this;

		// array nuovi valori
		Complex[] newValues = new Complex[ this.getLength() / fattore ];
		// indice per iterare sui nuovi valori
		int j = 0;

		/*
			Non ho bisogno di iterare su tutti i valori,
			devo prendere solo quelli tali che (i % fattore == 0).
			Ciò significa, partendo da i = 0, i+= fattore.
		 */
		for(int i = 0; i < this.getLength(); i += fattore)
			newValues[j++] = this.getSample(i); 

		return new ArraySignal(newValues);
	}
	
	public boolean scriviSuFile(String filename) {
		SignalWriter sw = new SignalWriter(this);
		sw.setFilename(filename);
		
		return sw.process();
	}

}
