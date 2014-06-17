package it.uniroma3.signals.processing;

import it.uniroma3.signals.domain.ArraySignal;
import it.uniroma3.signals.domain.Complex;
import it.uniroma3.signals.domain.Signal;

public class SelettoreCanale extends SignalProcessor {
	private double deltaF;

	@Override
	public Signal process() {
        Complex[] newValues = new Complex[this.getSignal().getLength()];

        Complex esponente;

        for(int i = 0; i < this.getSignal().getLength(); i++) {
            esponente = new Complex(0, 2 * Math.PI * deltaF * i);
            newValues[i] = this.getSignal().getSample(i).prodotto(esponente.exp());
        }

        return new ArraySignal(newValues);
    }

	public double getDeltaF() {
		return deltaF;
	}

	public void setDeltaF(double deltaF) {
		this.deltaF = deltaF;
	}

	public SelettoreCanale(Signal s) {
		super(s);
	}
}
