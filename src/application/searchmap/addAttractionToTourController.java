package application.searchmap;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Attraction;
import Entities.StringIntPair;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class addAttractionToTourController {
	
	private ArrayList<Attraction> attractions;
	private int tourID;
	private String errorMessage;
	private String cityName;
	
	public addAttractionToTourController(ArrayList<Attraction> attractions, int tourID, String cityName)
	{
		this.attractions = attractions;
		this.tourID = tourID;
		this.cityName = cityName;
	}

    @FXML
    private TableColumn<Attraction, String> nameColumn;

    @FXML
    private Button addBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<Attraction, Integer> accessibleColumn;

    @FXML
    private TableView<Attraction> attractionsTable;

    @FXML
    private TableColumn<Attraction, String> categoryColumn;

    @FXML
    private TextArea descriptionFT;

    @FXML
    private TextField timeFT;
    
    @FXML
    private Label errorText;

    @FXML
    void userClickOnTable() {
    	Attraction attraction = attractionsTable.getSelectionModel().getSelectedItem();
    	if(attraction != null) {
            addBtn.setDisable(false);
            descriptionFT.setText(attraction.getDescription());
            timeFT.setEditable(true);
    	}
    }

    @FXML
    void add(ActionEvent event) throws IOException {
    	if (timeFT.getText().isEmpty()) {
    		System.out.println("Enter time");
    	}
    	else {
        	Attraction attraction = attractionsTable.getSelectionModel().getSelectedItem();
        	Main.getInstance().addAttractionToTour(attraction.getId(), tourID, Integer.parseInt(timeFT.getText()), cityName);
        	Main.getInstance().goToAddAttractionToTour(cityName, tourID);
    	}
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToTourInfo(tourID);
    }

    @FXML
    void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("category"));
        accessibleColumn.setCellValueFactory(new PropertyValueFactory<Attraction, Integer>("isAccessible"));
        
        attractionsTable.setItems(getAttractions());
        addBtn.setDisable(true);
        timeFT.setEditable(false);
        errorText.setText(errorMessage);
        
    }
    
    
    public ObservableList<Attraction> getAttractions(){
        ObservableList<Attraction> attractions = FXCollections.observableArrayList();
        for (Attraction attr : this.attractions) {
            attractions.add(attr);
        }
        return attractions;
    }

}
