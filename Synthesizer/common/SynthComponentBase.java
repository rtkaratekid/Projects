package common;

public abstract class SynthComponentBase implements SynthComponent{
	private SynthInput[] inputs = new SynthInput[0];

	protected void setInputs(SynthInput[] inputs) {
		this.inputs = inputs;
	}
	@Override
	public SynthInput[] getInputs() {
		return inputs;
	}
}
