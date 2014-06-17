package it.uniroma3.signals.processing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.signals.domain.ArraySignal;
import it.uniroma3.signals.domain.Complex;
import it.uniroma3.signals.domain.Signal;
import it.uniroma3.signals.processing.CambioTassoCampionamento;

public class CambioTassoCampionamentoTest {
	
	private Complex[] vettoreComplessoUno;
	private Complex[] vettoreComplessoDue;
	
	private Signal segnaleUno;
	private Signal segnaleDue;

	@Before
	public void setUp() throws Exception {
		
		this.vettoreComplessoUno = new Complex[] {
			new Complex(3,0),
			new Complex(2,0),
			new Complex(1,0)
		};
		
		this.vettoreComplessoDue = new Complex[]{
				new Complex(3,0),
				new Complex(0,0)
		};
		
		this.segnaleUno = new ArraySignal(this.vettoreComplessoUno);	
		this.segnaleDue = new ArraySignal(this.vettoreComplessoDue);
	}
	
    @Test
    public void cambioTassoFattoriUno(){
        int T1 = 1,
            T2 = 1;
        
        CambioTassoCampionamento processor = new CambioTassoCampionamento(this.segnaleUno);
        processor.setT1(T1);
        processor.setT2(T2);
        
        Signal segnaleNuovoTasso = processor.process();
        
        assertEquals(this.segnaleUno.getLength(), segnaleNuovoTasso.getLength());
        
        assertEquals(this.segnaleUno.getSample(0), segnaleNuovoTasso.getSample(0));
        assertEquals(this.segnaleUno.getSample(1), segnaleNuovoTasso.getSample(1));
        assertEquals(this.segnaleUno.getSample(2), segnaleNuovoTasso.getSample(2));
    }
    
    @Test
    public void cambioTassoFattoriNonUno(){
        int T1 = 10,
        	T2 = 15;
        
        CambioTassoCampionamento processor = new CambioTassoCampionamento(this.segnaleUno);
        processor.setT1(T1);
        processor.setT2(T2);
        
        Signal segnaleNuovoTasso = processor.process();
        
        assertEquals(this.segnaleDue.getLength(), segnaleNuovoTasso.getLength());
        
        assertEquals(this.segnaleDue.getSample(0), segnaleNuovoTasso.getSample(0));
        assertEquals(1.2732, segnaleNuovoTasso.getSample(1).getReale(), 0.0001);      
    }

}
