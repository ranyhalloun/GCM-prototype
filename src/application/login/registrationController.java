package application.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class registrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField username;


    @FXML
    void registration(ActionEvent event) throws IOException {
        Main.getInstance().registerNewUser(firstname.getText(), lastname.getText(), username.getText(), password.getText());
        // TODO:
        // Handle wrong input
        // When Registering successfully, go to previous view
        // Add button to go to previous view
    }

    @FXML
    void initialize() {
        assert firstname != null : "fx:id=\"firstname\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert lastname != null : "fx:id=\"lastname\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'registrationView.fxml'.";


    }

}
