package application;

import java.io.IOException;
import java.time.LocalDate;

import Entities.Attraction;
import Entities.AttractionTimePair;
import Entities.City;
import Entities.DownloadDetails;
import Entities.Map;
import Entities.PurchaseDetails;
import Entities.Report;
import Entities.SearchMapResult;
import Entities.StringIntPair;
import Entities.Tour;
import Entities.ViewDetails;
import Users.UserType;
import application.companyManager.companyManagerServicesController;
import application.companyManager.pricesController;
import application.connection.connectionController;
import application.customer.Customer;
import application.customer.customerProfileController;
import application.customer.customerServicesController;
import application.gcmWorker.gcmWorkerController;
import application.insertMap.externalMapsController;
import application.insertMap.insertCityController;
import application.login.loginController;
import application.login.registrationController;
import application.reports.citiesAndDatesController;
import application.reports.cityReportController;
import application.reports.customerReportController;
import application.reports.downloadsHistoryController;
import application.reports.purchasesHistoryController;
import application.reports.searchCustomerController;
import application.reports.viewsHistoryController;
import application.searchmap.addAttractionToTourController;
import application.searchmap.attractionController;
import application.searchmap.cityController;
import application.searchmap.mapController;
import application.searchmap.searchMapController;
import application.searchmap.searchMapResultController;
import application.searchmap.tourController;
import application.searchmap.toursTableController;
import application.tours.AddTourToCityController;
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
import application.gcmManager.changePricesController;
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

	public void goToMapsInfo(String attraction, String cityName, String description) throws IOException
	{
	    // Get the maps
	    ArrayList<Map> maps = gcmClient.handleGetMapsInfo(attraction, cityName, description);
	    // Show the maps
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/searchMapResultView.fxml"));
        loader.setController(new searchMapResultController(maps, attraction, cityName, description));
        AnchorPane searchMapView = loader.load();
        mainLayout.getChildren().setAll(searchMapView);
	}

	public void goToCityInfo(String cityName) throws IOException
	{
	    // Get City Info
        City city = gcmClient.handleSearchCity(cityName);
        // Go to City Window
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/cityView.fxml"));
	    loader.setController(new cityController(city));
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
	
	public void goToTourInfo(int tourID) throws IOException
	{
	    // get Tour Info
	    Tour tour = gcmClient.handleGetTourInfoFromID(tourID);
	    // Go To Tour Info Windows
		FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/tourView.fxml"));
	    loader.setController(new tourController(tour));
        AnchorPane tourView = loader.load();
        mainLayout.getChildren().setAll(tourView);
	}

    public void goToMapInfo(int mapID, String attraction, String cityName, String description) throws IOException 
    {
        // Get map
        Map map = gcmClient.handleGetMapInfoFromID(mapID);
        // Go To show map info
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchmap/mapView.fxml"));
        loader.setController(new mapController(map, attraction, cityName, description));
        AnchorPane mapView = loader.load();
        mainLayout.getChildren().setAll(mapView);
    }

   	public void goToCityTours(String cityName) throws IOException {
   	    // Get Tours.
   	    ArrayList<Tour> tours = gcmClient.handleGetCityTours(cityName);
   	    // Go To Tours Windows.
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
	
	public void goToCompanyManagerServices() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("companyManager/companyManagerServicesView.fxml"));
        loader.setController(new companyManagerServicesController());
        AnchorPane companyManagerServicesView = loader.load();
        mainLayout.getChildren().setAll(companyManagerServicesView);
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

    public void goToRequestApproval(String errorMessage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gcmWorker/requestApprovalView.fxml"));
        loader.setController(new requestApprovalController(errorMessage));
        AnchorPane requestApprovalView = loader.load();
        mainLayout.getChildren().setAll(requestApprovalView);
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
    
    public void insertNewMap(Map map, ArrayList<Map> maps) throws IOException {
        gcmClient.handleInsertNewMap(map, maps);
    }
    
    public void checkCityExistance(String cityName, Map map, ArrayList<Map> maps) throws IOException{
        gcmClient.handleCheckCityExistance(cityName, map, maps);
    }

    public void getNewExternalMaps() throws IOException {
        gcmClient.handleGetNewExternalMaps();
    }
    
    public void goToExternalMaps(ArrayList<Map> maps, String successMessage, String errorMessage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("insertMap/externalMapsView.fxml"));
        loader.setController(new externalMapsController(maps, successMessage, errorMessage));
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
    
    public void removeAttractionFromTour(String attractionID, int tourID) throws IOException {
    	gcmClient.handleRemoveAttractionFromTour(attractionID, tourID);
    }
    
    public void removeTourFromCityTours(int tourID) throws IOException {
        gcmClient.handleRemoveTourFromCityTours(tourID);
    }
    
    public void goToAddAttractionToTour(String cityName, int tourID) throws IOException {
        // Get Attractions that we can add
        ArrayList<Attraction> attractions = gcmClient.handleGetAttractionsOfCity(cityName, tourID);
        // Go To Add Attractions to Tour Window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchMap/addAttractionToTourView.fxml"));
        loader.setController(new addAttractionToTourController(attractions, tourID, cityName));
        AnchorPane addAttractionToTourView = loader.load();
        mainLayout.getChildren().setAll(addAttractionToTourView);
    }
    
    public void goToInsertNewCity(ArrayList<Map> maps, String errorMessage, String successMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("insertMap/insertCityView.fxml"));
        loader.setController(new insertCityController(maps, errorMessage, successMessage));
        AnchorPane insertCityView = loader.load();
        mainLayout.getChildren().setAll(insertCityView);
    }

    public void insertNewCity(ArrayList<Map> maps, String cityName, String description) throws IOException {
        gcmClient.handleInsertNewCity(maps, cityName, description);
    }
    
    public void addAttractionToTour(String attractionID, int tourID, int time, String cityName) throws IOException {
        gcmClient.handleAddAttractionsToTour(attractionID, tourID, time, cityName);
    }
    
    public void updateDBAfterDecline(String cityName) throws IOException {
        gcmClient.handleUpdateDBAfterDecline(cityName);
    }

    public void updateDBAfterAccept(String cityName) throws IOException{
        gcmClient.handleUpdateDBAfterAccept(cityName);
    }

    public void goToPricesView(String oldSubsPrice, String oldOnePrice, String newSubsPrice, String newOnePrice) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("companyManager/pricesView.fxml"));
        loader.setController(new pricesController(oldSubsPrice, oldOnePrice, newSubsPrice, newOnePrice));
        AnchorPane pricesView = loader.load();
        mainLayout.getChildren().setAll(pricesView);
    }
    
    public void getPrices() throws IOException{
        gcmClient.handleGetPrices();
    }

    public void updatePricesAfterAccept(String newSubsPrice, String newOnePrice) throws IOException{
        gcmClient.handleUpdatePricesAfterAccept(newSubsPrice, newOnePrice);
    }

    public void updatePricesAfterDecline() throws IOException{
        gcmClient.handleUpdatePricesAfterDecline();
    }

    public void changePrices() throws IOException{
        gcmClient.handleChangePrices();
    }

    public void goToChangePrices(String oldSubsPrice, String oldOnePrice, String errorMessage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gcmManager/changePricesView.fxml"));
        loader.setController(new changePricesController(oldSubsPrice, oldOnePrice, errorMessage));
        AnchorPane addAttractionToTourView = loader.load();
        mainLayout.getChildren().setAll(addAttractionToTourView);
    }

    public void sendNewPrices(String newSubsPrice, String newOnePrice)throws IOException{
        gcmClient.handleSendNewPrices(newOnePrice, newSubsPrice);
    }

    public void goToAddTourToCity(String cityName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tours/AddTourToCityView.fxml"));
        loader.setController(new AddTourToCityController(cityName));
        AnchorPane addTourToCityView = loader.load();
        mainLayout.getChildren().setAll(addTourToCityView);
    }

    public void addTourToCity(String cityName, String description) throws IOException {
        gcmClient.handleAddTourToCity(cityName, description);
    }
    
    public void goToCityReport(String cityName, LocalDate fromDate, LocalDate toDate) throws IOException {
        Report report = gcmClient.handleGetCityReport(cityName, fromDate, toDate);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/cityReportView.fxml"));
        loader.setController(new cityReportController(report, cityName));
        AnchorPane cityReportView = loader.load();
        mainLayout.getChildren().setAll(cityReportView);
    }
    
    public void goToCitiesReport(String errorMessage) throws IOException {
        arrayOfStrings cities = gcmClient.handleGetCustomersCities();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/citiesAndDatesView.fxml"));
        loader.setController(new citiesAndDatesController(cities.getArrayList(), errorMessage));
        AnchorPane citiesAndDatesView = loader.load();
        mainLayout.getChildren().setAll(citiesAndDatesView);
    }
    
    public void goToSearchCustomer(String errorMessage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/searchCustomerView.fxml"));
        loader.setController(new searchCustomerController(errorMessage));
        AnchorPane searchCustomerView = loader.load();
        mainLayout.getChildren().setAll(searchCustomerView);
    }
    
    public void CheckCustomer(String username)throws IOException {
    	gcmClient.handleCheckCustomer(username);
    }
    
    public void goToCustomerReport(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/customerReportView.fxml"));
        loader.setController(new customerReportController(username));
        AnchorPane customerReportView = loader.load();
        mainLayout.getChildren().setAll(customerReportView);
    }
    
    
    public void goToPurchasesHistory(String customerUsername) throws IOException {
        
    	ArrayList<PurchaseDetails> purchases = gcmClient.handleGetPurchases(customerUsername);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/purchasesHistoryView.fxml"));
        loader.setController(new purchasesHistoryController(customerUsername, purchases));
        AnchorPane purchasesHistoryView = loader.load();
        mainLayout.getChildren().setAll(purchasesHistoryView);
    }
    
    public void goToViewsHistory(String customerUsername) throws IOException {
        
    	ArrayList<ViewDetails> views = gcmClient.handleGetViews(customerUsername);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/viewsHistoryView.fxml"));
        loader.setController(new viewsHistoryController(customerUsername, views));
        AnchorPane viewsHistoryView = loader.load();
        mainLayout.getChildren().setAll(viewsHistoryView);
    }
    
    public void goToDownloadsHistory(String customerUsername) throws IOException {
        
    	ArrayList<DownloadDetails> downloads = gcmClient.handleGetDownloads(customerUsername);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("reports/downloadsHistoryView.fxml"));
        loader.setController(new downloadsHistoryController(customerUsername, downloads));
        AnchorPane downloadsHistoryView = loader.load();
        mainLayout.getChildren().setAll(downloadsHistoryView);
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
