package application.companyManager;
import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class companyManagerServicesController {

	private String messageText;
	public companyManagerServicesController(String messageText) {
		this.messageText = messageText;
	}
	
    @FXML
    private Button logoutBtn;

    @FXML
    private Button insertMapBtn;

    @FXML
    private Button searchMapBtn;

    @FXML
    private Button checkNewPricesBtn;
    

    @FXML
    private Button citiesReportsBtn;

    @FXML
    private Button customersReportsBtn;
    
    @FXML
    private Button myProfileBtn;

    @FXML
    private Label message;
    
    @FXML
    void logout(ActionEvent event) throws IOException {
        System.out.println("Logging out");
        Main.getInstance().goToLogin("");
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
    void checkNewPrices(ActionEvent event) throws IOException {
        System.out.println("Going to check new prices");
        Main.getInstance().getPrices();
    }
    

    @FXML
    void citiesReports(ActionEvent event) throws IOException {
        System.out.println("Going to cities reports");
        Main.getInstance().goToCitiesReport("");
    }
    
    @FXML
    void customersReports(ActionEvent event) throws IOException {
    	System.out.println("Going to search customer");
        Main.getInstance().goToSearchCustomer("");
    }
    
    @FXML
    void myProfile(ActionEvent event) throws IOException {
    	Main.getInstance().goToMyProfile();
    }
    
    @FXML
    void initialize() {
    	message.setText(messageText);
    }

}