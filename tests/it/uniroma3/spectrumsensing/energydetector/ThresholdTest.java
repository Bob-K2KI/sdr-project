package it.uniroma3.spectrumsensing.energydetector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ThresholdTest {
	
	private double[] energy;
	private Treshold th;
	private double pfa;
	private double mean;
	private double variance;
	private double threshold;

	@Before
	public void setUp() throws Exception {
		pfa = 0.001;
		th = new Threshold(pfa);
		energy = new double[]{12.3, 0.5, 6.7, 9, 11, 3,2};
		mean = 7.1166;
		variance = 17.4647;		
	}

	@Test
	public void testCalculateMean() {
		double calculatedMean = th.calculateMean(energy);
		assertEquals(calculatedMean, mean, 0.0001);
	}

	@Test
	public void testCalculateVariance() {
		double calculatedVariance = th.calculateVariance(energy);
		assertEquals(calculatedVariance, variance, 0.0001);
	}

}
