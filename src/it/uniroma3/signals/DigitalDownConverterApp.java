package it.uniroma3.signals;

import it.uniroma3.signals.domain.FileSignal;
import it.uniroma3.signals.domain.Signal;
import it.uniroma3.signals.processing.DigitalDownConverter;

import java.io.IOException;
import java.util.Scanner;

public class DigitalDownConverterApp {
	private Scanner in;
	
	public DigitalDownConverterApp() {
		this.in = new Scanner(System.in);
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
		System.out.print("... OK!");
		
		return originario;
	}
	
	private int getInt(String message) {
		System.out.print(message);
		return this.in.nextInt();
	}

	public static void main(String[] args) throws IOException {
		DigitalDownConverterApp ddc = new DigitalDownConverterApp();
		// Signal segnaleIniziale = ddc.getOriginalSignal();
		Signal segnaleIniziale = new FileSignal("/tmp/a/CBB_FM.txt", 2000000);
		
		// int bandaCanale = ddc.getInt("Inserire la banda dei singoli canali: ");
		int bandaCanale = 200000;
		
		// int portanteCanale = ddc.getInt("Inserire la portante del canale su cui sintonizzarsi: ");
		int portanteCanale = 0;
		
		DigitalDownConverter processor = new DigitalDownConverter(segnaleIniziale);
		processor.setBand(bandaCanale);
		processor.setDeltaF(portanteCanale);
		processor.setSampleRate(1);
		processor.setT2(1);
		
		Signal nuovoSegnale = processor.process();
		
		System.out.println(nuovoSegnale.scriviSuFile("/tmp/a/out.dat"));
	}

}
