package it.uniroma3.domain;

public abstract class Signal {
	private int SNR;
	
	public abstract Complex getSample(int i);

	public int getSNR() {
		return this.SNR;
	}

	public void setSNR(int SNR) {
		this.SNR = SNR;
	}

}
