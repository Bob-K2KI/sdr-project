package it.uniroma3.signals.processing;

import it.uniroma3.signals.domain.Signal;

public class CambioTassoCampionamento extends SignalProcessor {
	private int T1;
	private int T2;
	
	
	public CambioTassoCampionamento(Signal s) {
		super(s);
		this.T1 = 1;
		this.T2 = 2;
	}
	
	@Override
	public Signal process() {
		int[] fattori = this.getParameters(this.T1, this.T2);
		
		// Sfrutto i segnali di ritorno per invocare immediatamente
		// l'operazione successiva.
		return this.getSignal()
			.espandi(fattori[0])
			.interpola(fattori[0])
			.decima(fattori[1]);
	}
	
	/**
	 * Dati T1 e T2 vengono calcolati i valori dei parametri F1 ed F2
	 * @param T1
	 * @param T2
	 * @return array contenente F1 ed F2
	 */
	
	private int[] getParameters(int t1, int t2){
		int gcd = Utils.gcd(t1, t2);
		int[] fArray = {t1/gcd, t2/gcd};
		return fArray;
	}
	
	public int getT1() {
		return T1;
	}

	public void setT1(int t1) {
		T1 = t1;
	}

	public int getT2() {
		return T2;
	}

	public void setT2(int t2) {
		T2 = t2;
	}
}
