package Entities;

import java.util.ArrayList;
import java.io.Serializable;

public class City implements Serializable {

    private int numOfAttractions;
    private int numOfTours;
    private int numOfMaps;
    private ArrayList<Tour> tours;
    private String description;
    private String cityName;
    
    //----------------------------------//
    
    public City()
    {
        this.numOfAttractions = -1;
        this.numOfTours = -1;
        this.numOfMaps = -1;
        this.setTours(new ArrayList<Tour>());
        this.setDescription("");
        this.cityName = "";
    }
    //----------------------------------//
    
    //Getters
    public int getNumOfAttractions() {
        return numOfAttractions;
    }

    public int getNumOfTours() {
        return numOfTours;
    }    

    public int getNumOfMaps() {
        return numOfMaps;
    }
    
    public ArrayList<Tour> getTours() {
        return tours;
    }

    public String getDescription() {
        return description;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    //----------------------------------//
    
    //Setters
    public void setNumOfAttractions(int numOfAttractions) {
        this.numOfAttractions = numOfAttractions;
    }
    
    public void setNumOfTours(int numOfTours) {
        this.numOfTours = numOfTours;
    }
    
    public void setNumOfMaps(int numOfMaps) {
        this.numOfMaps = numOfMaps;
    }

    public void setTours(ArrayList<Tour> tours) {
        this.tours = tours;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
