package ui.filters;

import filters.VariableLowpassFilter;
import ui.AppContents;
import ui.SynthComponentWidgetBase;

public class VariableLowpassWidget extends SynthComponentWidgetBase {
	public VariableLowpassWidget(AppContents parent) {
		this(parent, new VariableLowpassFilter());
	}
	
	public VariableLowpassWidget(AppContents parent, VariableLowpassFilter fil) {
		super(parent, "Lowpass Filter", fil);
	}
}