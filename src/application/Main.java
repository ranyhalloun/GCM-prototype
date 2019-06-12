package application;

import java.io.IOException;

import Entities.Attraction;
import Entities.Map;
import Entities.SearchMapResult;
import Entities.StringIntPair;
import Entities.Tour;
import Users.UserType;
import application.connection.connectionController;
import application.customer.Customer;
import application.customer.customerProfileController;
import application.customer.customerServicesController;
import application.gcmWorker.gcmWorkerController;
import application.insertMap.insertMapController;
import application.login.loginController;
import application.login.registrationController;
import application.searchmap.addAttractionToTourController;
import application.searchmap.attractionController;
import application.searchmap.cityController;
import application.searchmap.mapController;
import application.searchmap.searchMapController;
import application.searchmap.searchMapResultController;
import application.searchmap.tourController;
import application.searchmap.toursTableController;
import client.GCMClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import application.gcmManager.gcmManagerServicesController;
import application.gcmManager.requestListController;
import application.gcmWorker.requestApprovalController;

public class Main extends Application {

    private static Main main;
    private Stage primaryStage;
    private static AnchorPane mainLayout;
    private static GCMClient gcmClient;
    private static UserType userType;
    
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
	}
	
	public void getCityTours(String cityName) throws IOException
	{
		gcmClient.handleGetCityTours(cityName);
	}
	
	public void cityInfo(SearchMapResult searchMapResult) throws IOException
	{
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/cityView.fxml"));
	    loader.setController(new cityController(searchMapResult));
        AnchorPane cityView = loader.load();
        mainLayout.getChildren().setAll(cityView);
	}
	
	public void attractionInfo(SearchMapResult searchMapResult) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/attractionView.fxml"));
	    loader.setController(new attractionController(searchMapResult));
        AnchorPane attractionView = loader.load();
        mainLayout.getChildren().setAll(attractionView);
	}
	
	public void goToTourInfo(ArrayList<StringIntPair> attractionsTime, String cityName, ArrayList<Tour> tours, int tourID) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/tourView.fxml"));
	    loader.setController(new tourController(attractionsTime, cityName, tours, tourID));
        AnchorPane tourView = loader.load();
        mainLayout.getChildren().setAll(tourView);
	}
	
	public void goToMapsTable(SearchMapResult searchMapResult) throws IOException
	{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapResultView.fxml"));
        loader.setController(new searchMapResultController(searchMapResult));
        AnchorPane searchMapView = loader.load();
        mainLayout.getChildren().setAll(searchMapView);
	}
	
	
	
   public void goToMapInfo(Map map, SearchMapResult searchMapResult) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/mapView.fxml"));
        loader.setController(new mapController(map, searchMapResult));
        AnchorPane mapView = loader.load();
        mainLayout.getChildren().setAll(mapView);
    }

   	public void goToCityToursTable(ArrayList<Tour> tours, String cityName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/toursTable.fxml"));
        loader.setController(new toursTableController(tours, cityName));
        AnchorPane toursTableView = loader.load();
        mainLayout.getChildren().setAll(toursTableView);   		
   	}
   	
	public void registerNewUser(String firstname, String lastname, String username, String password, String email, String phone) throws IOException, InterruptedException
	{
	    gcmClient.handleRegistration(firstname, lastname, username, password, email, phone);
	}
	
	public void editCustomerInfo(Customer customer, String oldUsername, boolObject exists) throws IOException
	{
        gcmClient.handleEditingCustomerInfo(customer, oldUsername, exists);
	}
	
	public void requestApproval(String cityName) throws IOException
    {
        gcmClient.handleRequestApproval(cityName);
    }
	
	public void goToGCMManagerServices() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gcmManager/gcmManagerServicesView.fxml"));
        loader.setController(new gcmManagerServicesController());
        AnchorPane gcmManagerServicesView = loader.load();
        mainLayout.getChildren().setAll(gcmManagerServicesView);
    }
	
	public void goToCheckRequests() throws IOException{
        arrayOfStrings cities = new arrayOfStrings();
        gcmClient.handleGetCitiesQueue(cities);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gcmManager/requestListView.fxml"));
        loader.setController(new requestListController());
        Parent root = (Parent)loader.load();

        requestListController controller = loader.getController();
        controller.getCities(cities.getArrayList());
        mainLayout.getChildren().setAll(root);

    }

    public void goToRequestApproval() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gcmWorker/requestApprovalView.fxml"));
        loader.setController(new requestApprovalController());
        AnchorPane insertMapView = loader.load();
        mainLayout.getChildren().setAll(insertMapView);
    }

    public void goToRegistration(String errorMessage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login/registrationView.fxml"));
        loader.setController(new registrationController(errorMessage));
        AnchorPane registrationView = loader.load();
        mainLayout.getChildren().setAll(registrationView);
    }

    public void signIn(String username, String password) throws IOException {
        gcmClient.handleSignIn(username, password);
    }
    
    
    public void goToCostumerServices() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customer/customerServicesView.fxml"));
        loader.setController(new customerServicesController());
        AnchorPane customerServicesView = loader.load();
        mainLayout.getChildren().setAll(customerServicesView);

    }
    
    public void goToGCMWorkerServices() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gcmWorker/gcmWorkerView.fxml"));
        loader.setController(new gcmWorkerController());
        AnchorPane gcmWorkerView = loader.load();
        mainLayout.getChildren().setAll(gcmWorkerView);
    }

    public void goToSearchMap(String errorMessage) throws IOException
	{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapView.fxml"));
        loader.setController(new searchMapController(errorMessage));
        AnchorPane searchMapView = loader.load();
        mainLayout.getChildren().setAll(searchMapView);
	}

    public void goToLogin(String errorMessage) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("login/loginView.fxml"));
        loader.setController(new loginController(errorMessage));
	    AnchorPane loginView = loader.load();
        mainLayout.getChildren().setAll(loginView);
    }
    
    public void insertNewMap(int id, String cityName, String description, String imagePath) throws IOException {
        gcmClient.handleInsertNewMap(id, cityName, description, imagePath);
    }
    
    public void goToInsertNewMap() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("insertMap/insertMap.fxml"));
        loader.setController(new insertMapController());
        AnchorPane insertMapView = loader.load();
        mainLayout.getChildren().setAll(insertMapView);
    }
    
    public void goToCustomerProfile() throws IOException {
    	Customer customer = new Customer();
    	gcmClient.handleGetCustomerInfo(customer);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("customer/customerProfileView.fxml"));
        loader.setController(new customerProfileController());
        Parent root = (Parent)loader.load();

        customerProfileController controller = loader.getController();
        controller.getCustomer(customer);
        mainLayout.getChildren().setAll(root);
    }
    
    public void removeAttractionFromTour(String attractionName, int tourID) throws IOException {
    	gcmClient.handleRemoveAttractionFromTour(attractionName, tourID);
    }    	
    
    public void getAttractionsOfCity(String cityName,int tourID) throws IOException {
    	gcmClient.handleGetAttractionsOfCity(cityName, tourID);
    }
    public void goToAddAttractionToTour(ArrayList<Attraction> attractions, String errorMessage, int tourID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchMap/addAttractionToTourView.fxml"));
        loader.setController(new addAttractionToTourController(attractions, errorMessage, tourID));
        AnchorPane addAttractionToTourView = loader.load();
        mainLayout.getChildren().setAll(addAttractionToTourView);
    }
    
    public void goToAddAttractionToTour(String attractionID, int tourID, int time) throws IOException {
    	gcmClient.handleAddAttractionsToTour(attractionID, tourID, time);
    }
    
    public void updateUserType(UserType userType) {
        Main.userType = userType;
    }
    
    public UserType getUserType() {
    	return userType;
    }
    public void error(String message) throws IOException {
    	System.out.println(message);
    }
    
	public void connect(String ip, int port) throws IOException
	{
        gcmClient = new GCMClient("127.0.0.1", DEFAULT_PORT);
	    gcmClient.handleStartConnection(ip, port);
	    goToLogin("");
	}

    void stopConnection()
    {
        if (gcmClient != null) {
            if (gcmClient.isConnected()) {
                try {
                    gcmClient.closeConnection();
                } catch (IOException e) {
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
