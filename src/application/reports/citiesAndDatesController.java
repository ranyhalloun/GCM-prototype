package application.reports;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import application.Main;
import application.arrayOfStrings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class citiesAndDatesController {

	private ArrayList<String> cities;
	private String errorMessage;
	public citiesAndDatesController(ArrayList<String> cities, String errorMessage) {
		this.cities = cities;
		this.errorMessage = errorMessage;
	}
	

    @FXML
    private Label errorText;
    
    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<String> tableView;

    @FXML
    private TableColumn<String, String> cityNameColumn;

    @FXML
    private Button cityReportBtn;

    @FXML
    void cityReport(ActionEvent event) throws IOException {
        System.out.println("Going city report");
        String cityName = tableView.getSelectionModel().getSelectedItem();
        LocalDate from = fromDate.getValue();
        LocalDate to = toDate.getValue();
        if(from == null || to == null) {
        	Main.getInstance().goToCitiesReport("Enter from and to dates please!");
        }
        else {
        	this.cityReportBtn.setDisable(true);
        	Main.getInstance().goToCityReport(cityName, from, to);
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        System.out.println("Going back to Services");
    	switch(Main.getInstance().getUserType()) {
	        case GCMManager:
	            Main.getInstance().goToGCMManagerServices();
	            break;
	        case CompanyManager:
	            Main.getInstance().goToCompanyManagerServices();
	            break;
		}
    }

    @FXML
    void userClickOnTable() {
        if(tableView.getSelectionModel().getSelectedItem()!=null) {
            this.cityReportBtn.setDisable(false);
            fromDate.setDisable(false);
            toDate.setDisable(false);
        }
    }
    
    public void getCities() {
        ObservableList<String> tempCities = FXCollections.observableArrayList(cities);
        cityNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        tableView.setItems(tempCities);
    }

    @FXML
    void initialize() {
        cityReportBtn.setDisable(true);
        fromDate.setDisable(true);
        toDate.setDisable(true);
        errorText.setText(errorMessage);
        getCities();
    }
    
}
