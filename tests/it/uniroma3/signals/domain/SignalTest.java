package it.uniroma3.signals.domain;

import static org.junit.Assert.*;
import it.uniroma3.signals.domain.ArraySignal;
import it.uniroma3.signals.domain.Complex;
import it.uniroma3.signals.domain.Signal;
import it.uniroma3.signals.domain.SignalFactory;

import org.junit.Before;
import org.junit.Test;

public class SignalTest {

	
	private Complex[] vettoreComplessoUno;
	private Complex[] vettoreComplessoDue;
	private Complex[] vettoreComplessoTre;
	
	private Signal segnaleUno;	
	private Signal segnaleDue;
	private Signal segnaleTre;

	@Before
	public void setUp() throws Exception {
		
		this.vettoreComplessoUno = new Complex[] {
			new Complex(3,0),
			new Complex(2,0),
			new Complex(1,0)
		};
		
		this.vettoreComplessoDue = new Complex[] {
			new Complex(1,0),
			new Complex(1,0),
			new Complex(2,0),
			new Complex(1,0)
		};
		
		this.vettoreComplessoTre = new Complex[]{
				new Complex(1,0),
				new Complex(0,0),
				new Complex(2,0),
				new Complex(0,0),
				new Complex(1,0),
				new Complex(0,0)
			};
		
		this.segnaleUno     = new ArraySignal(this.vettoreComplessoUno);	
		this.segnaleDue     = new ArraySignal(this.vettoreComplessoDue);	
		this.segnaleTre     = new ArraySignal(this.vettoreComplessoTre);
	}
	
    @Test
    public void convoluzioneComplessa() {
        Signal risultato = this.segnaleUno.convoluzione(this.segnaleDue);
        
        assertEquals(3, risultato.getSample(0).getReale(), 0);
        assertEquals(5, risultato.getSample(1).getReale(), 0);
        assertEquals(9, risultato.getSample(2).getReale(), 0);
        assertEquals(8, risultato.getSample(3).getReale(), 0);
        assertEquals(4, risultato.getSample(4).getReale(), 0);
        assertEquals(1, risultato.getSample(5).getReale(), 0);
    }
	
	@Test
	public void sinc() {
		double band = 0.25;
		assertEquals(1, SignalFactory.sinc(0, 20), 0);
		assertEquals(0, SignalFactory.sinc(1.0/(2.0*band), band), 0);

		// Me lo ha detto Wolfram
		// http://www.wolframalpha.com/input/?i=sinc%280.42+*+0.5+*+pi%29
		assertEquals(0.9290, SignalFactory.sinc(0.42, band), 0.0001);
	}
	
    @Test
    public void lowPassFilter() {
        double band = 0.25;
        Signal filter = SignalFactory.lowPassFilter(band);
        int simmetria = filter.getLength() / 2;
        
        assertEquals(0, filter.getSample(simmetria-2).abs(), 0);
        assertEquals(0.3183, filter.getSample(simmetria-1).abs(), 0.0001);
        assertEquals(0.5, filter.getSample(simmetria).abs(), 0);
        assertEquals(0.3183, filter.getSample(simmetria+1).abs(), 0.0001);
        assertEquals(0, filter.getSample(simmetria+2).abs(), 0);

    }
    
    @Test
    public void bandPassFilter() {
        double band = 0.25,
               portante = 3;
        
        Signal filter = SignalFactory.bandPassFilter(band, portante);
        int simmetria = filter.getLength() / 2;
        
        // 2 * B * sinc(2*pi*B*n) * 2 * cos(2 * pi * f0 * n)

        assertEquals(0, filter.getSample(simmetria-2).getReale(), 0);
        assertEquals(0.6366, filter.getSample(simmetria-1).getReale(), 0.0001);
        assertEquals(4*band, filter.getSample(simmetria).getReale(), 0);
        assertEquals(0.6366, filter.getSample(simmetria+1).getReale(), 0.0001);
        assertEquals(0, filter.getSample(simmetria+2).getReale(), 0);
    }
    
    @Test
    public void espansoreFattoreDue() {
        int fattore = 2;
        Signal espanso = this.segnaleUno.espandi(fattore);
        assertEquals(this.segnaleUno.getLength() * fattore, espanso.getLength());
        
        Complex zero = new Complex();
        
        assertEquals(this.segnaleUno.getSample(0), espanso.getSample(0));
        assertEquals(zero, espanso.getSample(1));
        
        assertEquals(this.segnaleUno.getSample(1), espanso.getSample(2));
        assertEquals(zero, espanso.getSample(3));
        
        assertEquals(this.segnaleUno.getSample(2), espanso.getSample(4));
        assertEquals(zero, espanso.getSample(5));
    }
    
    @Test
    public void espansoreFattoreUno() {
        int fattore = 1;
        Signal espanso = this.segnaleUno.espandi(fattore);
        assertEquals(this.segnaleUno.getLength(), espanso.getLength());
        
        assertEquals(this.segnaleUno.getSample(0), espanso.getSample(0));
        assertEquals(this.segnaleUno.getSample(1), espanso.getSample(1));
        assertEquals(this.segnaleUno.getSample(2), espanso.getSample(2));
    }
    
    @Test
    public void interpolatoreFattoreUno(){
        int fattore = 1;
        Signal interpolato = this.segnaleTre.interpola(fattore);
        
        assertEquals(this.segnaleTre.getLength(), interpolato.getLength());
        
        assertEquals(this.segnaleTre.getSample(0), interpolato.getSample(0));
        assertEquals(this.segnaleTre.getSample(1), interpolato.getSample(1));
        assertEquals(this.segnaleTre.getSample(2), interpolato.getSample(2));
        assertEquals(this.segnaleTre.getSample(3), interpolato.getSample(3));
        assertEquals(this.segnaleTre.getSample(4), interpolato.getSample(4));
        assertEquals(this.segnaleTre.getSample(5), interpolato.getSample(5));
        
    }
    
    @Test
    public void interpolatoreFattoreDue(){
        int fattore = 2;
        Signal interpolato = this.segnaleTre.interpola(fattore);
        
        assertEquals(this.segnaleTre.getLength(), interpolato.getLength());
        
        
        assertEquals(this.segnaleTre.getSample(0), interpolato.getSample(0));
        assertEquals(1.6976, interpolato.getSample(1).getReale(), 0.0001);
        assertEquals(this.segnaleTre.getSample(2), interpolato.getSample(2));
        assertEquals(1.6976, interpolato.getSample(3).getReale(), 0.0001);
        assertEquals(this.segnaleTre.getSample(4), interpolato.getSample(4));
        assertEquals(0.3395, interpolato.getSample(5).getReale(), 0.0001);
    }
    
    @Test
    public void decimatoreFattoreDue() {
        int fattore = 2;
        Signal decimato = this.segnaleDue.decima(fattore);
        assertEquals(this.segnaleDue.getLength() / fattore, decimato.getLength());
        
        assertEquals(this.segnaleDue.getSample(0), decimato.getSample(0));
        assertEquals(this.segnaleDue.getSample(2), decimato.getSample(1));
    }
    
    @Test
    public void decimatoreFattoreUno() {
        int fattore = 1;
        Signal decimato = this.segnaleDue.decima(fattore);
        assertEquals(this.segnaleDue.getLength(), decimato.getLength());
        
        assertEquals(this.segnaleDue.getSample(0), decimato.getSample(0));
        assertEquals(this.segnaleDue.getSample(1), decimato.getSample(1));
        assertEquals(this.segnaleDue.getSample(2), decimato.getSample(2));
        assertEquals(this.segnaleDue.getSample(3), decimato.getSample(3));
    }
}
