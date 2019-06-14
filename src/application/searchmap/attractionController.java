package application.searchmap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.SearchMapResult;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;


public class attractionController {
    
    private SearchMapResult searchMapResult;

    public attractionController(SearchMapResult searchMapResult)
    {
        this.searchMapResult = searchMapResult;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<String> attractionsTableView;

    @FXML
    private Button back;

    @FXML
    private TextArea description;

    @FXML
    private Button goToMapsBtn;

    @FXML
    private TableColumn<String, String> nameColumn;

    @FXML
    private TextArea numOfMapIncludeAttraction;


    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToSearchMap("");
    }

    @FXML
    void goToMaps(ActionEvent event) {
    }

    @FXML
    void userClickOnTable(MouseEvent event) {
    }

    @FXML
    void initialize() {
        assert attractionsTableView != null : "fx:id=\"attractionsTableView\" was not injected: check your FXML file 'attractionView.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'attractionView.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'attractionView.fxml'.";
        assert goToMapsBtn != null : "fx:id=\"goToMapsBtn\" was not injected: check your FXML file 'attractionView.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'attractionView.fxml'.";
        assert numOfMapIncludeAttraction != null : "fx:id=\"numOfMapIncludeAttraction\" was not injected: check your FXML file 'attractionView.fxml'.";
        
        description.setEditable(false);
        
//        ObservableList<String> tempCities = FXCollections.observableArrayList(searchMapResult.getCitiesNameOfAttraction());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
//        attractionsTableView.setItems(tempCities);
    }
}


