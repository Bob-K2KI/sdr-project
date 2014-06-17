package it.uniroma3.signals.domain;

public class ArraySignal extends Signal {
	
	private Complex[] values;
	
	public ArraySignal(Complex[] values) {
		this.values = values;
		this.setSNR(Integer.MIN_VALUE);
	}

	public ArraySignal(Complex[] values, int snr) {
		this.values = values;
		this.setSNR(snr);
	}
	
	public Complex[] getValues() {
		return values;
	}
	
	@Override
	public Complex getSample(int i) {
		return this.values[i];
	}


	public void setValues(Complex[] values) {
		this.values = values;
	}

	public int getLength() {
		return this.values.length;
	}


}
