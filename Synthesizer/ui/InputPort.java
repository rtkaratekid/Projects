package ui;

import common.SynthInput;
import javafx.scene.shape.Circle;

public class InputPort {
	private SynthInput input;
	private Circle port;

	public InputPort(SynthInput input, Circle circle) {
		this.input = input;
		this.port = circle;
	}
	
	public Circle getPort() { return port; }
	public SynthInput getInput() { return input; }
}