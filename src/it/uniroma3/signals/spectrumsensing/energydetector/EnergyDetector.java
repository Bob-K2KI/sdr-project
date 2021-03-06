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
		
		for(int i = 0; i < attempts; i++)
			energy[i] = SignalFactory.Noise(this.SNR, 1000, 1).getEnergy();
		
		Threshold th = new Threshold(fakeAlarmProbability);
		
		try {
			this.threshold = th.calculateTh(energy);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/* operazioni online */
	public boolean detection(Signal s) {
		System.out.println("Energia segnale: " + s.getEnergy() + " - Soglia: " + this.threshold);
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
