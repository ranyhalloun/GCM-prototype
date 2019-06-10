package application.gcmWorker;
import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class requestApprovalController {

    @FXML
    private TextField cityName;

    @FXML
    private Button sendBtn;

    @FXML
    private Button backBtn;

    @FXML
    void send(ActionEvent event) throws IOException {
        System.out.println("Sending city to waiting approval queue");
        Main.getInstance().requestApproval(cityName.getText());
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        System.out.println("Going to customer services");
        Main.getInstance().goToGCMWorkerServices();
    }

}