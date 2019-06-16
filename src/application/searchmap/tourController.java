package application.searchmap;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Attraction;
import Entities.AttractionTimePair;
import Entities.Map;
import Entities.StringIntPair;
import Entities.Tour;
import Users.UserType;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class tourController {
	
	private int tourID;
	private String cityName;
	private ArrayList<AttractionTimePair> attractionTimePairs;
	private UserType userType;
	
	public tourController(Tour tour, UserType userType) {
		this.attractionTimePairs = tour.getAttractionsTimePair();
		this.cityName = tour.getCityName();
		this.tourID = tour.getId();
		this.userType = userType;
	}

    @FXML
    private TableColumn<AttractionTimePair, Integer> timeColumn;

    @FXML
    private TableView<AttractionTimePair> tourTable;

    @FXML
    private Button removeBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<AttractionTimePair, String> attractionNameColumn;

    @FXML
    private Button addBtn;
    
    @FXML
    private TextArea attractionDescription;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToCityTours(cityName);
    }

    @FXML
    void userClickOnTable() {
    	if(tourTable.getSelectionModel().getSelectedItem() != null) {
    		removeBtn.setDisable(false);
    		attractionDescription.setText(tourTable.getSelectionModel().getSelectedItem().getAttraction().getDescription());
    	}
    }

    @FXML
    void add(ActionEvent event) throws IOException {
    	Main.getInstance().goToAddAttractionToTour(cityName, tourID);
    }

    @FXML
    void remove(ActionEvent event) throws IOException {
    	
        AttractionTimePair attractionInTour = tourTable.getSelectionModel().getSelectedItem();
    	Main.getInstance().removeAttractionFromTour(attractionInTour.getAttraction().getId(), tourID);
    	Main.getInstance().goToTourInfo(tourID);
    }

    @FXML
    void initialize() {
    	attractionNameColumn.setCellValueFactory(new PropertyValueFactory<AttractionTimePair, String>("attractionName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<AttractionTimePair, Integer>("time"));
        
        tourTable.setItems(getAttractionTimePairs());
        this.removeBtn.setDisable(true);
        
        switch (userType) {
        case Anonymous:
        case Customer:
        case Worker:
            this.addBtn.setVisible(false);
            this.removeBtn.setVisible(false);
            break;
        default:
            break;
        }
    }
    
    public ObservableList<AttractionTimePair> getAttractionTimePairs() {
        ObservableList<AttractionTimePair> attractionsInTour = FXCollections.observableArrayList();
        for (AttractionTimePair entry : this.attractionTimePairs) {
            attractionsInTour.add(entry);
        }
        return attractionsInTour;
    }
}
