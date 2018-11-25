package ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;

import common.Signal;
import exceptions.IncompatibleSignalException;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import ui.generators.SquarewaveWidget;

public class AppContents {

	private ArrayList<ComponentWidget> componentWidgets;

	private ComponentWidget selectedWidget;
	private Line activeCable;

	private Speaker mixerOutput;

	private ArrayList<Cable> cables;
	private Pane canvas;
	private Scene scene;

	public AppContents() {

		componentWidgets = new ArrayList<>();
		cables = new ArrayList<>();
		selectedWidget = null;
		activeCable = null;

		BorderPane border = new BorderPane();
		HBox controls = new HBox();
		Button play = new Button("play");
		controls.getChildren().add(play);
		border.setBottom(controls);
		controls.setStyle("-fx-border-color: black");

		canvas = new Pane();
		border.setCenter(canvas);
		scene = new Scene(border, 800, 800);

		play.setOnMouseClicked(event -> {
			try {
				if (mixerOutput.getSpeakerSource() == null) {
					return;
				}
				Clip c = AudioSystem.getClip();

				AudioFormat format16 = new AudioFormat(c.getFormat().getSampleRate(), 16, 1, true, false);
				Signal data = mixerOutput.getSpeakerSource().getSignal();
				c.open(format16, data.getData(), 0, data.getData().length);

				c.start();

				c.addLineListener(le -> {
					if (le.getType() == LineEvent.Type.STOP) {
						c.close();
					}
				});

			} catch (LineUnavailableException | IncompatibleSignalException e) {
				e.printStackTrace();
			}
		});

		setupCableDragging();

		border.setRight(new WidgetLibrary(this, canvas));

		border.applyCss();
		border.layout();

		Bounds groupBounds = border.getCenter().getLayoutBounds();
		mixerOutput = new Speaker(canvas, groupBounds);
		componentWidgets.add(mixerOutput);
		
		/*try {
			loadWidgets(new ObjectInputStream(new FileInputStream(App.filename)));
			border.applyCss();
			border.layout();
			updateCables();
		} catch (Exception e) {
			e.printStackTrace();
			addWidget(new SquarewaveWidget(this));
		}*/
		addWidget(new SquarewaveWidget(this));
	}

	private void setupCableDragging() {
		scene.setOnMousePressed(event -> {
			for (ComponentWidget w : componentWidgets) {
				if (w.getOutputPort() == event.getTarget()) {
					selectedWidget = w;
					activeCable = new Line();
					Point2D circleCoords = w.getOutputPort()
							.localToScene(new Point2D(w.getOutputPort().getCenterX(), w.getOutputPort().getCenterY()));

					activeCable.setStartX(circleCoords.getX());
					activeCable.setStartY(circleCoords.getY());
					activeCable.setEndX(circleCoords.getX());
					activeCable.setEndY(circleCoords.getY());

					canvas.getChildren().add(activeCable);

					return;
				}

			}
		});

		scene.setOnMouseDragged(event -> {
			if (activeCable != null) {
				activeCable.setEndX(event.getSceneX());
				activeCable.setEndY(event.getSceneY());
			}
		});

		scene.setOnMouseReleased(event -> {
			if (activeCable != null) {
				for (ComponentWidget w : componentWidgets) {
					for (InputPort ip : w.getInputPorts()) {
						Circle targetCircle = ip.getPort();
						Point2D pointLocal = targetCircle
								.sceneToLocal(new Point2D(event.getSceneX(), event.getSceneY()));
						if (targetCircle.contains(pointLocal)) {

							Cable cab = new Cable(AppContents.this, canvas, selectedWidget, ip);
							cables.add(cab);
							break;
						}
					}
				}
				canvas.getChildren().remove(activeCable);
				activeCable = null;
				selectedWidget = null;
			}

		});
	}

	public Scene getScene() {
		return scene;
	}

	public void addWidget(SynthComponentWidgetBase sourceWidget) {
		canvas.getChildren().add(sourceWidget);
		componentWidgets.add(sourceWidget);
	}

	public void updateCables() {
		for (Cable c : cables) {
			c.update();
		}
	}

	public void removeCable(Cable cable) {

		canvas.getChildren().remove(cable.getLine());
		cables.remove(cable);
	}

	/*private void loadWidgets(ObjectInputStream ois) throws Exception {
		WidgetInfo[] widgetInfos = (WidgetInfo[]) ois.readObject();
		for (WidgetInfo wi : widgetInfos) {
			System.out.println(wi);
			SynthComponentWidgetBase asw = (SynthComponentWidgetBase) wi.WidgetClass
					.getConstructor(this.getClass(), wi.source.getClass()).newInstance(this, wi.source);
			asw.position(wi.x, wi.y);
			addWidget(asw);
		}
		CableInfo[] cis = (CableInfo[]) ois.readObject();
		for (CableInfo ci : cis) {
			System.out.println(ci);
			if (ci.target >= 0) {
				cables.add(new Cable(this, canvas, componentWidgets.get(ci.source),
						(TargetWidget) componentWidgets.get(ci.target)));
			} else {
				cables.add(new Cable(this, canvas, componentWidgets.get(ci.source), mixerOutput));
			}
		}
	}
	public void writeWidgets(ObjectOutputStream oos) throws IOException {
		WidgetInfo[] wis = new WidgetInfo[componentWidgets.size()];
		for (int i = 0; i < wis.length; ++i) {
			wis[i] = componentWidgets.get(i).getWidgetInfo();
		}
		oos.writeObject(wis);
		CableInfo[] cis = new CableInfo[cables.size()];
		for (int i = 0; i < cis.length; ++i) {
			Cable c = cables.get(i);
			cis[i] = new CableInfo(componentWidgets.indexOf(c.getSource()), componentWidgets.indexOf(c.getTarget()));
		}
		oos.writeObject(cis);
	}*/
}