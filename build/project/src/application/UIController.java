
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.GCMClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class UIController {

    private ResourceBundle resources;

    private URL location;
    
    private GCMClient gcmClient;

    private Button incNumOfPurchasesBtn;

    @FXML
    private TextField numOfPurchases;

    private Button showNumOfPurchasesBtn;

    @FXML
    private TextField userName;

    
    public UIController() throws IOException
    {
        gcmClient = new GCMClient(this,"127.0.0.1", 458);
        numOfPurchases = new TextField();
    }

    public void showNumberToUser(String num)
    {
        numOfPurchases.setText(num);
    }
    
    @FXML
    void incNumOfPurchases(ActionEvent event) throws IOException {
        gcmClient.handleIncNumOfPurchases(userName.getText());
    }

    @FXML
    void showNumOfPurchases(ActionEvent event) throws IOException {
        gcmClient.handleShowNumOfPurchases(userName.getText());
    }

    void initialize() throws IOException {
        assert incNumOfPurchasesBtn != null : "fx:id=\"incNumOfPurchasesBtn\" was not injected: check your FXML file 'UIScene.fxml'.";
        assert numOfPurchases != null : "fx:id=\"numOfPurchases\" was not injected: check your FXML file 'UIScene.fxml'.";
        assert showNumOfPurchasesBtn != null : "fx:id=\"showNumOfPurchasesBtn\" was not injected: check your FXML file 'UIScene.fxml'.";
        assert userName != null : "fx:id=\"userName\" was not injected: check your FXML file 'UIScene.fxml'.";
    }

}
