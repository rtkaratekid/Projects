package ui.filters;

import filters.AdjustVolume;
import ui.AppContents;
import ui.ComboWidget;
import ui.SynthComponentWidgetBase;

public class AdjustVolumeWidget extends SynthComponentWidgetBase {

	public AdjustVolumeWidget(AppContents parent) {
		this(parent, new AdjustVolume(0.5));
	}

	public AdjustVolumeWidget(AppContents parent, AdjustVolume av) {
		super(parent, "Adjust Volume", av);

		ComboWidget s = new ComboWidget(0, 1.0, av.getScale());
		s.addListener(newValue -> av.setScale(newValue));
		addControl("Scale", s);
	}
}