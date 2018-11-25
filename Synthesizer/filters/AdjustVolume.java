package filters;

import common.Signal;
import common.SynthComponent;
import common.SynthComponentBase;
import common.SynthInput;
import exceptions.IncompatibleSignalException;

public class AdjustVolume extends SynthComponentBase {

	private double scale;
	private SynthComponent input;

	public AdjustVolume(double scale) {
		this.scale = scale;
		setInputs(new SynthInput[] { new Input() });
	}

	@Override
	public Signal getSignal() throws IncompatibleSignalException {

		Signal output = new Signal(input.getSignal());
		for (int i = 0; i < output.getSampleCount(); ++i) {
			output.setSample(i, (int) (scale * output.getSample(i)));
		}
		return output;
	}

	public void setScale(double newScale) {
		scale = newScale;
	}

	public double getScale() {
		return scale;
	}

	private class Input implements SynthInput {

		@Override
		public String getName() {
			return "Input";
		}

		@Override
		public void connect(SynthComponent input) {
			AdjustVolume.this.input = input;
		}

		@Override
		public void disconnect(SynthComponent input) {
			AdjustVolume.this.input = null;
		}

	}

}