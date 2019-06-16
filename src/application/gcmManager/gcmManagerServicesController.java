package application.gcmManager;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class gcmManagerServicesController {

	private String message;
	public gcmManagerServicesController(String message) {
		this.message = message;
	}
	
    @FXML
    private Button insertMapBtn;

    @FXML
    private Button searchMapBtn;

    @FXML
    private Button changePricesBtn;
    
    @FXML
    private Button logOutBtn;

    @FXML
    private Button checkRequestsBtn;


    @FXML
    private Button citiesReportsBtn;
    
    @FXML
    private Button customersReportsBtn;
    
    @FXML
    private Button myProfileBtn;
    
    @FXML
    private Label messageText;
    
    @FXML
    void customersReports(ActionEvent event) throws IOException {
    	System.out.println("Going to search customer");
        Main.getInstance().goToSearchCustomer("");
    }
    
    @FXML
    void searchMap(ActionEvent event) throws IOException {
        System.out.println("Going to search map");
        Main.getInstance().goToSearchMap("");
    }

    @FXML
    void insertMap(ActionEvent event) throws IOException {
        System.out.println("Going to insert map");
        Main.getInstance().getNewExternalMaps();
    }

    @FXML
    void checkRequests(ActionEvent event) throws IOException {
        System.out.println("Going to check requests");
        Main.getInstance().goToCheckRequests();
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        System.out.println("Logging out");
        Main.getInstance().goToLogin("");
    }
    
    @FXML
    void changePrices(ActionEvent event) throws IOException {
        System.out.println("Going to change prices");
        Main.getInstance().changePrices();
    }


    @FXML
    void citiesReports(ActionEvent event) throws IOException {
    	 System.out.println("Going to cities reports");
         Main.getInstance().goToCitiesReport("");
    }

    @FXML
    void myProfile(ActionEvent event) throws IOException {
    	Main.getInstance().goToMyProfile();
    }
    
    @FXML
    void initialize() {
        assert insertMapBtn != null : "fx:id=\"insertMapBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
        assert logOutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
        assert checkRequestsBtn != null : "fx:id=\"checkRequestsBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
        assert searchMapBtn != null : "fx:id=\"searchMapBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
        messageText.setText(message);
    }
}