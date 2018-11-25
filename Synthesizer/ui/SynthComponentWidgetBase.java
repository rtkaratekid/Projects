package ui;

import common.SynthComponent;
import common.SynthInput;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class SynthComponentWidgetBase extends BorderPane implements ComponentWidget {

	

	protected SynthComponentWidgetBase(AppContents parent, String title, SynthComponent component) {

		this.setTop(new Label(title));

		grid = new GridPane();
		this.setStyle("-fx-border-color: black");
		this.setLayoutX(50);
		this.setLayoutY(50);
		this.setCenter(grid);

		outputPort = new Circle();
		outputPort.setRadius(10);
		outputPort.setFill(Color.BLUE);
		this.setRight(outputPort);
		BorderPane.setAlignment(outputPort, Pos.CENTER);

		makeDraggable(parent);

		this.component = component;
		makeInputPorts();
	}

	private void makeDraggable(AppContents parent) {
		this.setOnMousePressed(event -> {
			dragStartX = event.getSceneX();
			dragStartY = event.getSceneY();
			translateStartX = getTranslateX();
			translateStartY = getTranslateY();
		});

		this.setOnMouseDragged(event -> {
			if (!outputPort.contains(outputPort.sceneToLocal(new Point2D(dragStartX, dragStartY)))) {
				setTranslateX(translateStartX + event.getSceneX() - dragStartX);
				setTranslateY(translateStartY + event.getSceneY() - dragStartY);
				parent.updateCables();
			}
		});
	}

	private void makeInputPorts() {
		GridPane inputs = new GridPane();
		setLeft(inputs);
		SynthInput[] synthInputs = component.getInputs();
		inputPorts = new InputPort[synthInputs.length];
		for(int i = 0; i < inputPorts.length; ++i) {
			Circle port = new Circle(0,0,10);
			port.setFill(Color.GREEN);
			inputPorts[i] = new InputPort(synthInputs[i], port);
			int row = inputs.getRowCount();
			inputs.add(port, 0, row);
			inputs.add(new Label(synthInputs[i].getName()), 1, row);
		}
	}

	protected void addControl(String name, Node control) {
		int rows = grid.getRowCount();
		grid.add(new Label(name), 0, rows);
		grid.add(control, 1, rows);

	}

	@Override
	public SynthComponent getSource() {
		return component;
	}

	@Override
	public Circle getOutputPort() {
		return outputPort;
	}

	@Override
	public InputPort[] getInputPorts() {
		return inputPorts;
	}
	
	public void position(double x, double y) {
		setTranslateX(x);
		setTranslateY(y);
	}

	public WidgetInfo getWidgetInfo() {
		return new WidgetInfo(this.getClass(), this.getTranslateX(), this.getTranslateY(), this.getSource());
	}

	
	private double dragStartX, dragStartY, translateStartX, translateStartY;
	private SynthComponent component;
	private GridPane grid;
	private Circle outputPort;
	private InputPort[] inputPorts;
}