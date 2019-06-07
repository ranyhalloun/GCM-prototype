package application.searchmap;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class searchMapResultController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView mapCategoryListView;

    @FXML
    private ListView mapShortContent;

    @FXML
    private ListView mapsListView;


    @FXML
    void initialize() {
        assert mapCategoryListView != null : "fx:id=\"mapCategoryListView\" was not injected: check your FXML file 'searchMapResultView.fxml'.";
        assert mapShortContent != null : "fx:id=\"mapShortContent\" was not injected: check your FXML file 'searchMapResultView.fxml'.";
        assert mapsListView != null : "fx:id=\"mapsListView\" was not injected: check your FXML file 'searchMapResultView.fxml'.";
        
        System.out.println("inside initialize()");
        
        mapCategoryListView.getItems().addAll("City name", "Attractions", "Description");
        mapCategoryListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

}
