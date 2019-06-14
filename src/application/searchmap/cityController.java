package application.searchmap;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.City;
import Entities.Map;
import Entities.SearchMapResult;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class cityController {
    
    private City city;
    
    public cityController(City city)
    {
        System.out.println("City Controller");
        this.city = city;
    }
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Label cityName;
    
    @FXML
    private TextArea cityDescription;

    @FXML
    private Button cityToursBtn;

    @FXML
    private Button mapInfoBtn;

    @FXML
    private TextField numOfAttractions;

    @FXML
    private TextField numOfMaps;

    @FXML
    private TextField numOfTours;


    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToSearchMap("");
    }

    @FXML
    void cityTours(ActionEvent event) throws IOException {
    	Main.getInstance().goToCityTours(cityName.getText());
    }

    @FXML
    void mapInfo(ActionEvent event) throws IOException {
        Main.getInstance().goToMapsInfo("" ,city.getCityName(), "");
    }

    @FXML
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'cityView.fxml'.";
        assert cityDescription != null : "fx:id=\"cityDescription\" was not injected: check your FXML file 'cityView.fxml'.";
        assert cityName != null : "fx:id=\"cityName\" was not injected: check your FXML file 'cityView.fxml'.";
        assert cityToursBtn != null : "fx:id=\"cityToursBtn\" was not injected: check your FXML file 'cityView.fxml'.";
        assert mapInfoBtn != null : "fx:id=\"mapInfoBtn\" was not injected: check your FXML file 'cityView.fxml'.";
        assert numOfAttractions != null : "fx:id=\"numOfAttractions\" was not injected: check your FXML file 'cityView.fxml'.";
        assert numOfMaps != null : "fx:id=\"numOfMaps\" was not injected: check your FXML file 'cityView.fxml'.";
        assert numOfTours != null : "fx:id=\"numOfTours\" was not injected: check your FXML file 'cityView.fxml'.";

        numOfAttractions.setText(Integer.toString(city.getNumOfAttractions()));
        numOfTours.setText(Integer.toString(city.getNumOfTours()));
        numOfMaps.setText(Integer.toString(city.getNumOfMaps()));
        cityDescription.setText(city.getDescription());
        cityName.setText(city.getCityName());
        
        numOfAttractions.setEditable(false);
        numOfTours.setEditable(false);
        numOfMaps.setEditable(false);
        cityDescription.setEditable(false);
    }
}
