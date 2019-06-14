package application.reports;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Entities.DownloadDetails;
import Entities.PurchaseDetails;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class downloadsHistoryController {

	
	private String customerUsername;
	ArrayList<DownloadDetails> downloads;
	public downloadsHistoryController(String customerUsername, ArrayList<DownloadDetails> downloads){
		this.customerUsername = customerUsername;
		this.downloads = downloads;
	}
	
    @FXML
    private Button backBtn;

    @FXML
    private TableView<DownloadDetails> tableView;

    @FXML
    private TableColumn<DownloadDetails, LocalDate> dateColumn;

    @FXML
    private TableColumn<DownloadDetails, String> cityNameColumn;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToCustomerReport(customerUsername);
    }
    @FXML
    void initialize() {
    	cityNameColumn.setCellValueFactory(new PropertyValueFactory<DownloadDetails, String>("cityName"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<DownloadDetails, LocalDate>("date"));

        tableView.setItems(getDownloads());
    }
    
    public ObservableList<DownloadDetails> getDownloads() {
        ObservableList<DownloadDetails> tempDownloads = FXCollections.observableArrayList();
        for (DownloadDetails entry : downloads) {
        	tempDownloads.add(entry);
        }
        return tempDownloads;
    }
}
