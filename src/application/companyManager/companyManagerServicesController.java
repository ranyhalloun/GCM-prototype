package application.companyManager;
import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class companyManagerServicesController {

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

}