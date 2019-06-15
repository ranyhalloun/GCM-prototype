package application.gcmWorker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Entities.PurchaseDetails;
import Entities.updateCityRequest;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class managerRequestNotifiController {

	private ArrayList<updateCityRequest> notifis;
	public managerRequestNotifiController(ArrayList<updateCityRequest> notifis) {
		this.notifis = notifis;
	}
	
    @FXML
    private TableColumn<updateCityRequest, String> answerColumn;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<updateCityRequest, LocalDate> dateColumn;

    @FXML
    private TableColumn<updateCityRequest, Integer> versionColumn;

    @FXML
    private TableColumn<updateCityRequest, String> cityNameColumn;

    @FXML
    private TableView<updateCityRequest> tableView;
    
    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToGCMWorkerServices();
    }
    @FXML
    void initialize() {
    	cityNameColumn.setCellValueFactory(new PropertyValueFactory<updateCityRequest, String>("cityName"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<updateCityRequest, LocalDate>("date"));
    	versionColumn.setCellValueFactory(new PropertyValueFactory<updateCityRequest, Integer>("version"));
    	answerColumn.setCellValueFactory(new PropertyValueFactory<updateCityRequest, String>("answer"));


        tableView.setItems(getNotifi());
    }
    
    public ObservableList<updateCityRequest> getNotifi() {
        ObservableList<updateCityRequest> tempNotifis = FXCollections.observableArrayList();
        for (updateCityRequest entry : notifis) {
        	tempNotifis.add(entry);
        }
        return tempNotifis;
    }
}
