package ui;

import common.SynthComponent;
import javafx.scene.shape.Circle;

public interface ComponentWidget {
	Circle getOutputPort();
	SynthComponent getSource();
	InputPort[] getInputPorts();

}