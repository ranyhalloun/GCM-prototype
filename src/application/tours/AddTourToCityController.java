package application.tours;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class AddTourToCityController {
    
    private String tourCityName;
    
    public AddTourToCityController(String tourCityName)
    {
        System.out.println("AddTourToCityController");
        this.tourCityName = tourCityName;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField cityName;

    @FXML
    private TextField description;
    
    @FXML
    private Button insertTourBtn;


    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToCityTours(tourCityName);
    }

    @FXML
    void insertTour(ActionEvent event) throws IOException {
        System.out.println("Inserting tour..");
        Main.getInstance().addTourToCity(cityName.getText(), description.getText());
        System.out.println("Inserting tour..");
    }

    @FXML
    void initialize() {
        assert cityName != null : "fx:id=\"cityName\" was not injected: check your FXML file 'AddTourToCityView.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'AddTourToCityView.fxml'.";
        
        cityName.setText(tourCityName);
        

    }

}
