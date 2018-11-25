package ui.mixers;

import mixers.AdditiveMixer;
import ui.AppContents;
import ui.SynthComponentWidgetBase;

public class AdditiveMixerWidget extends SynthComponentWidgetBase {

	public AdditiveMixerWidget(AppContents parent) {
		this(parent, new AdditiveMixer());
	}

	public AdditiveMixerWidget(AppContents parent, AdditiveMixer mix) {
		super(parent, "Additive Mixer", mix);
	}
}