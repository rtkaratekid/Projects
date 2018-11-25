package generators;

import common.Signal;
import common.SynthComponent;

public class SinewaveGenerator extends WaveformGeneratorBase implements SynthComponent {

	private Signal buffer;
	private double frequency;
	
	public SinewaveGenerator(double frequency, int bitsPerSample, double sampleRate, double duration) {
		super(bitsPerSample, sampleRate, duration);
		this.frequency = frequency;
		
		generate();
		
		
	}
	
	private void generate() {
		int bytesPerSample = bitsPerSample/8;
		buffer = new Signal(sampleRate, duration, bytesPerSample);
		
		int max = Integer.MAX_VALUE >> (32 - bitsPerSample);
		
		for(int i = 0; i < buffer.getSampleCount(); ++i){
			int sample = (int)(max*Math.sin(2*Math.PI*frequency*i/sampleRate));
			buffer.setSample(i, sample);
			
		}
	}
	
	
	@Override
	public Signal getSignal() {
		return buffer;
	}
	public double getFrequency() {
		return frequency;
	}
	
	public void setFrequency(double newFrequency) {
		frequency = newFrequency;
		generate();
	}

}