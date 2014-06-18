package it.uniroma3.signals;

import it.uniroma3.signals.domain.FileSignal;
import it.uniroma3.signals.domain.Signal;
import it.uniroma3.signals.spectrumsensing.energydetector.EnergyDetector;

import java.io.IOException;
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
		System.out.println("############ Online Section ############");
		Signal receivedSignal = this.getOriginalSignal();
		
		if( this.ed.detection(receivedSignal) )
			System.out.println("Attenzione: l'utente primario sta trasmettendo");
		else
			System.out.println("Daje forte: è un white space!");
		
	}
	
	private Signal getOriginalSignal() {
		String inputFilename;
		int length;
		Signal originario = null;
		
		System.out.print("Inserire il file originario: ");
		inputFilename = this.in.next();
		
		System.out.print("Inserire la quantità di campioni da leggere dal file originario: ");
		length = this.in.nextInt();
		
		System.out.print("Apro il file originario ...");
		try {
			originario = new FileSignal(inputFilename, length);
		} catch(IOException e) {
			System.out.print("ERRORE");
			System.exit(1);
		}
		System.out.println("... OK!");
		
		return originario;
	}
	
	
	public static void main(String[] args) {
		EnergyDetectorApp app = new EnergyDetectorApp();
		
		app.offlineCalculations();
		app.onlineDetection();
	}

}
