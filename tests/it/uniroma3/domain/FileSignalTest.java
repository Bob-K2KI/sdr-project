package it.uniroma3.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileSignalTest {
	private String unexistsFile;
	private String malformedFile;
	private String wellformedFile;

	@Before
	public void setUp() throws Exception {
		this.unexistsFile = "test/404.txt";
		this.malformedFile = "test/500.txt";
		this.wellformedFile = "test/samples.txt";
	}
	
	@Test(expected=FileNotFoundException.class)
	public void fileNotFound() throws IOException {
		new FileSignal(this.unexistsFile, 10);
	}

	@Test
	public void malformedFile() throws IOException {
		FileSignal signal = new FileSignal(this.malformedFile, 1);
		assertNull(signal.getSample(0));
	}
	
	@Test
	public void wellformedFile() throws IOException {
		FileSignal signal = new FileSignal(this.wellformedFile, 3);
		Complex firtSample = signal.getSample(0);
		Complex secondSample = signal.getSample(1);
		Complex thirdSample = signal.getSample(2);
		Complex fourthSample = signal.getSample(3);


		assertEquals(-0.59564, firtSample.getReale(), 0);
		assertEquals(-0.25322, firtSample.getImmaginaria(), 0);

		assertEquals(2.9973, secondSample.getReale(), 0);
		assertEquals(0.15224, secondSample.getImmaginaria(), 0);

		assertEquals(0.12782, thirdSample.getReale(), 0);
		assertEquals(-1.4966, thirdSample.getImmaginaria(), 0);
		
		assertNull(fourthSample);
	}

	@Test
	public void samplesMapCache() throws IOException {
		FileSignal signal = new FileSignal(this.wellformedFile, 3);
		// Leggo il terzo campione
		Complex thirdSample = signal.getSample(2);

		// Mi assicuro che essendo passato dal campione 1 al terzo
		// ho memorizzato l'inizio degli altri campioni
		assertEquals(0, signal.getSamplesMap()[0]);
		assertEquals(18, signal.getSamplesMap()[1]);
		assertEquals(33, signal.getSamplesMap()[2]);
	}
}
