package generators;

import common.Signal;
import common.SynthComponent;
import exceptions.IncompatibleSignalException;

public class LinearRamp extends WaveformGeneratorBase{

	private Signal buffer;
	private double start;
	private double stop;
	
	public LinearRamp(double start, double stop, int bitsPerSample, double sampleRate, double duration) {
		super(bitsPerSample, sampleRate, duration);
		this.start = start;
		this.stop = stop;
		generate();
	}
	
	private void generate() {
		int bytesPerSample = bitsPerSample/8;
		buffer = new Signal(sampleRate, duration, bytesPerSample);
		int nSamples = buffer.getSampleCount();
		for(int i = 0; i < nSamples; ++i) {
			double val = start*(nSamples -i)/nSamples + stop*i/nSamples;
			buffer.setSample(i, (int)val);
		}
	}

	@Override
	public Signal getSignal() throws IncompatibleSignalException {
		return buffer;
	}
	
	public double getStart() { return start;}
	public double getStop() { return stop;}
	public void setStart(double newStart) {
		start = newStart;
		generate();
	}
	public void setStop(double newStop) {
		stop = newStop;
		generate();
	}
	
	
}