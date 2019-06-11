package application.searchmap;
import java.io.IOException;

import Entities.Attraction;
import Entities.City;
import Entities.SearchMapResult;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;




public class attractionController {

	private SearchMapResult searchMapResult;

	public attractionController(SearchMapResult searchMapResult)
    {
        this.searchMapResult = searchMapResult;
    }
	
	@FXML
    private TextField isAccessible;

    @FXML
    private Button backBtn;

    @FXML
    private Button mapsBtn;

    @FXML
    private TextField category;

    @FXML
    private TextField attractionName;

    @FXML
    private TextArea description;
    
    @FXML
    void back(ActionEvent event) throws IOException {
        Main.getInstance().goToSearchMap("");
    }

    @FXML
    void maps(ActionEvent event) throws IOException {
    	Main.getInstance().goToMapsTable(searchMapResult);
    }
    
    @FXML
    void initialize() {

        Attraction attraction = searchMapResult.getAttraction();
        attractionName.setText(attraction.getName());
        category.setText(attraction.getCategory());
        isAccessible.setText(Boolean.toString(attraction.getIsAccessible()));
        description.setText(attraction.getDescription());
        
        attractionName.setEditable(false);
        category.setEditable(false);
        isAccessible.setEditable(false);
        description.setEditable(false);

    }

}
