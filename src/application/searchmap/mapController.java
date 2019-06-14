package application.searchmap;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Attraction;
import Entities.Map;
import Entities.SearchMapResult;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class mapController {
    
    private String attraction;
    private String cityName;
    private String description;
    private Map map;
    
    public mapController(Map map, String attraction, String cityName, String description)
    {
        this.map = map;
        this.attraction = attraction;
        this.cityName = cityName;
        this.description = description;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Attraction> attractionsTable;
    
    @FXML
    private TableColumn<Attraction, String> attractionDescriptionColumn;

    @FXML
    private TableColumn<Attraction, String> attractionNameColumn;

    @FXML
    private TextField mapCityName;

    @FXML
    private TextArea mapDescription;

    @FXML
    private TextField mapID;

    @FXML
    private TextField version;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Button displayMapBtn;
    
    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToMapsInfo(attraction, cityName, description);
    }
    
    @FXML
    void displayMap(ActionEvent event) {
//        Main.getInstance().displayImage(map)
    }
    
    @FXML
    void initialize() {
        assert attractionsTable != null : "fx:id=\"attractionsTable\" was not injected: check your FXML file 'mapView.fxml'.";
        assert mapCityName != null : "fx:id=\"mapCityName\" was not injected: check your FXML file 'mapView.fxml'.";
        assert mapDescription != null : "fx:id=\"mapDescription\" was not injected: check your FXML file 'mapView.fxml'.";
        assert mapID != null : "fx:id=\"mapID\" was not injected: check your FXML file 'mapView.fxml'.";
        assert version != null : "fx:id=\"version\" was not injected: check your FXML file 'mapView.fxml'.";
        
        attractionNameColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
        attractionDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("description"));
        
        attractionsTable.setItems(getAttractions());
        mapCityName.setText(map.getCityName());
        version.setText(Integer.toString(map.getVersion()));
        mapID.setText(Integer.toString(map.getMapID()));
        mapDescription.setText(map.getDescription());
    }
    
    
    public ObservableList<Attraction> getAttractions(){
        ObservableList<Attraction> attractions = FXCollections.observableArrayList();
        for (Attraction attr : map.getAttractions()) {
            attractions.add(attr);
        }
        return attractions;
    }

}
