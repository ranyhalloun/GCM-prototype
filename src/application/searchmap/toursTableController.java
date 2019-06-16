package application.searchmap;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Attraction;
import Entities.Tour;
import Users.UserType;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;


public class toursTableController {

	private String cityName;
	private ArrayList<Tour> tours;
	private UserType userType;
	
	public toursTableController(ArrayList<Tour> tours, String cityName, UserType userType)
	{
		this.cityName = cityName;
		this.tours = tours;
		this.userType = userType;
		System.out.println("Number of Tours:" + tours.size());
		for (Tour tour : tours) {
		    tour.print();
		}
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
    private TableColumn<Tour, Integer>  numAttractionsColumn;

    @FXML
    private Label title;

    @FXML
    private TextArea tourDescription;

    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToCityInfo(cityName);
    }

    @FXML
    void userClickOnTable() {
        if(toursTable.getSelectionModel().getSelectedItem()!=null)
        {
        	this.tourInfoBtn.setDisable(false);
        	this.removeTourBtn.setDisable(false);
        	tourDescription.setText(toursTable.getSelectionModel().getSelectedItem().getDescription());
        }
    }

    @FXML
    void tourInfo(ActionEvent event) throws IOException {
    	Main.getInstance().goToTourInfo(toursTable.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    void addTour(ActionEvent event) throws IOException {
        Main.getInstance().goToAddTourToCity(cityName);
    }

    @FXML
    void removeTour(ActionEvent event) throws IOException {
        Main.getInstance().removeTourFromCityTours(toursTable.getSelectionModel().getSelectedItem().getId());
        Main.getInstance().goToCityTours(cityName);
    }
    @FXML
    void initialize() {
    	tourIDColumn.setCellValueFactory(new PropertyValueFactory<Tour, Integer>("id"));
    	numAttractionsColumn.setCellValueFactory(new PropertyValueFactory<Tour, Integer>("numOfAttractions"));
        
        toursTable.setItems(getTours());
        title.setText(cityName + "'s Tours");
        this.tourInfoBtn.setDisable(true);
        this.removeTourBtn.setDisable(true);
        
        switch (userType) {
        case Anonymous:
        case Customer:
        case Worker:
            this.addTourBtn.setVisible(false);
            this.removeTourBtn.setVisible(false);
            break;
        default:
            break;
        }
    }
    
    public ObservableList<Tour> getTours(){
        ObservableList<Tour> tours= FXCollections.observableArrayList();
        for (Tour tour : this.tours) {
            tours.add(tour);
        }
        return tours;
    }
    
}
