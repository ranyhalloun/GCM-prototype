package Entities;

import javafx.beans.property.SimpleStringProperty;

public class Map {
    private int mapID;
    private SimpleStringProperty description;
    private SimpleStringProperty cityName;
    private String imagePath;
    
    //Constructor
    public Map(int mapID, String description, String cityName, String imagePath) {
        this.mapID = mapID;
        this.description = new SimpleStringProperty(description);
        this.cityName = new SimpleStringProperty(cityName);
        this.imagePath = imagePath;
    }
    
    //Getters
    public int getMapID() {
        return mapID;
    }
    
    public String getDescription() {
        return description.get();
    }
    
    public String getCityName() {
        return cityName.get();
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    //Setters
    
    public void setImagePath(String path) {
        this.imagePath = path;
    }
}