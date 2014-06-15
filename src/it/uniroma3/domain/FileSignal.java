package it.uniroma3.domain;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileSignal extends Signal {
	private int length;
	private String filename;
	private RandomAccessFile memoryMappedFile;
	private MappedByteBuffer resource;
	
	private int[] samplesMap;
	

	public FileSignal(String filename, int length, int snr) throws IOException {
		this(filename, length);
		this.setSNR(snr);
	}
	
	public FileSignal(String filename, int length) throws IOException {
		this.memoryMappedFile = new RandomAccessFile(filename, "r");
		
		// Calcolo la porzione del file da caricare in memoria
		// come il minimo tra la lunghezza totale del file
		// e il numero di campioni che voglio leggere * 20
		// (dove 20 è attualmente il massimo numero di caratteri di una riga/campione)
		long bufferSize = Math.min(this.memoryMappedFile.length(), length * 20);
		
		this.resource = this.memoryMappedFile
				.getChannel()
				.map(FileChannel.MapMode.READ_ONLY, 0, bufferSize);
		
		
		this.length = length;
		this.samplesMap = new int[length];
		this.setSNR(Integer.MIN_VALUE);
	}

	@Override
	public Complex getSample(int i) {
		if(i >= this.length)
			return null;
		int initialIndex = this.getClosestReadedSample(i);
		int position;
		
		// Itero dal primo indice utile fino a che non arrivo allo i-esimo valore
		while(initialIndex < i) {
			// il byte iniziale è dato dalla samplesMap
			position = this.samplesMap[initialIndex];
			// ciclo fino a che non trovo un a capo che indica la fine di un sample
			while((char)this.resource.get(position++) != '\n');
				
			// incremento di uno per indicare l'indice del simbolo
			initialIndex++;
			// salvo nella sampleMap che ho trovato un nuovo simbolo
			this.foundNewSample(initialIndex, position);
		}
		
		// Ora che sono all'inizio del sample che devo leggere, lo leggo
		return this.readSample(initialIndex);
	}
	
	private int getClosestReadedSample(int i) {
		while(i > 0 && this.samplesMap[i] == 0)
			i--;
		return i;
	}
	
	private void foundNewSample(int index, int position) {
		this.samplesMap[index] = position;
	}
	
	private Complex readSample(int index) {
		StringBuilder bufferBuilder = new StringBuilder();
		// Posizione iniziale per quel campione
		int position = this.samplesMap[index];
		char tmp;
		
		try {
			// Prendo un carattere alla volta fino a che non trovo un a capo
			do {
				tmp = (char)this.resource.get(position++);
				bufferBuilder.append(tmp);
			} while(tmp != '\n');
		} catch(IndexOutOfBoundsException e) {
			// Nel caso in cui vado oltre la memoria consentita 
			return null;
		}
		
		// Spezzo i caratteri trovati in due stringhe
		String[] parts = bufferBuilder.toString().split("\t");
	
		// Converto le due stringhe nel campione
		try {
			return new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
		} catch(NumberFormatException e) {
			return null;
		}

	}

	
	public int getLength() {
		return this.length;
	}

	public String getFilename() {
		return filename;
	}

	public MappedByteBuffer getResource() {
		return resource;
	}

	public int[] getSamplesMap() {
		return this.samplesMap;
	}
}
