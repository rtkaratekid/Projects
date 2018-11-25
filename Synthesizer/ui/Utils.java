package ui;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Utils {

	static Point2D circleSceneCoordinates(Circle c) {
		return c.localToScene(new Point2D(c.getCenterX(), c.getCenterY()));
	}
}