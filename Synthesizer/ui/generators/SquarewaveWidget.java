package ui.generators;

import generators.SquarewaveGenerator;
import ui.App;
import ui.AppContents;
import ui.ComboWidget;
import ui.SynthComponentWidgetBase;

public class SquarewaveWidget extends SynthComponentWidgetBase {

	public SquarewaveWidget(AppContents parent) {
		this(parent, new SquarewaveGenerator(440, 16, App.SAMPLE_RATE, 1.0));
	}

	public SquarewaveWidget(AppContents parent, SquarewaveGenerator gen) {
		super(parent, "Squarewave Generator", gen);

		ComboWidget s = new ComboWidget(1, 10000, gen.getFrequency());
		s.addListener(newVal -> gen.setFrequency(newVal));
		addControl("Frequency", s);
	}
}