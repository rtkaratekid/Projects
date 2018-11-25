package ui.generators;

import generators.VariableSinewaveGenerator;
import ui.App;
import ui.AppContents;
import ui.SynthComponentWidgetBase;

public class VariableSinewaveWidget extends SynthComponentWidgetBase {

	public VariableSinewaveWidget(AppContents parent) {
		this(parent, new VariableSinewaveGenerator(16, App.SAMPLE_RATE, 1.0));
	}

	public VariableSinewaveWidget(AppContents parent, VariableSinewaveGenerator vsg) {
		super(parent, "Variable Sinewave", vsg);

	}
}