package it.uniroma3.signals;

import it.uniroma3.signals.spectrumsensing.energydetector.EnergyDetector;

import java.util.Scanner;

public class EnergyDetectorApp {
	private Scanner in;
	private EnergyDetector ed;
	
	public EnergyDetectorApp() {
		this.in = new Scanner(System.in);
	}
	
	public void offlineCalculations() {
		System.out.println("############ Offline Section ############");
		System.out.print("Inserire il SNR (in db) al quale si vuole lavorare: ");
		this.ed = new EnergyDetector(this.in.nextDouble());
		
		System.out.print("Inserire la probabilità di falso allarme desiderata: ");
		double Pfa = this.in.nextDouble();
		
		System.out.print("Inserire il numero di tentativi da effettuare per il calcolo della soglia: ");
		int attempts = this.in.nextInt();
		
		ed.generateThreshold(attempts, Pfa);
		
		System.out.println("Lavorando offline e' stata generato un valore di soglia per il dato SNR di: " + ed.getThreshold());
	}
	
	public void onlineDetection() {
		
	}
	
	
	public static void main(String[] args) {
		EnergyDetectorApp app = new EnergyDetectorApp();
		
		app.offlineCalculations();
		app.onlineDetection();
	}

}
