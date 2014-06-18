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
//		System.out.print("Inserire il SNR (in db) al quale si vuole lavorare: ");
//		this.ed = new EnergyDetector(this.in.nextDouble());
		double db = -8;
		this.ed = new EnergyDetector(db);
		
//		System.out.print("Inserire la probabilità di falso allarme desiderata: ");
//		double Pfa = this.in.nextDouble();
		double Pfa = 0.001;
		
//		System.out.print("Inserire il numero di tentativi da effettuare per il calcolo della soglia: ");
//		int attempts = this.in.nextInt();
		int attempts = 1000;
		
		ed.generateThreshold(attempts, Pfa);
//		System.exit(0);
		
		System.out.println("Lavorando offline e' stata generato un valore di soglia per il dato SNR di: " + ed.getThreshold());
		System.out.println("SNR: " + db + " - Pfa: " + Pfa + "");
	}
	
	public void onlineDetection() {
//		System.out.println("############ Online Section ############");
//		Signal receivedSignal = this.getOriginalSignal();
		Signal receivedSignal = null;
		try {
			receivedSignal = new FileSignal("/tmp/sdr/1/output_SNR=-5dB.dat", 1000);
		} catch(IOException e) {
			System.out.print("ERRORE");
			System.exit(1);
		}

		if( this.ed.detection(receivedSignal) )
			System.out.println("Attenzione: l'utente primario sta trasmettendo");
		else
			System.out.println("Daje forte: è un white space!");
//		for(int i = 0; i < 100; i++){
//			if( this.ed.detection(receivedSignal.sliceSignal(i, 1000)) )
//				System.out.println("Attenzione: l'utente primario sta trasmettendo");
//			else
//				System.out.println("Daje forte: è un white space!");
//		}
		
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
