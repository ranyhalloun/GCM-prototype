package application.searchmap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class searchMapController {

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
    void searchMap(ActionEvent event) throws IOException {
        Main.getInstance().searchMap(attraction.getText(), cityName.getText(), description.getText());
    }

    @FXML
    void initialize() {
        assert attraction != null : "fx:id=\"attraction\" was not injected: check your FXML file 'searchMapView.fxml'.";
        assert cityName != null : "fx:id=\"cityName\" was not injected: check your FXML file 'searchMapView.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'searchMapView.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'searchMapView.fxml'.";


    }

}
