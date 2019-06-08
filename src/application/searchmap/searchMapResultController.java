package application.searchmap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Map;
import application.Main;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


public class searchMapResultController {

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
        Main.getInstance().goToSearchMap();
    }
    
    @FXML
    void displayMap(ActionEvent event) throws IOException {
        //Main.getInstance().goToMapImage(tableView.getSelectionModel().getSelectedItem());      
    }
    
    //This method will enable the displayMap button once a row in the table is selected
    @FXML
    public void userClickOnTable() {
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
        maps.add(new Map(1,"Best city", "Haifa", "https://www.google.com/maps/vt/data=mCYx3nrsT4at7jFwH_pM1-tcK0Xw0BWrMxNJ62AwCJEYfitBviFUgSbP42GZN7-T1vv7mJVjpcfZJNTj4ZezvbVYFQcu8msFd1zyCIi79BZXXF4seZ7Kcyi4gBLCCRnZyK5sL6llvyPorSL-Pixyk6_-KYEHiMcgWe2eQTbwlnnBYh8g5gY81HS73kDLGx6Pr9Eb7R_F50jQGnIAyn0VyNnNYwYkP9E"));
        maps.add(new Map(2,"Nighty city", "Tel Aviv","https://www.google.com/maps/vt/data=nfhY5fd0yhDxcSp6mZys4XtGwT7hOaJDJt5btBtE25N3ktViJYOtijsl1nFMNdtsTtzKOclf3Gm6dDQB3h6LYLNi20sFTRyRS9RXHPE40CqL215LqPKfS6fYTZUI4e48_mw67X3BJAF_iqkGDAyy929gjiZ22uMFm389wytR25JgDIFsF6h_m78i8mkIiRquNI4k5X3jcSVXXiTGfWIVXl1Ej6y8gzI"));
        
        return maps;
    }

}
