package ui;

import common.SynthComponent;
import common.SynthInput;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Speaker implements ComponentWidget, SynthInput {

	private Circle circle;
	private SynthComponent source;
private InputPort[] ports;
	public Speaker(Pane parent, Bounds groupBounds) {
		source = null;
		circle = new Circle(groupBounds.getMaxX(), .5 * (groupBounds.getMinY() + groupBounds.getMaxY()), 30);
		circle.setFill(Color.GREEN);
		parent.getChildren().add(circle);
		ports = new InputPort[] {new InputPort(this, circle)};
		
	}

	
	public SynthComponent getSpeakerSource() {
		System.out.println("getting speaker source " + source);
		return source;
	}

	@Override
	public Circle getOutputPort() {
		return null;
	}

	@Override
	public SynthComponent getSource() {
		return null;
	}

	@Override
	public InputPort[] getInputPorts() {
		return ports;
	}


	@Override
	public String getName() {
		return "Input";
	}


	@Override
	public void connect(SynthComponent input) {
		source = input;
	}


	@Override
	public void disconnect(SynthComponent input) {
		source = null;
	}
}