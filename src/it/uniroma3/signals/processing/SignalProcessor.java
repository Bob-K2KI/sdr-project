package it.uniroma3.signals.processing;

import it.uniroma3.signals.domain.Signal;

public abstract class SignalProcessor {
	private Signal signal;
	
	public SignalProcessor(Signal s) {
		this.signal = s;
	}
	
	public abstract Signal process();
	
	protected Signal getSignal() {
		return signal;
	}

	protected void setSignal(Signal signal) {
		this.signal = signal;
	}	
}
