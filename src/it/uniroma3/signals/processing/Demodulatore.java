package it.uniroma3.signals.processing;

import it.uniroma3.signals.domain.ArraySignal;
import it.uniroma3.signals.domain.Complex;
import it.uniroma3.signals.domain.Signal;

public class Demodulatore extends SignalProcessor {

	public Demodulatore(Signal s) {
		super(s);
	}
	
	/**
	 * Operazione di demodulazione
	 * Permette di portare il segnale in banda base
	 * e di estrarne l'informazione
	 * 
	 * L'algoritmo implementa un Demodulatore Numerico
	 * 
	 * @return Signal demodulato
	 */

	@Override
    public Signal process() {
        Complex[] frequences = new Complex[this.getSignal().getLength()];

        for(int i = 0; i < this.getSignal().getLength(); i++) {
        	// Calcolo l'indice considerando lo shift dei dati
            int shifted = (i+1) % this.getSignal().getLength();
            // Calcolo del rapporto incrementale
            Complex tmp = this.getSignal().getSample(i).prodotto(this.getSignal().getSample(shifted).coniugato());
            // calcolo delle frequenze
            frequences[i] = new Complex(Math.atan(tmp.getImmaginaria() / tmp.getReale()), 0);
        }

        return new ArraySignal(frequences);
    }
}
