package application.searchmap;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Map;
import Entities.StringIntPair;
import Entities.Tour;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class tourController {

	private String cityName;
	private ArrayList<Tour> tours;
	private ArrayList<StringIntPair> attractionsTime;
	
	public tourController(ArrayList<StringIntPair> attractionsTime, String cityName, ArrayList<Tour> tours) {
		this.attractionsTime = attractionsTime;
		this.cityName = cityName;
		this.tours = tours;
	}
    @FXML
    private TableColumn<StringIntPair, Integer> timeColumn;

    @FXML
    private TableView<StringIntPair> tourTable;

    @FXML
    private Button removeBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<StringIntPair, String> attractionNameColumn;

    @FXML
    private Button addBtn;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToCityToursTable(tours, cityName);
    }

    @FXML
    void userClickOnTable() {
    	if(tourTable.getSelectionModel().getSelectedItem() != null)
    		removeBtn.setDisable(false);
    }

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {
    	Main.getInstance().removeAttractionFromTour(tourTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    void initialize() {
    	attractionNameColumn.setCellValueFactory(new PropertyValueFactory<StringIntPair, String>("string_field"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<StringIntPair, Integer>("int_field"));
        
        tourTable.setItems(getAttractionsTime());
        this.removeBtn.setDisable(true);
    }
    
    public ObservableList<StringIntPair> getAttractionsTime(){
        ObservableList<StringIntPair> attractionsTime = FXCollections.observableArrayList();
        for (StringIntPair entry : this.attractionsTime) {
            attractionsTime.add(entry);
        }
        return attractionsTime;
    }
}
