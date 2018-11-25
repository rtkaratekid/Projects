package filters;

import common.Signal;
import common.SynthComponent;
import common.SynthComponentBase;
import common.SynthInput;
import exceptions.IncompatibleSignalException;

public class VariableLowpassFilter extends SynthComponentBase {

	SynthComponent input, frequencyInput;
	
	public VariableLowpassFilter() {
		setInputs(new SynthInput[] {new Input(), new CutoffFrequency()});
	}

	
	private class CutoffFrequency implements SynthInput {

		@Override
		public String getName() {
			return "Frequency";
		}

		@Override
		public void connect(SynthComponent input) {
			frequencyInput = input;
		}

		@Override
		public void disconnect(SynthComponent input) {
			frequencyInput = null;
		}
		
	}
	private class Input implements SynthInput {
		public String getName() {
			return "Input";
		}

		@Override
		public void connect(SynthComponent input) {
			VariableLowpassFilter.this.input = input;
		}

		@Override
		public void disconnect(SynthComponent input) {
			VariableLowpassFilter.this.input = null;
		}

	}
	
	@Override
	public Signal getSignal() throws IncompatibleSignalException {
		Signal inputSignal = input.getSignal();
		Signal frequencySignal = frequencyInput.getSignal();
		Signal output = new Signal(inputSignal);
		double dt = 1.0 / inputSignal.getSampleRate();

		for (int i = 1; i < inputSignal.getSampleCount(); ++i) {
			int filtered = LowpassFilter.filterSample(frequencySignal.getSample(i), dt, output.getSample(i -1), inputSignal.getSample(i));
			output.setSample(i, filtered);
		}
		return output;
	}
	
}