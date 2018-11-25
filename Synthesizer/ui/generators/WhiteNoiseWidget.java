package ui.generators;

import generators.WhiteNoiseGenerator;
import ui.App;
import ui.AppContents;
import ui.SynthComponentWidgetBase;

public class WhiteNoiseWidget extends SynthComponentWidgetBase {

	public WhiteNoiseWidget(AppContents parent) {
		this(parent, new WhiteNoiseGenerator(16, App.SAMPLE_RATE, 1.0));
	}
	
	public WhiteNoiseWidget(AppContents parent, WhiteNoiseGenerator gen) {
		super(parent, "White Noise Generator", gen);
	}
}