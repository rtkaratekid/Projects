package ui;

import java.util.ArrayList;

import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ComboWidget extends HBox {
	private double low, high;
	private ArrayList<ValueWatcher> listeners;

	private Slider s;
	private TextField tf;

	public ComboWidget(double low, double high, double start) {
		listeners = new ArrayList<>();
		this.low = low;
		this.high = high;
		
		s = new Slider(low, high, start);
		s.setShowTickLabels(true);
		tf = new TextField(Double.toString(s.getValue()));
		tf.setPrefColumnCount(3);

		s.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (inRange(newValue.doubleValue())) {
				tf.setText(Double.toString(newValue.doubleValue()));
				for (ValueWatcher l : listeners) {
					l.valueChanged(newValue.doubleValue());
				}
			}
		});

		tf.setOnAction(event -> {
			double d = Double.parseDouble(tf.getText());
			if (inRange(d)) {
				s.setValue(d);
				for (ValueWatcher l : listeners) {
					l.valueChanged(d);
				}
			} else {
				tf.setText(Double.toString(s.getValue()));
			}
		});

		getChildren().add(tf);
		getChildren().add(s);
	}

	public void addListener(ValueWatcher listener) {
		listeners.add(listener);
	}

	private boolean inRange(double x) {
		return x >= low && x <= high;
	}

}