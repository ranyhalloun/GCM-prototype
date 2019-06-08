package application;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.prism.Image;

import application.connection.connectionController;
import application.login.loginController;
import application.login.registrationController;
import application.searchmap.Map;
import application.searchmap.mapImageController;
import application.searchmap.searchMapController;
import application.searchmap.searchMapResultController;
import application.errorBox.errorBoxController;

import client.GCMClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
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
	
	public void goToSearchMapResult(String attraction, String cityName, String description) throws IOException
	{
	    gcmClient.handleSearchMap(attraction, cityName, description);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapResultView.fxml"));
        loader.setController(new searchMapResultController());
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
    	goToSearchMap();
	}
	
    public void goToLogin() throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("login/loginView.fxml"));
        loader.setController(new loginController());
	    AnchorPane loginView = loader.load();
        mainLayout.getChildren().setAll(loginView);
    }
    
    
    public void error(String message) throws IOException {
    	System.out.println("hello");
    	goToLogin();
    }

    public void goToSearchMap() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapView.fxml"));
        loader.setController(new searchMapController());
        AnchorPane searchMapView = loader.load();
        mainLayout.getChildren().setAll(searchMapView);
    }
    
    public void goToMapImage(Map map) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/mapImage.fxml"));
        AnchorPane imageMapView = loader.load();
        mainLayout.getChildren().setAll(imageMapView);
    	//displayImage(map.getImagePath());
        
    	//AnchorPane tableViewParent = loader.load();
    	//Scene tableViewScene = new Scene(tableViewParent);
    	
    	/*mapImageController controller = loader.getController();
    	if(controller == null){
    		System.out.println("walaAhbla");
    	}
    	
    	controller.initData(tableView.getSelectionModel().getSelectedItem());
    	
    	tableViewParent = loader.load();
        AnchorPane mapImage = loader.load();
        mainLayout.getChildren().setAll(tableViewParent);*/
    }
    
    public void insertNewMap(int id, String cityName, String description, String imagePath) throws IOException {
	    gcmClient.handleInsertNewMap(id, cityName, description, imagePath);
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
	
   // example to input:"https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Printable_map_haifa_israel_g_view_level_12_eng_svg.svg/250px-Printable_map_haifa_israel_g_view_level_12_eng_svg.svg.png" 
   public void displayImage(String path) throws IOException {
	   URL url = new URL(path);
	   BufferedImage image = ImageIO.read(url);
	    
       JFrame frame = new JFrame();
       frame.setSize(300, 300);
       JLabel label = new JLabel(new ImageIcon(image));
       frame.add(label);
       frame.setVisible(true);
   }
	public static void main(String[] args)
	{
		launch(args);
	}
	
}
