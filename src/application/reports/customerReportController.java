package application.reports;
import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class customerReportController {

	private String customerUsername;
	public customerReportController(String customerUsername) {
		this.customerUsername = customerUsername;
	}
	
    @FXML
    private Button purchasesHistoryBtn;

    @FXML
    private Button downloadsHistoryBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button viewsHistoryBtn;

    @FXML
    void back(ActionEvent event) throws IOException {
	    Main.getInstance().goToSearchCustomer("");
    }

    @FXML
    void viewHistory(ActionEvent event) throws IOException  {
    	Main.getInstance().goToViewsHistory(customerUsername);
    }

    @FXML
    void purchasesHistory(ActionEvent event) throws IOException  {
    	Main.getInstance().goToPurchasesHistory(customerUsername);
    }

    @FXML
    void downloadsHistory(ActionEvent event) throws IOException  {
    	Main.getInstance().goToDownloadsHistory(customerUsername);
    }

}
