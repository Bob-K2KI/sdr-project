package it.uniroma3.signals.spectrumsensing.energydetector;

import it.uniroma3.signals.domain.Signal;
import it.uniroma3.signals.domain.SignalFactory;

public class EnergyDetector {
	private double threshold;
	private double SNR;
	
	public EnergyDetector(double SNR) {
		this.SNR = SNR;
	}
	
	
	/* operazioni offline */ 
	public void generateThreshold(int attempts, double fakeAlarmProbability) {
		double[] energy = new double[attempts];
		double noisePower = Math.pow(10, (this.SNR / 10));
		
		for(int i = 0; i < attempts; i++)
			energy[i] = SignalFactory.Noise(this.SNR, 10*1000, noisePower).getEnergy();
		
		//this.threshold = new Soglia(energy, fakeAlarmProbability);
	}
	
	
	/* operazioni online */
	public boolean detection(Signal s) {
		return s.getEnergy() > this.threshold;
	}
	
	
	
	/* getters & setters */
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	public double getSNR() {
		return SNR;
	}
	public void setSNR(double sNR) {
		SNR = sNR;
	}
}
