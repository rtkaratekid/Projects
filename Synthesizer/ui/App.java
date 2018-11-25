package ui;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	public static final double SAMPLE_RATE = 44100.0;
	public static final String filename = "savedSession.obj";
	
	
	private AppContents appContents;

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		
			appContents = new AppContents();
		
		
		primaryStage.setScene(appContents.getScene());
		
		primaryStage.show();
	}

	
	/*@Override
	public void stop() {
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filename));
			appContents.writeWidgets(oos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}*/
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	
}