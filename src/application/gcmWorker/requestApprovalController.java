package application.gcmWorker;
import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class requestApprovalController {

	private String errorMessage;
	
	public requestApprovalController(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
    @FXML
    private TextField cityName;

    @FXML
    private Button sendBtn;

    @FXML
    private Button backBtn;
    

    @FXML
    private Label errorText;
    
    @FXML
    void send(ActionEvent event) throws IOException {
        System.out.println("Sending city to waiting approval queue");
        if(cityName.getText().isEmpty())
        	Main.getInstance().goToRequestApproval("Fill enter city name please!");
        else {
        	Main.getInstance().requestApproval(cityName.getText());
        	cityName.setText("");
        	errorText.setText("");
        }
    }
    @FXML
    void back(ActionEvent event) throws IOException {
        System.out.println("Going to customer services");
        Main.getInstance().goToGCMWorkerServices();
    }
    
    @FXML
    void initialize() {
    	errorText.setText(errorMessage);
    }

}