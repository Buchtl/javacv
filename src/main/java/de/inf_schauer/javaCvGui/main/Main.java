package de.inf_schauer.javaCvGui.main;
	
import java.util.logging.Logger;


import de.inf_schauer.javaCvGui.gui.FXController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FirstFX.fxml"));
			BorderPane root = (BorderPane) loader.load();
			FXController controller = loader.getController();
			
			
			//BasicConfigurator.configure();
			Logger logger = Logger.getLogger("...");
			logger.info("digiDoc started");
			
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("FirstFX.fxml"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
			
			primaryStage.setTitle("JavaCvGui");	
			primaryStage.setScene(scene);	
			primaryStage.setMaximized(true);	
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_java300");
		launch(args);
	}
}
