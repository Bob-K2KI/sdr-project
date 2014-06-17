package it.uniroma3.signals.domain;

import static org.junit.Assert.*;
import it.uniroma3.signals.domain.Complex;

import org.junit.Before;
import org.junit.Test;

public class ComplexTest {
	private Complex zero;
	private Complex reale;
	private Complex immaginario;
	private Complex complesso;
	
	
	@Before
	public void setUp() throws Exception {
		this.zero = new Complex(0, 0);
		this.reale = new Complex(10, 0);
		this.immaginario = new Complex(0, 10);
		this.complesso = new Complex(25, 3.44);
	}
	
	@Test
	public void getterTest() {
		assertEquals(0, this.zero.getReale(), 0);
		assertEquals(0, this.zero.getImmaginaria(), 0);
		
		assertEquals(25, this.complesso.getReale(), 0);
		assertEquals(3.44, this.complesso.getImmaginaria(), 0);
	}
	
	@Test
	public void moduloTest() {
		assertEquals(0, this.zero.abs(), 0);
		assertEquals(10, this.reale.abs(), 0);
		assertEquals(10, this.immaginario.abs(), 0);
		assertEquals(25.23556, this.complesso.abs(), 0.00001);
	}
	
	@Test
	public void coniugatoTest() {
		assertEquals(0, this.zero.coniugato().getReale(), 0);
		assertEquals(0, this.zero.coniugato().getImmaginaria(), 0);
		
		assertEquals(25, this.complesso.coniugato().getReale(), 0);
		assertEquals(-3.44, this.complesso.coniugato().getImmaginaria(), 0);
	}
	
	@Test
	public void sommaTest() {
		assertEquals(0, this.zero.somma(this.zero).getReale(), 0);
		assertEquals(0, this.zero.somma(this.zero).getImmaginaria(), 0);

		assertEquals(10, this.reale.somma(this.immaginario).getReale(), 0);
		assertEquals(10, this.reale.somma(this.immaginario).getImmaginaria(), 0);
		
		assertEquals(35, this.complesso.somma(this.reale).getReale(), 0);
		assertEquals(3.44, this.complesso.somma(this.reale).getImmaginaria(), 0);
		
		assertEquals(50, this.complesso.somma(this.complesso).getReale(), 0);
		assertEquals(6.88, this.complesso.somma(this.complesso).getImmaginaria(), 0);
	}
	
	@Test
	public void expTest() {
		assertEquals(1, this.zero.exp().abs(), 0);
		assertEquals(1, this.zero.exp().getReale(), 0);
		assertEquals(0, this.zero.exp().getImmaginaria(), 0);
	}

}