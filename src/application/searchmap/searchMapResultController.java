package application.searchmap;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Map;
import Entities.SearchMapResult;
import application.Main;
import application.customer.customerServicesController;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class searchMapResultController {
    
    private String attraction;
    private String cityName;
    private String description;
    private ArrayList<Map> maps;
    private String date; 
    
    public searchMapResultController(ArrayList<Map> maps, String attraction, String cityName, String description, String date)
    {
        this.maps = maps;
        System.out.println("Num of maps: " + maps.size());
        this.attraction = attraction;
        this.cityName = cityName;
        this.description = description;
        this.date = date;
    }

    @FXML
    private TableColumn<Map, Integer> mapIDColumn;

    @FXML
    private Button backBtn;
    
    @FXML
    private Button displayMapBtn;

    @FXML
    private TableColumn<Map, String> cityColumn;

    @FXML
    private TableView<Map> tableView;
    
    @FXML
    private TextArea mapDescription;
    
    @FXML
    private TextField numOfMaps;
    
    @FXML
    private TextField expirationDate;
    
    @FXML
    private Label expirationDateLabel;
    
    @FXML
    void back(ActionEvent event) throws IOException {
        System.out.println("Back..");
        boolean searchMapByCity = !this.cityName.isEmpty();
        boolean searchMapByAttraction = !this.attraction.isEmpty();
        boolean searchMapByDescription = !this.description.isEmpty();
        
        // City Name only, go to City Info..
        if(searchMapByCity&&!searchMapByAttraction&&!searchMapByDescription)
        	Main.getInstance().goToCityInfo(this.cityName);
        // Else, go to search Windows..
        else {
        	Main.getInstance().goToSearchMap("");
        }
    }
    
    @FXML
    void displayMapInfo(ActionEvent event) throws IOException {
        Main.getInstance().goToMapInfo(tableView.getSelectionModel().getSelectedItem().getMapID(), attraction, cityName, description);
    }
    
    //This method will enable the displayMap button once a row in the table is selected
    @FXML
    public void userClickOnTable() {
        if(tableView.getSelectionModel().getSelectedItem()!=null) {
            this.displayMapBtn.setDisable(false);
            this.mapDescription.setText(tableView.getSelectionModel().getSelectedItem().getDescription());
        }
    }

    @FXML
    void initialize() {
        mapIDColumn.setCellValueFactory(new PropertyValueFactory<Map, Integer>("mapID"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<Map, String>("cityName"));
        
        tableView.setItems(getMaps());
        this.numOfMaps.setText(Integer.toString(this.maps.size()));
        //Disable the displayMap button until a row is selected
        this.displayMapBtn.setDisable(true);
        expirationDate.setText(date);
        expirationDate.setEditable(false);
        
    	switch(Main.getInstance().getUserType()) {
		case Customer:
			expirationDate.setVisible(true);
			expirationDateLabel.setVisible(true);
			break;
		default:
			expirationDate.setVisible(false);
			expirationDateLabel.setVisible(false);
			break;
    	}
    }
    
    public ObservableList<Map> getMaps(){
        ObservableList<Map> maps = FXCollections.observableArrayList();
        for (Map map : this.maps) {
            maps.add(map);
            System.out.println("Added map: " + map.getMapID());
        }
        return maps;
    }

}
