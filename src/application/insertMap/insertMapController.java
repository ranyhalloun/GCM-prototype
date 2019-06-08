package application.insertMap;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class insertMapController {

    @FXML
    private TextField id;

    @FXML
    private TextField cityName;

    @FXML
    private TextField imagePath;

    @FXML
    private TextField description;

    @FXML
    void insert(ActionEvent event) throws IOException {
        Main.getInstance().insertNewMap(Integer.parseInt(id.getText()), cityName.getText(), imagePath.getText(), description.getText());
    }

}