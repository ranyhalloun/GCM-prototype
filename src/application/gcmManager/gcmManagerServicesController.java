package application.gcmManager;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class gcmManagerServicesController {

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
    void initialize() {
        assert insertMapBtn != null : "fx:id=\"insertMapBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
        assert logOutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
        assert checkRequestsBtn != null : "fx:id=\"checkRequestsBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
        assert searchMapBtn != null : "fx:id=\"searchMapBtn\" was not injected: check your FXML file 'gcmManagerView.fxml'.";
    }
}