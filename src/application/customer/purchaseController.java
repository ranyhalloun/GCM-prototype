package application.customer;

import java.io.IOException;
import java.time.LocalDate;

import Entities.Map;
import Entities.ViewDetails;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class purchaseController {
	
	private String oneTimePrice;
	private String subscriptionPrice;
	private int mapID;
	private String city;
	private String attraction;
	private String description;
	public purchaseController(int mapID, String attraction, String city, String description, String oneTimePrice, String subscriptionPrice) {
		this.mapID = mapID;
		this.attraction = attraction;
		this.city = city;
		this.description = description;
		this.oneTimePrice = oneTimePrice;
		this.subscriptionPrice = subscriptionPrice;
	}
	
    @FXML
    private TextField cityName;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Button purchaseBtn;

    @FXML
    private Button backBtn;
    
    @FXML
    private TextField price;
    
    @FXML
    private TextField discount;
    
    @FXML
    private TextField afterDiscount;

    @FXML
    void purchase(ActionEvent event) throws IOException {
    	Main.getInstance().updateDBAfterPurchasing(city,typeComboBox.getValue());
    }

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToMapInfo(mapID, attraction, city, description);
    }
    
    @FXML
    void comboBoxWasUpdated(ActionEvent event) throws IOException {
    	String type = typeComboBox.getValue().toString();
    	if(type.equals("OneTime")) {
    		price.setText(oneTimePrice);
    		discount.setText("%0");
    		afterDiscount.setText(oneTimePrice); 
    	}
    	else {
    		boolean haveSubscription = Main.getInstance().checkSubscription(city);
    		price.setText(subscriptionPrice);
    		if (haveSubscription){
	    		discount.setText("%10");
	    		afterDiscount.setText(Double.toString((Double.parseDouble(subscriptionPrice)*0.9))); 
    		}
    	
    		else {
        		discount.setText("%0");
        		afterDiscount.setText(subscriptionPrice);
    		}
    	}
    }
    
    @FXML
    void initialize() {
    	
    	ObservableList<String> typeList = FXCollections.observableArrayList("OneTime", "Subscription");
    	
    	cityName.setText(city);
    	cityName.setEditable(false);
    	typeComboBox.setValue("OneTime");
    	typeComboBox.setItems(typeList);
    	price.setText(oneTimePrice);
    	discount.setText("0%");
		afterDiscount.setText(oneTimePrice);
		price.setEditable(false);
		discount.setEditable(false);
		afterDiscount.setEditable(false);


    }
}
