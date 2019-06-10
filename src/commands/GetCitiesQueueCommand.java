package commands;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.ObservableList;

public class GetCitiesQueueCommand implements Serializable{
	
	// Output
	ArrayList<String> cities;
	private boolean success;
    private String error = "";
    
    // Constructor
    public GetCitiesQueueCommand(){
    	this.success = false;
    }

    //Getters
    public boolean getSuccess() {
    	return this.success;
    }
    
    public ArrayList<String> getCities(){
    	return this.cities;
    }
    
    // Setters
    public void setSuccess(boolean success) {
    	this.success = success;
    }
    public void setCities(ArrayList<String> cities){
    	this.cities = cities;
    }
}
