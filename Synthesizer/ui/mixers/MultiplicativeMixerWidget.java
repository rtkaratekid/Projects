package ui.mixers;

import mixers.MultiplicativeMixer;
import ui.AppContents;
import ui.SynthComponentWidgetBase;

public class MultiplicativeMixerWidget extends SynthComponentWidgetBase {

	public MultiplicativeMixerWidget(AppContents parent) {
		this(parent, new MultiplicativeMixer());
	}
	public MultiplicativeMixerWidget(AppContents parent, MultiplicativeMixer mix) {
		super(parent, "Multiplicative Mixer", mix);
	}
}