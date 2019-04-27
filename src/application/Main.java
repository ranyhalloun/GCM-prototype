package application;
	
import java.io.IOException;
import java.net.URL;

import client.GCMClient;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {

	    FXMLLoader loader = new FXMLLoader();
	    loader.setController(new UIController());
	    loader.setLocation(getClass().getResource("UIScene.fxml"));
	    AnchorPane pane = loader.load();
//	    GCMClient gcmClient = new GCMClient("127.0.0.1", 458);
//	    URL url = getClass().getResource("UIScene.fxml");
//	    AnchorPane pane = FXMLLoader.load(url);
	    Scene scene = new Scene(pane);

	    primaryStage.setScene(scene);
	    primaryStage.setTitle("GCM - Prototype");
	    primaryStage.show();
		               
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
