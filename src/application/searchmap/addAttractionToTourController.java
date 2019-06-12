package application.searchmap;
import java.util.ArrayList;

import Entities.Attraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class addAttractionToTourController {
	
	private ArrayList<Attraction> attractions;
	
	public addAttractionToTourController(ArrayList<Attraction> attractions)
	{
		this.attractions = attractions;
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
    void userClickOnTable() {
    	Attraction attraction = attractionsTable.getSelectionModel().getSelectedItem();
    	if(attraction != null) {
            addBtn.setDisable(false);
            descriptionFT.setText(attraction.getDescription());
    	}
    }

    @FXML
    void add(ActionEvent event) {
    	
    }
    
    @FXML
    void back(ActionEvent event) {
    }
    
    @FXML
    void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Attraction, String>("category"));
        accessibleColumn.setCellValueFactory(new PropertyValueFactory<Attraction, Integer>("isAccessible"));
        
        attractionsTable.setItems(getAttractions());
        addBtn.setDisable(true);
    }
    
    
    public ObservableList<Attraction> getAttractions(){
        ObservableList<Attraction> attractions = FXCollections.observableArrayList();
        for (Attraction attr : this.attractions) {
            attractions.add(attr);
        }
        return attractions;
    }

}
