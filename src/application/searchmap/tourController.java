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
	
	private int tourID;
	private String cityName;
	private ArrayList<Tour> tours;
	private ArrayList<StringIntPair> attractionsTime;
	
	public tourController(ArrayList<StringIntPair> attractionsTime, String cityName, ArrayList<Tour> tours, int tourID) {
		this.attractionsTime = attractionsTime;
		this.cityName = cityName;
		this.tours = tours;
		this.tourID = tourID;
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
    void add(ActionEvent event) throws IOException {
    	Main.getInstance().getAttractionsOfCity(cityName);
    }

    @FXML
    void remove(ActionEvent event) throws IOException {
    	
    	StringIntPair attractionInTour = tourTable.getSelectionModel().getSelectedItem();
    	tourTable.getItems().remove((attractionInTour));
    	for(Tour tour : tours)
    	{
    		if(tour.getId() == tourID)
    		{
    			tour.getAttractionsName().remove(attractionInTour);
    			if(tour.getAttractionsName().size() == 0)
    				tours.remove(tour);
    			break;
    		}
    	}
    	Main.getInstance().removeAttractionFromTour(attractionInTour.getString_field(), tourID);
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
