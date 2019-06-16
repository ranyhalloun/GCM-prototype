package application.reports;


import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class searchCustomerController {
	
	private String errorMessage;
	public searchCustomerController(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
    @FXML
    private Label errorText;

    @FXML
    private Button backBtn;

    @FXML
    private TextField username;
    
    @FXML
    private Button getReportBtn;
    
    @FXML
    void getReport(ActionEvent event) throws IOException {
    	if(username.getText().isEmpty())
    		Main.getInstance().goToSearchCustomer("Enter username please!");
    	
    	else
    		Main.getInstance().CheckCustomer(username.getText());
    	
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
        System.out.println("Going back to Services");
    	switch(Main.getInstance().getUserType()) {
	        case GCMManager:
	            Main.getInstance().goToGCMManagerServices("");
	            break;
	        case CompanyManager:
	            Main.getInstance().goToCompanyManagerServices("");
	            break;
		}
    }
    
    @FXML
    void initialize() {
        errorText.setText(errorMessage);
    }

}
