package application.companyManager;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class pricesController {

    private String oldSubsPrice;
    private String oldOnePrice;
    private String newSubsPrice;
    private String newOnePrice;

    public pricesController(String oldSubsPrice, String oldOnePrice, String newSubsPrice, String newOnePrice) {
        this.oldSubsPrice = oldSubsPrice;
        this.oldOnePrice = oldOnePrice;
        this.newSubsPrice = newSubsPrice;
        this.newOnePrice = newOnePrice;
    }

    @FXML
    private Button acceptBtn;

    @FXML
    private Label oldOne;

    @FXML
    private Label newSubs;

    @FXML
    private Button backBtn;

    @FXML
    private Button declineBtn;

    @FXML
    private Label oldSubs;

    @FXML
    private Label newOne;

    @FXML
    void accept(ActionEvent event) throws IOException {
        Main.getInstance().updatePricesAfterAccept(newSubs.getText(), newOne.getText());
    }

    @FXML
    void decline(ActionEvent event) throws IOException {
        Main.getInstance().updatePricesAfterDecline();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToCompanyManagerServices("");
    }

    @FXML
    void initialize() {
        oldOne.setText(oldOnePrice);
        oldSubs.setText(oldSubsPrice);
        newOne.setText(newOnePrice);
        newSubs.setText(newSubsPrice);

        if(newSubs.getText().equals("-1") && newOne.getText().equals("-1"))
        {
            acceptBtn.setDisable(true);
            declineBtn.setDisable(true);
        }
    }

}