package ui;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Cable {

	private Line line;
	private ComponentWidget source;
	private InputPort target;
	private AppContents parent;

	public Cable(AppContents parent, Pane parentCanvas, ComponentWidget source, InputPort target) {

		this.parent = parent;
		this.source = source;
		this.target = target;

		line = new Line();
		line.setStrokeWidth(4);
		update();
		parentCanvas.getChildren().add(line);

		
		target.getInput().connect(source.getSource());

		line.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				destroy();
			}
		});

	}

	public void update() {
		Point2D sourceScene = Utils.circleSceneCoordinates(source.getOutputPort());
		Point2D targetScene = Utils.circleSceneCoordinates(target.getPort());
		
		line.setStartX(sourceScene.getX());
		line.setStartY(sourceScene.getY());
		line.setEndX(targetScene.getX());
		line.setEndY(targetScene.getY());
	}

	public void destroy() {
		target.getInput().disconnect(source.getSource());

		parent.removeCable(this);
		line = null;
	}

	//public SynthComponentWidgetBase getSource() { return source;}
	//public TargetWidget getTarget() { return target;}
	
	public Line getLine() {
		return line;
	}
}