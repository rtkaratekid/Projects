package generators;

import common.Signal;
import common.SynthComponent;

public class SquarewaveGenerator  extends WaveformGeneratorBase implements SynthComponent{

	
	Signal buffer;
	private double frequency;
	
	public SquarewaveGenerator(double frequency, int bitsPerSample, double sampleRate, double duration){
		super(bitsPerSample, sampleRate, duration);
		this.frequency = frequency;
		
		generate();
		
		
	}
	private void generate() {
		int bytesPerSample = bitsPerSample/8;
		buffer = new Signal(sampleRate, duration, bytesPerSample);
		int max = Integer.MAX_VALUE >> (32 - bitsPerSample);
		
		for(int i = 0; i < buffer.getSampleCount(); ++i){
			int sample = max;
			if((frequency*i/sampleRate) % 1 > 0.5){
				sample *= -1;
			}
			buffer.setSample(i, sample);
		}
	}
	
	public double getFrequency() {
		return frequency;
	}
	
	public void setFrequency(double newFrequency) {
		frequency = newFrequency;
		generate();
	}
	@Override
	public Signal getSignal() {
		return buffer;
	}

}