package application;

import java.io.IOException;

import application.connection.connectionController;
import application.login.loginController;
import application.login.registrationController;
import application.searchmap.searchMapController;
import client.GCMClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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
        System.out.printf("client: %s%n", Thread.currentThread().getName());
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
                stopConnection();
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
	
	public void registerNewUser(String firstname, String lastname, String username, String password, String email, String phone) throws IOException
	{	
	    gcmClient.handleRegistration(firstname, lastname, username, password, email, phone);
	    
	}
	
	
    public void goToRegistration() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login/registrationView.fxml"));
        loader.setController(new registrationController());
        AnchorPane registrationView = loader.load();
        mainLayout.getChildren().setAll(registrationView);
    }
    
    public void signIn(String username, String password) throws IOException {
        gcmClient.handleSignIn(username, password);        
    }
	
    public void continueAsAnonymous() throws IOException
	{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapView.fxml"));
        loader.setController(new searchMapController());
        AnchorPane searchMapView = loader.load();
        mainLayout.getChildren().setAll(searchMapView);
	}
	
    public void goToLogin() throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("login/loginView.fxml"));
        loader.setController(new loginController());
	    AnchorPane loginView = loader.load();
        mainLayout.getChildren().setAll(loginView);
    }
    
    
    public void error(String message) throws IOException {
        
    	System.out.println(message);
    	
    	
    }

    
	public void connect(String ip, int port) throws IOException
	{
        gcmClient = new GCMClient("127.0.0.1", DEFAULT_PORT);
	    gcmClient.handleStartConnection(ip, port);
	    goToLogin();
	}
	
    void stopConnection()
    {
        if (gcmClient != null) {
            if (gcmClient.isConnected()) {
                try {
                    gcmClient.closeConnection();
                } catch (IOException e) {
                    System.out.println("Shit");
                    e.printStackTrace();
                }
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
