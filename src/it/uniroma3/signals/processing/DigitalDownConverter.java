package it.uniroma3.signals.processing;

import it.uniroma3.signals.domain.Signal;
import it.uniroma3.signals.domain.SignalFactory;

public class DigitalDownConverter extends SignalProcessor {
	private double sampleRate;
	private int band;
	private int deltaF;
	private int T1;
	private int T2;

	public DigitalDownConverter(Signal s) {
		super(s);
	}

	@Override
	public Signal process() {
		//tasso di campionamento originale
		this.T1 = (int) (1 / this.sampleRate);
		
		Signal newSignal;
		Signal lowPassFilter = SignalFactory.lowPassFilter(this.band);
		
		newSignal = this.applicaSelettoreCanale(this.getSignal());
		newSignal = newSignal.convoluzione(lowPassFilter);
		newSignal = this.applicaCambioTassoCampionamento(newSignal);	
		return newSignal;
	}
	
	private Signal applicaSelettoreCanale(Signal s) {
		SelettoreCanale processor = new SelettoreCanale(s);
		processor.setDeltaF(this.deltaF);
		return processor.process();
	}
	
	private Signal applicaCambioTassoCampionamento(Signal s) {
		CambioTassoCampionamento processor = new CambioTassoCampionamento(s);
		processor.setT1(this.T1);
		processor.setT2(this.T2);
		return processor.process();
	}

	public double getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}

	public int getDeltaF() {
		return deltaF;
	}

	public void setDeltaF(int deltaF) {
		this.deltaF = deltaF;
	}

	public int getT2() {
		return T2;
	}

	public void setT2(int t2) {
		T2 = t2;
	}
	
	public int getBand() {
		return band;
	}

	public void setBand(int band) {
		this.band = band;
	}
}
