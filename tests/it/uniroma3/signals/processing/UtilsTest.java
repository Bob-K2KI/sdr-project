package it.uniroma3.signals.processing;

import static org.junit.Assert.*;
import it.uniroma3.signals.processing.Utils;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void gcd() {
		assertEquals(6, Utils.gcd(24, 18));
		assertEquals(18, Utils.gcd(0, 18));
		assertEquals(24, Utils.gcd(24, 0));
		assertEquals(1, Utils.gcd(1, 1));
	}

	@Test(expected=IllegalArgumentException.class)
	public void gcdWithNegative() {
		Utils.gcd(-1, -5);
	}
		
}
