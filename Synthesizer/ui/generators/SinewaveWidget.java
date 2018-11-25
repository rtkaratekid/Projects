package ui.generators;

import generators.SinewaveGenerator;
import ui.App;
import ui.AppContents;
import ui.ComboWidget;
import ui.SynthComponentWidgetBase;

public class SinewaveWidget extends SynthComponentWidgetBase {

	public SinewaveWidget(AppContents parent) {
		this(parent, new SinewaveGenerator(440, 16, App.SAMPLE_RATE, 1.0));
	}

	public SinewaveWidget(AppContents parent, SinewaveGenerator gen) {
		super(parent, "Sinewave Generator", gen);

		ComboWidget s = new ComboWidget(0, 10000, gen.getFrequency());
		s.addListener(newVal -> gen.setFrequency(newVal));
		addControl("Frequency", s);
	}
}