package ui.generators;

import generators.LinearRamp;
import ui.App;
import ui.AppContents;
import ui.ComboWidget;
import ui.SynthComponentWidgetBase;

public class LinearRampWidget extends SynthComponentWidgetBase {

	public LinearRampWidget(AppContents parent) {
		this(parent, new LinearRamp(0, 5000, 16, App.SAMPLE_RATE, 1.0));
	}

	public LinearRampWidget(AppContents parent, LinearRamp ramp) {
		super(parent, "Linear Ramp", ramp);

		ComboWidget startSlider = new ComboWidget(220, 1200, ramp.getStart());
		startSlider.addListener(newVal -> ramp.setStart(newVal));
		addControl("Start", startSlider);

		ComboWidget stopSlider = new ComboWidget(220, 1200, ramp.getStop());
		stopSlider.addListener(newVal -> ramp.setStop(newVal));
		addControl("Stop", stopSlider);
	}
}