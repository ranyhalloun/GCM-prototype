package application.connection;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class connectionController {
    
    @FXML
    private TextField IP;

    @FXML
    private TextField Port;
    
    @FXML
    private Button startConnectionBtn;
    
    @FXML
    void startConnection(ActionEvent event) throws NumberFormatException, IOException {
        System.out.printf("Starting connection:%nIP: %s, Port: %s%n",IP.getText(), Integer.parseInt(Port.getText()));
        Main.getInstance().connect(IP.getText(), Integer.parseInt(Port.getText()));
    }

    void initialize() throws IOException {
        assert startConnectionBtn != null : "fx:id=\"startConnectionBtn\" was not injected: check your FXML file 'UIScene.fxml'.";
    }

}
