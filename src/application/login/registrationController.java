package application.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class registrationController {

	private String errorMessage;
	
	public registrationController(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
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
    private TextField phone;
    
    @FXML
    private TextField email;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Label errorMessageText;

    @FXML
    void registration(ActionEvent event) throws IOException, InterruptedException {
        String firstname_ = firstname.getText();
        String lastname_ = lastname.getText();
        String username_ = username.getText();
        String password_ = password.getText();
        String email_ = email.getText();
        String phone_ = phone.getText();
        
        if (firstname_.isEmpty() || lastname_.isEmpty() || username_.isEmpty()
                || password_.isEmpty() || email_.isEmpty() || phone_.isEmpty()) {
            Main.getInstance().goToRegistration("Fill all the fields to register");
        } else {
            Main.getInstance().registerNewUser(firstname_, lastname_, username_, password_, email_, phone_);
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	System.out.println("Back to login view");
        Main.getInstance().goToLogin("");
    }

    @FXML
    void initialize() {
        assert firstname != null : "fx:id=\"firstname\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert lastname != null : "fx:id=\"lastname\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'registrationView.fxml'.";
        assert phone != null : "fx:id=\"phone\" was not injected: check your FXML file 'registrationView.fxml'.";
        
        errorMessageText.setText(errorMessage);
    }
}
