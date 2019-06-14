package application.gcmWorker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class gcmWorkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button insertMapBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button requestApprovalBtn;

    @FXML
    private Button searchMapBtn;


    @FXML
    void goToInsertMap(ActionEvent event) throws IOException {
        System.out.println("Go to insert map");
        Main.getInstance().getNewExternalMaps();
    }

    @FXML
    void goToSearchMap(ActionEvent event) throws IOException {
        System.out.println("Going to search map");
        Main.getInstance().goToSearchMap("");
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        System.out.println("Logging out");
        Main.getInstance().goToLogin("");
    }

    @FXML
    void requestApproval(ActionEvent event) throws IOException {
        System.out.println("Going to requestApproval");
        Main.getInstance().goToRequestApproval("");
    }

    @FXML
    void initialize() {
        assert insertMapBtn != null : "fx:id=\"insertMapBtn\" was not injected: check your FXML file 'gcmWorkerView.fxml'.";
        assert logoutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'gcmWorkerView.fxml'.";
        assert requestApprovalBtn != null : "fx:id=\"requestApprovalBtn\" was not injected: check your FXML file 'gcmWorkerView.fxml'.";
        assert searchMapBtn != null : "fx:id=\"searchMapBtn\" was not injected: check your FXML file 'gcmWorkerView.fxml'.";
    }

}
