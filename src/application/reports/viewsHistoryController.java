package application.reports;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Entities.PurchaseDetails;
import Entities.ViewDetails;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class viewsHistoryController {

	private String customerUsername;
	ArrayList<ViewDetails> views;
	public viewsHistoryController(String customerUsername, ArrayList<ViewDetails> views){
		this.customerUsername = customerUsername;
		this.views = views;
	}
	
    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<ViewDetails, Integer> mapIDColumn;

    @FXML
    private TableView<ViewDetails> tableView;

    @FXML
    private TableColumn<ViewDetails, LocalDate> dateColumn;

    @FXML
    private TableColumn<ViewDetails, String> cityNameColumn;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToCustomerReport(customerUsername);
    }
    @FXML
    void initialize() {
    	cityNameColumn.setCellValueFactory(new PropertyValueFactory<ViewDetails, String>("cityName"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<ViewDetails, LocalDate>("date"));
    	mapIDColumn.setCellValueFactory(new PropertyValueFactory<ViewDetails, Integer>("mapID"));

        tableView.setItems(getViews());
    }
    
    public ObservableList<ViewDetails> getViews() {
        ObservableList<ViewDetails> tempViews = FXCollections.observableArrayList();
        for (ViewDetails entry : views) {
        	tempViews.add(entry);
        }
        return tempViews;
    }
}
