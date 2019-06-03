package application.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class loginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button signinBtn;
    
    @FXML
    private Button registrationBtn;

    @FXML
    private Button anonymousBtn;

    @FXML
    private TextField password;

    @FXML
    private TextField username;


    @FXML
    void goToSearchMap(ActionEvent event) throws IOException {
        System.out.println("Continuing as anonymous...");
        Main.getInstance().continueAsAnonymous();
    }

    @FXML
    void goToRegistration(ActionEvent event) throws IOException {
        System.out.println("Go to Registration View...");
        Main.getInstance().goToRegistration();
    }

    @FXML
    void signIn(ActionEvent event) throws IOException {
        Main.getInstance().signIn(username.getText(), password.getText());
    }

    @FXML
    void initialize() {
        assert anonymousBtn != null : "fx:id=\"anonymousBtn\" was not injected: check your FXML file 'loginView.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'loginView.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'loginView.fxml'.";


    }

}
