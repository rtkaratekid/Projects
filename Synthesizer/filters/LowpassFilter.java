package filters;

import common.Signal;
import common.SynthComponent;
import common.SynthComponentBase;
import common.SynthInput;
import exceptions.IncompatibleSignalException;

public class LowpassFilter extends SynthComponentBase {

	private double cutoffFrequency;
	private SynthComponent input;

	public LowpassFilter(double cutoffFrequency) {
		this.cutoffFrequency = cutoffFrequency;
		setInputs(new SynthInput[] { new Input() });
	}

	
	protected static int filterSample(double cutoffFrequency, double dt, int prevOut, int currentIn) {
		double alpha = (2 * Math.PI * cutoffFrequency * dt) / (2 * Math.PI * cutoffFrequency * dt + 1);
		return (int)(prevOut + alpha*(currentIn - prevOut));
	}
	
	@Override
	public Signal getSignal() throws IncompatibleSignalException {
		Signal inputSignal = input.getSignal();
		Signal output = new Signal(inputSignal);
		double dt = 1.0 / inputSignal.getSampleRate();

		for (int i = 1; i < inputSignal.getSampleCount(); ++i) {
			int filtered = filterSample(cutoffFrequency, dt, output.getSample(i -1), inputSignal.getSample(i));
			output.setSample(i, filtered);
		}
		return output;
	}

	private class Input implements SynthInput {
		public String getName() {
			return "Input";
		}

		@Override
		public void connect(SynthComponent input) {
			LowpassFilter.this.input = input;
		}

		@Override
		public void disconnect(SynthComponent input) {
			LowpassFilter.this.input = null;
		}

	}

	public double getCutoffFrequency() {
		return cutoffFrequency;
	}

	public void setCutoffFrequency(double nff) {
		cutoffFrequency = nff;
	}
}