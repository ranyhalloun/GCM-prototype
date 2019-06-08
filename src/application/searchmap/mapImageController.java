package application.searchmap;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import application.Main;

public class mapImageController {
	
	private Map map;
	
	public void initData(Map map) {
		this.map = map;
		//photo.setImage()
	}
	

    @FXML
    private Button backBtn;
    
    @FXML
    private ImageView photo;

    @FXML
   /* void back(ActionEvent event) {
    	Main.getInstance().goToSearchMapResult(map.getMapID(), map.getCityName(), map.getDescription());
    }*/

	
    void initialize() throws IOException {
    	//Main.getInstance().displayImage(map.getImagePath());
    }
	
}


