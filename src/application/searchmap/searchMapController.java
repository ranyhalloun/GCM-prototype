package application.searchmap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Users.UserType;

public class searchMapController {

	private String errorMessage;
	public searchMapController(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField attraction;

    @FXML
    private TextField cityName;

    @FXML
    private TextField description;

    @FXML
    private Button searchBtn;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Label errorMessageText;
    
    @FXML
    void searchMap(ActionEvent event) throws IOException {
    	if(attraction.getText().isEmpty() && cityName.getText().isEmpty() && description.getText().isEmpty())
    		Main.getInstance().goToSearchMap("You have to fill at least one field!");
    	else {
    	    //Search By City Name only -> Show City Info.
    	    if (attraction.getText().isEmpty() && !cityName.getText().isEmpty() && description.getText().isEmpty()) {
    	        Main.getInstance().goToCityInfo(cityName.getText());
    	    }
            //Search By other combination -> Show Maps Info.
    	    else {
    	        Main.getInstance().goToMapsInfo(attraction.getText(), cityName.getText(), description.getText());
    	    }
    	}
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	System.out.println("Back to login view");
    	switch(Main.getInstance().getUserType()) {
    		case Anonymous:
    		case Worker:
    			Main.getInstance().goToLogin("");
    			break;
    		case Customer:
    			Main.getInstance().goToCostumerServices();
    			break;
    		case GCMWorker:
    			Main.getInstance().goToGCMWorkerServices();
    			break;
            case GCMManager:
                Main.getInstance().goToGCMManagerServices();
                break;
            case CompanyManager:
                Main.getInstance().goToCompanyManagerServices();
                break;
            	
    	}
    }
    
    @FXML
    void initialize() {
        assert attraction != null : "fx:id=\"attraction\" was not injected: check your FXML file 'searchMapView.fxml'.";
        assert cityName != null : "fx:id=\"cityName\" was not injected: check your FXML file 'searchMapView.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'searchMapView.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'searchMapView.fxml'.";
        
        errorMessageText.setText(errorMessage);
    }

}
