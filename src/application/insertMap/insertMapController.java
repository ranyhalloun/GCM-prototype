package application.insertMap;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button insertBtn;
    
    @FXML
    private Button backBtn;
    
    @FXML
    void insert(ActionEvent event) throws IOException {
        Main.getInstance().insertNewMap(Integer.parseInt(id.getText()), cityName.getText(), imagePath.getText(), description.getText());
    }
    

    @FXML
    void back(ActionEvent event) throws IOException {
    	System.out.println("Going to previous view");
    	switch(Main.getInstance().getUserType()) {
    		case GCMWorker:
    			Main.getInstance().goToGCMWorkerServices();
    			break;
            case GCMManager:
                Main.getInstance().goToGCMManagerServices();
                break;
            case CompanyManager:
                // CompanyManager Windows
                break;
    	}

    }
}