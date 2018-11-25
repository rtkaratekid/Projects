package ui.filters;

import filters.LowpassFilter;
import ui.AppContents;
import ui.ComboWidget;
import ui.SynthComponentWidgetBase;

public class LowpassWidget extends SynthComponentWidgetBase {

	public LowpassWidget(AppContents parent) {
		this(parent, new LowpassFilter(1000));
	}

	public LowpassWidget(AppContents parent, LowpassFilter fil) {
		super(parent, "Lowpass Filter", fil);

		ComboWidget s = new ComboWidget(0, 10000, fil.getCutoffFrequency());
		s.addListener(newValue -> fil.setCutoffFrequency(newValue));
		addControl("Cutoff Frequency", s);
	}
}