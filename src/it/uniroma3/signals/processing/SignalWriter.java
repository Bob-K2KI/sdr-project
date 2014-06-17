package it.uniroma3.signals.processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import it.uniroma3.signals.domain.Signal;

public class SignalWriter {
	private Signal signal;
	private String filename;

	public SignalWriter(Signal s) {
		this.signal = s;
	}
	
	public boolean process() {
		return this.process(true);
	}

	public boolean process(boolean soloReali) {
		try {
			BufferedWriter bw = this.getWriter();
		
			for(int i = 0; i < this.signal.getLength(); i++)
				if(soloReali)
					bw.write(this.signal.getSample(i).getReale() + "\n");
				else
					bw.write(this.signal.getSample(i).getReale() + "\t" + this.signal.getSample(i).getImmaginaria() + "\n");
			
			bw.close();
			return true;
		} catch(IOException e) {
			return false;
		}
	}
	
	private BufferedWriter getWriter() throws IOException {
		File file = new File(this.filename);
		
		if(!file.exists())
			file.createNewFile();
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		return bw;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
