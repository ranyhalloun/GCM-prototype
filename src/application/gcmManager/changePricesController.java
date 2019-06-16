package application.gcmManager;
import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class changePricesController {

    private String errorMessage;
    private String oldSubsPrice;
    private String oldOneTimePrice;
    public changePricesController(String oldSubs, String oldOneTime, String errorMessage)
    {
        this.oldSubsPrice = oldSubs;
        this.oldOneTimePrice = oldOneTime;
        this.errorMessage = errorMessage;
    }


    @FXML
    private Button backBtn;

    @FXML
    private Label errorText;

    @FXML
    private TextField newOneTime;

    @FXML
    private Button sendBtn;

    @FXML
    private TextField oldOneTime;

    @FXML
    private TextField newSubs;

    @FXML
    private TextField oldSubs;

    @FXML
    void sentToCM(ActionEvent event) throws IOException {
        if(newOneTime.getText().isEmpty() && newSubs.getText().isEmpty())
            Main.getInstance().goToChangePrices(oldSubsPrice, oldOneTimePrice, "Enter new price please!");
        else
            Main.getInstance().sendNewPrices(newSubs.getText(), newOneTime.getText());
    }



    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToGCMManagerServices("");
    }

    @FXML
    void initialize() {
        oldSubs.setText(oldSubsPrice);
        oldOneTime.setText(oldOneTimePrice);
        oldSubs.setEditable(false);
        oldOneTime.setEditable(false);
        errorText.setText(errorMessage);
    }

}