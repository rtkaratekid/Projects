package generators;

import common.Signal;
import common.SynthComponent;
import common.SynthInput;
import exceptions.IncompatibleSignalException;

public class VariableSinewaveGenerator extends WaveformGeneratorBase{

	private SynthComponent inputFrequency;
	
	public VariableSinewaveGenerator(int bitsPerSample, double sampleRate, double duration) {
		super(bitsPerSample, sampleRate, duration);
		setInputs(new SynthInput[] {new Frequency()}); 
	}

	@Override
	public Signal getSignal() throws IncompatibleSignalException {
		
		Signal inputSignal = inputFrequency.getSignal();
		Signal output = new Signal(inputSignal);
		
		double phase = 0;
		double dt = 1.0/output.getSampleRate();
		int max = 0x7FFF;
		for(int i = 0; i < output.getSampleCount(); ++i) {
			int sample = (int)(max*Math.sin(phase));
			output.setSample(i, sample);
			
			phase += 2*Math.PI*inputSignal.getSample(i)*dt; 
		}
		return output;
	}

	private class Frequency implements SynthInput {
		@Override
		public String getName() {
			return "Frequency";
		}

		@Override
		public void connect(SynthComponent input) {
			VariableSinewaveGenerator.this.inputFrequency = input;
		}

		@Override
		public void disconnect(SynthComponent input) {
			VariableSinewaveGenerator.this.inputFrequency = null;
		}
	}
}