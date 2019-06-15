package application.customer;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class customerServicesController {

    @FXML
    private Button searchMapBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button myProfileBtn;

    @FXML
    private Button newVersionsBtn;
    
    @FXML
    void logOut(ActionEvent event) throws IOException {
        System.out.println("Logging out");
        Main.getInstance().goToLogin("");
    }

    @FXML
    void searchMap(ActionEvent event) throws IOException {
        System.out.println("Going to search map");
        Main.getInstance().goToSearchMap("");
    }

    @FXML
    void myProfile(ActionEvent event) throws IOException {
    	System.out.println("Going to customer profile");
        Main.getInstance().goToMyProfile();
    }
    
    @FXML
    void newVersions(ActionEvent event) throws IOException {
    	Main.getInstance().goToNewVersions();
    }

}
