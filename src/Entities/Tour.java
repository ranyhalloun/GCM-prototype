package Entities;

import java.util.ArrayList;
import java.io.Serializable;


public class Tour implements Serializable {

    private String cityName;
    private int id;
    private String description;
    private ArrayList<AttractionTimePair> attractionsTimePair;
    private int numOfAttractions;
    
    //------------------------------------------//

    public Tour()
    {
        this.id = -1;
        this.description = "";
        this.cityName = "";
        this.attractionsTimePair = new ArrayList<AttractionTimePair>();
        this.numOfAttractions = 0;
    }
    
    public Tour(int id, String description, ArrayList<AttractionTimePair> attractionsTimePair)
    {
        this.id = id;
        this.description = description;
        this.cityName = "";
        this.attractionsTimePair = attractionsTimePair;
        this.numOfAttractions = this.attractionsTimePair.size();
    }
    
    public Tour(int id, String description)
    {
        this.id = id;
        this.description = description;
        this.cityName = "";
        this.attractionsTimePair = new ArrayList<AttractionTimePair>();
        this.numOfAttractions = 0;
    }
    
    public Tour(int id, String description, String cityName)
    {
        this.id = id;
        this.description = description;
        this.cityName = cityName;
        this.attractionsTimePair = new ArrayList<AttractionTimePair>();
        this.numOfAttractions = 0;
    }
    
    //------------------------------------------//
    
    //Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<AttractionTimePair> getAttractionsTimePair() {
        return attractionsTimePair;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public int getNumOfAttractions() {
        return numOfAttractions;
    }
    

    //------------------------------------------//
    
    //Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setAttractionsTimePair(ArrayList<AttractionTimePair> attractionsTimePair) {
        this.attractionsTimePair = attractionsTimePair;
        this.numOfAttractions = this.attractionsTimePair.size();
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public void print() {
        System.out.printf("City Name of Tour: %s, Tour ID: %d, Tour Description: %s, Num of Attractions in Tour: %d.%n", 
                this.cityName, this.id, this.description, this.numOfAttractions);
    }
    
}
