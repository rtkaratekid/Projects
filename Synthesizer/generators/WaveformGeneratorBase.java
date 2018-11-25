package generators;

import common.SynthComponentBase;

public abstract class WaveformGeneratorBase extends SynthComponentBase {

	protected double duration, sampleRate;
	protected int bitsPerSample;
	
	protected WaveformGeneratorBase(int bitsPerSample, double sampleRate, double duration) {
		this.bitsPerSample = bitsPerSample;
		this.sampleRate = sampleRate;
		this.duration = duration;
	}
	
	public double getDuration() { return duration; }
	public double getSampleRate() { return sampleRate; }
	public int getBitsPerSample() { return bitsPerSample; }

	
}
