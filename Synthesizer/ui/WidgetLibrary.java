package ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.filters.AdjustVolumeWidget;
import ui.filters.LowpassWidget;
import ui.filters.VariableLowpassWidget;
import ui.generators.LinearRampWidget;
import ui.generators.SinewaveWidget;
import ui.generators.SquarewaveWidget;
import ui.generators.VariableSinewaveWidget;
import ui.generators.WhiteNoiseWidget;
import ui.mixers.AdditiveMixerWidget;
import ui.mixers.MultiplicativeMixerWidget;

public class WidgetLibrary extends VBox {

	public WidgetLibrary(AppContents parent, Pane canvas) {

		getChildren().add(new Label("Component Library"));

		getChildren().add(new Label("Generators"));

		Button whiteNoise = new Button("White Noise");
		whiteNoise.setOnAction(event -> parent.addWidget(new WhiteNoiseWidget(parent)));
		getChildren().add(whiteNoise);

		Button sinewave = new Button("Sine Wave");
		sinewave.setOnAction(event -> parent.addWidget(new SinewaveWidget(parent)));
		getChildren().add(sinewave);

		Button squarewave = new Button("Square Wave");
		squarewave.setOnAction(event -> parent.addWidget(new SquarewaveWidget(parent)));
		getChildren().add(squarewave);

		Button linearRamp = new Button("Linear Ramp");
		linearRamp.setOnAction(e -> parent.addWidget(new LinearRampWidget(parent)));
		getChildren().add(linearRamp);

		Button variableSinewave = new Button("Variable Sinewave");
		variableSinewave.setOnAction(event -> parent.addWidget(new VariableSinewaveWidget(parent)));
		getChildren().add(variableSinewave);

		getChildren().add(new Label("Filters"));

		Button adjustVolume = new Button("Adjust Volume");
		adjustVolume.setOnAction(event -> parent.addWidget(new AdjustVolumeWidget(parent)));
		getChildren().add(adjustVolume);

		Button lowpassFilter = new Button("Lowpass Filter");
		lowpassFilter.setOnAction(event -> parent.addWidget(new LowpassWidget(parent)));
		getChildren().add(lowpassFilter);
		
		Button variableLowpassFilter = new Button("Variable Lowpass Filter");
		variableLowpassFilter.setOnAction(event -> parent.addWidget(new VariableLowpassWidget(parent)));
		getChildren().add(variableLowpassFilter);
		
		getChildren().add(new Label("Mixers"));

		Button additiveMixer = new Button("Additive Mixer");
		additiveMixer.setOnAction(event -> parent.addWidget(new AdditiveMixerWidget(parent)));
		getChildren().add(additiveMixer);

		Button multiplicativeMixer = new Button("Multiplicative Mixer");
		multiplicativeMixer.setOnAction(event -> parent.addWidget(new MultiplicativeMixerWidget(parent)));
		getChildren().add(multiplicativeMixer);

	}

}