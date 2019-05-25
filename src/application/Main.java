package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {

	    // Constructing the scene
	    FXMLLoader loader = new FXMLLoader();
	    UIController uiController = new UIController();
	    loader.setController(uiController);
	    loader.setLocation(getClass().getResource("UIScene.fxml"));
	    AnchorPane pane = loader.load();
	    Scene scene = new Scene(pane);

	    // Setting the stage
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("GCM - Prototype");
	    primaryStage.show();
	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        public void handle(WindowEvent we) {
	            try {
                    uiController.stopConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
	        }
	    }); 
		               
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
