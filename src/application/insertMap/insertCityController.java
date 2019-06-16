package application.insertMap;

import java.io.IOException;
import java.util.ArrayList;

import Entities.Map;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class insertCityController {

    private ArrayList<Map> maps;
    private String errorMessage;
    private String successMessage;
    private String city;
    public insertCityController(ArrayList<Map> maps, String errorMessage, String successMessage, String city) {
        this.maps = maps;
        this.errorMessage = errorMessage;
        this.successMessage = successMessage;
        this.city = city;
    }

    @FXML
    private Label errorText;

    @FXML
    private Label successText;

    @FXML
    private Button createBtn;

    @FXML
    private TextArea descriptionFT;

    @FXML
    private TextField cityName;

    @FXML
    private Button backBtn;

    @FXML
    void create(ActionEvent event) throws IOException {
        Main.getInstance().insertNewCity(maps, cityName.getText(), descriptionFT.getText());
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToExternalMaps(maps, "", "");
    }

    @FXML
    void initialize() {
        errorText.setText(errorMessage);
        successText.setText(successMessage);
        cityName.setText(city);
        cityName.setEditable(false);
    }
}