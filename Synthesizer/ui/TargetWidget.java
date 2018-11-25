
package ui;

import common.SynthComponent;
import javafx.scene.shape.Circle;

public interface TargetWidget {
	Circle getInputPort();
	void connectInput(SynthComponent source);
	void disconnectInput(SynthComponent source);
}