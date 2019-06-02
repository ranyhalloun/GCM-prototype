package application;

import java.io.IOException;

import application.connection.connectionController;
import application.login.loginController;
import application.searchmap.searchMapController;
import client.GCMClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
    
    private static Main main;
    private Stage primaryStage;
    private static AnchorPane mainLayout;
    private static GCMClient gcmClient;
    
    final public static int DEFAULT_PORT = 458;

    
    public static Main getInstance()
    {
        if (main == null) {
            main = new Main();
        }
        return main;
    }
    
	@Override
	public void start(Stage primaryStage) throws IOException
	{
	    this.primaryStage = primaryStage;
	    showConnectionView();
	}
	
	private void showConnectionView() throws IOException
	{
	    // Constructing the scene
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(getClass().getResource("connection/connectionView.fxml"));
        loader.setController(new connectionController());
	    mainLayout = loader.load();
	    Scene scene = new Scene(mainLayout);
	    
	    // Setting the stage
	    primaryStage.setScene(scene);
        primaryStage.setTitle("GCM Application");
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    stopConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	public void searchMap(String attraction, String cityName, String description) throws IOException
	{
	    gcmClient.handleSearchMap(attraction, cityName, description);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapResultView.fxml"));
        loader.setController(new loginController());
        AnchorPane loginView = loader.load();
        mainLayout.getChildren().setAll(loginView);
	}
	
	public void continueAsAnonymous() throws IOException
	{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapView.fxml"));
        loader.setController(new searchMapController());
        AnchorPane loginView = loader.load();
        mainLayout.getChildren().setAll(loginView);
	}
	
	public void connect(String ip, int port) throws IOException
	{
        gcmClient = new GCMClient("127.0.0.1", DEFAULT_PORT);
	    gcmClient.handleStartConnection(ip, port);
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("login/loginView.fxml"));
        loader.setController(new loginController());
	    AnchorPane loginView = loader.load();
        mainLayout.getChildren().setAll(loginView);
	}
	
    void stopConnection() throws IOException
    {
        if (gcmClient != null) {
            if (gcmClient.isConnected()) {
                gcmClient.closeConnection();
                System.out.println("The connection to the server has been disconnected");
            }
        }
        System.out.println("Exiting...");
    }
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}
