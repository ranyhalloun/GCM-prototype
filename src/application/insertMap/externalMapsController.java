package application.insertMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Entities.Map;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class externalMapsController {

	
		private ArrayList<Map> maps;
		private String successMessage;
		private String errorMessage;
		
		public externalMapsController(ArrayList<Map> maps, String successMessage, String errorMessage) {
			this.maps = maps;
			this.successMessage = successMessage;
			this.errorMessage = errorMessage;
		}
		
	    @FXML
	    private Label successText;
	    
	    @FXML
	    private Label errorText;
	    
	    @FXML
	    private Button importBtn;

	    @FXML
	    private Button backBtn;

	    @FXML
	    private Button insertCityBtn;

	    @FXML
	    private TableColumn<Map, Integer> mapIDColumn;

	    @FXML
	    private TableView<Map> tableView;

	    @FXML
	    private TextArea descriptionFT;

	    @FXML
	    private TableColumn<Map, String> cityNameColumn;
	    
	    @FXML
	    public void userClickOnTable() {
	        if(tableView.getSelectionModel().getSelectedItem()!=null)
	        { 
	        	descriptionFT.setText(tableView.getSelectionModel().getSelectedItem().getDescription());
	        	this.importBtn.setDisable(false);
	        	
	        }
	    }

	    @FXML
	    void back(ActionEvent event) throws IOException {
	    	System.out.println("Going to previous view");
	    	switch(Main.getInstance().getUserType()) {
	    		case GCMWorker:
	    			Main.getInstance().goToGCMWorkerServices();
	    			break;
	            case GCMManager:
	                Main.getInstance().goToGCMManagerServices();
	                break;
	            case CompanyManager:
	                Main.getInstance().goToCompanyManagerServices();
	                break;
	    	}
	    }

        @FXML
        void importAction(ActionEvent event) throws IOException {
        	Map map = tableView.getSelectionModel().getSelectedItem();
            Main.getInstance().checkCityExistance(map.getCityName(), map, maps);
        }
        
        @FXML
        void insertCity(ActionEvent event) throws IOException {
        	Main.getInstance().goToInsertNewCity(maps, "", "");
        }

        @FXML
        void initialize() {
            mapIDColumn.setCellValueFactory(new PropertyValueFactory<Map, Integer>("mapID"));
            cityNameColumn.setCellValueFactory(new PropertyValueFactory<Map, String>("cityName"));
            System.out.println("Size = " + maps.size());
            tableView.setItems(getMaps());
            
            this.importBtn.setDisable(true);
            successText.setText(successMessage);
            errorText.setText(errorMessage);

        }
        
        public ObservableList<Map> getMaps(){
            ObservableList<Map> maps = FXCollections.observableArrayList();
            for (Map map : this.maps) {
                maps.add(map);
            }
            return maps;
        }
}




