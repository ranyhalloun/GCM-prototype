package application.searchmap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Map;
import Entities.SearchMapResult;
import application.Main;
import application.customer.customerServicesController;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


public class searchMapResultController {
    
    private SearchMapResult searchMapResult;
    
    public searchMapResultController(SearchMapResult searchMapResult)
    {
        this.searchMapResult = searchMapResult;
        System.out.println("Num of maps: " + Integer.toString(searchMapResult.getMaps().size()));
    }

    @FXML
    private TableColumn<Map, Integer> mapIDColumn;

    @FXML
    private TableColumn<Map, String> descriptionColumn;

    @FXML
    private Button backBtn;
    
    @FXML
    private Button displayMapBtn;

    @FXML
    private TableColumn<Map, String> cityColumn;

    @FXML
    private TableView<Map> tableView;

    
    @FXML
    void back(ActionEvent event) throws IOException {
        System.out.println("Back to SearchMap view");
        boolean searchMapByCity = searchMapResult.getSearchByCity();
        boolean searchMapByAttraction = searchMapResult.getSearchByAttraction();
        boolean searchMapByDescription = searchMapResult.getSearchByDescription();
        
        if(searchMapByCity&&!searchMapByAttraction&&!searchMapByDescription)
        	Main.getInstance().cityInfo(searchMapResult);
        else if(!searchMapByCity&&searchMapByAttraction&&!searchMapByDescription)
        	Main.getInstance().attractionInfo(searchMapResult);
        else
        	Main.getInstance().goToSearchMap("");
        	
    }
    
    @FXML
    void displayMap(ActionEvent event) throws IOException {
        Main.getInstance().goToMapInfo(tableView.getSelectionModel().getSelectedItem(), searchMapResult);
    }
    
    //This method will enable the displayMap button once a row in the table is selected
    @FXML
    public void userClickOnTable() {
        if(tableView.getSelectionModel().getSelectedItem()!=null)
            this.displayMapBtn.setDisable(false);
    }

    @FXML
    void initialize() {
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'searchMapResultView.fxml'.";
        mapIDColumn.setCellValueFactory(new PropertyValueFactory<Map, Integer>("mapID"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Map, String>("description"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<Map, String>("cityName"));
        
        tableView.setItems(getMaps());
        
        //Disable the displayMap button until a row is selected
        this.displayMapBtn.setDisable(true);

    }
    
    public ObservableList<Map> getMaps(){
        ObservableList<Map> maps = FXCollections.observableArrayList();
        for (Map map : searchMapResult.getMaps()) {
            maps.add(map);
        }
        return maps;
    }

}
