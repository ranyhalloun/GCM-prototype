package Entities;

import java.util.ArrayList;
import java.io.Serializable;


public class Map implements Serializable {

    private int mapID;
    private String description;
    private String cityName;    
    private String imagePath;
    private ArrayList<Attraction> attractions;
    private int version;

    //Constructor
    public Map() {
        this.mapID = -1;
        this.description = "";
        this.cityName = "";
        this.imagePath = "";
        this.attractions = new ArrayList<Attraction>();
        this.version = -1;
    }
    
    public Map(int mapID)
    {
        this.mapID = mapID;
        this.description = "";
        this.cityName = "";
        this.imagePath = "";
        this.attractions = new ArrayList<Attraction>();
        this.version = -1;
    }

    public Map(int mapID, String description, String cityName, String imagePath)
    {
        this.mapID = mapID;
        this.description = description;
        this.cityName = cityName;
        this.imagePath = imagePath;
        this.attractions = new ArrayList<Attraction>();
        this.version = -1;
    }
    
    public Map(int mapID, String description, String cityName, String imagePath,
            ArrayList<Attraction> attractions, int version) {
        this.mapID = mapID;
        this.description = description;
        this.cityName = cityName;
        this.imagePath = imagePath;
        this.attractions = attractions;
        this.version = version;
    }

    //Getters
    public int getMapID() {
        return mapID;
    }

    public String getDescription() {
        return description;
    }

    public String getCityName() {
        return cityName;
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }

    public int getVersion() {
        return version;
    }

    
    //Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    public void print() {
        System.out.printf("map ID: %s, description: %s, cityName: %s,"
                + "imagePath: %s, version: %d%n", this.mapID, this.description,
                this.cityName, this.imagePath, this.version);
        for (Attraction attr : attractions) {
            attr.print();
        }
    }
    
    @Override
    public boolean equals(Object obj) {
         return (this.mapID == ((Map)obj).getMapID());
    }
    
    @Override
    public int hashCode() {
         return this.mapID;
    }
}