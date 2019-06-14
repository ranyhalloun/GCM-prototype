package application.reports;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import Entities.AttractionTimePair;
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

public class purchasesHistoryController {
	
	private String customerUsername;
	ArrayList<PurchaseDetails> purchases;
	public purchasesHistoryController(String customerUsername, ArrayList<PurchaseDetails> purchases){
		this.customerUsername = customerUsername;
		this.purchases = purchases;
	}

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<PurchaseDetails, String> purchaseTypeColumn;

    @FXML
    private TableView<PurchaseDetails> tableView;

    @FXML
    private TableColumn<PurchaseDetails, LocalDate> dateColumn;

    @FXML
    private TableColumn<PurchaseDetails, String> cityNameColumn;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToCustomerReport(customerUsername);
    }
    
    
    @FXML
    void initialize() {
    	cityNameColumn.setCellValueFactory(new PropertyValueFactory<PurchaseDetails, String>("cityName"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<PurchaseDetails, LocalDate>("date"));
    	purchaseTypeColumn.setCellValueFactory(new PropertyValueFactory<PurchaseDetails, String>("purchaseType"));

        tableView.setItems(getPurchases());
    }
    
    public ObservableList<PurchaseDetails> getPurchases() {
        ObservableList<PurchaseDetails> temPurchases = FXCollections.observableArrayList();
        for (PurchaseDetails entry : purchases) {
        	temPurchases.add(entry);
        }
        return temPurchases;
    }

}
