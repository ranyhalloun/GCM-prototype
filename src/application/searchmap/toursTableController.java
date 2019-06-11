package application.searchmap;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Attraction;
import Entities.Tour;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class toursTableController {

	private String cityName;
	private ArrayList<Tour> tours;
	public toursTableController(ArrayList<Tour> tours, String cityName)
	{
		this.cityName = cityName;
		this.tours = tours;
	}
	
    @FXML
    private Button tourInfoBtn;

    @FXML
    private Button removeTourBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Tour> toursTable;

    @FXML
    private TableColumn<Tour, Integer> tourIDColumn;

    @FXML
    private Button addTourBtn;

    @FXML
    private TableColumn<Tour, String> descriptionColumn;
    
    @FXML
    private Label title;
    
    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void userClickOnTable() {
        if(toursTable.getSelectionModel().getSelectedItem()!=null)
        { 
        	this.tourInfoBtn.setDisable(false);
        	this.removeTourBtn.setDisable(false);
        }
    }
    @FXML
    void tourInfo(ActionEvent event) throws IOException {
    	Main.getInstance().goToTourInfo(toursTable.getSelectionModel().getSelectedItem().getAttractionsName(), cityName, tours, toursTable.getSelectionModel().getSelectedItem().getId());

    }

    @FXML
    void addTour(ActionEvent event) {

    }

    @FXML
    void removeTour(ActionEvent event) {

    }
    @FXML
    void initialize() {
    	tourIDColumn.setCellValueFactory(new PropertyValueFactory<Tour, Integer>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Tour, String>("description"));
        
        toursTable.setItems(getTours());
        title.setText(cityName + "'s Tours");
        this.tourInfoBtn.setDisable(true);
        this.removeTourBtn.setDisable(true);
    }
    
    public ObservableList<Tour> getTours(){
        ObservableList<Tour> tours= FXCollections.observableArrayList();
        for (Tour tour : this.tours) {
            tours.add(tour);
        }
        return tours;
    }
    
}
