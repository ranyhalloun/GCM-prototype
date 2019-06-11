package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.Tour;

public class GetCityToursCommand implements Serializable{
	
	//input
	private String cityName;
	
	//output
	private ArrayList<Tour> tours;
    private boolean success;
    private String error = "";
    
    public GetCityToursCommand(String cityName) {
    	this.cityName = cityName;
    }
    
    //Setters
    public void setSuccess(boolean success)
    {
    	this.success = success;
    }
    
    public void setTours(ArrayList<Tour> tours) {
    	this.tours = tours;
    }
    
    
    //Getters
    public boolean getSuccess() {
    	return this.success;
    }
    
    public ArrayList<Tour> getTours(){
    	return this.tours;
    }
    
    public String getCityName() {
    	return this.cityName;
    }
}
