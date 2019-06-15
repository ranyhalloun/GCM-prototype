package application.customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Entities.updateCityRequest;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class newVersionsController {

	private ArrayList<updateCityRequest> notifis;
	public newVersionsController(ArrayList<updateCityRequest> notifis) {
		this.notifis = notifis;
	}
	
    @FXML
    private Button backBtn;

    @FXML
    private TableView<updateCityRequest> tableView;

    @FXML
    private TableColumn<updateCityRequest, LocalDate> dateColumn;

    @FXML
    private TableColumn<updateCityRequest, Integer> versionColumn;

    @FXML
    private TableColumn<updateCityRequest, String> cityNameColumn;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToCustomerServices();
    }
    @FXML
    void initialize() {
    	cityNameColumn.setCellValueFactory(new PropertyValueFactory<updateCityRequest, String>("cityName"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<updateCityRequest, LocalDate>("date"));
    	versionColumn.setCellValueFactory(new PropertyValueFactory<updateCityRequest, Integer>("version"));

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
