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
    public insertCityController(ArrayList<Map> maps, String errorMessage, String successMessage) {
        this.maps = maps;
        this.errorMessage = errorMessage;
        this.successMessage = successMessage;
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
        if(cityName.getText().isEmpty())
            Main.getInstance().goToInsertNewCity(maps,"Enter city name please!", "");
        else    
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
    }
}