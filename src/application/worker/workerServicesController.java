package application.worker;


import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class workerServicesController {

    @FXML
    private Button logoutBtn;

    @FXML
    private Button searchMapBtn;

    @FXML
    private Button myProfileBtn;

    @FXML
    void searchMap(ActionEvent event) throws IOException {
        System.out.println("Logging out");
        Main.getInstance().goToSearchMap("");
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        System.out.println("Logging out");
        Main.getInstance().goToLogin("");
    }
    
    @FXML
    void myProfile(ActionEvent event) throws IOException {
    	System.out.println("Going to customer profile");
        Main.getInstance().goToMyProfile();
    }


}
