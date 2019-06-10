package Entities;

import java.io.Serializable;

public class Attraction implements Serializable {
    
    private String name;
    private String location;
    private String category;
    private String description;
    private String cityName;
    private boolean isAccessible;

    public Attraction()
    {
        this.name = "";
        this.location = "";
        this.category = "";
        this.description = "";
        this.setCityName("");
        this.isAccessible = false;
    }
    
    public Attraction(String name, String location)
    {
        this.name = name;
        this.location = location;
    }
    
    //----------------------------------//
    
    //Getters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
    
    public String getCityName() {
        return cityName;
    }

    public boolean isAccessible() {
        return isAccessible;
    }
    
    //----------------------------------//
    
    // Setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public void setAccessible(boolean isAccessible) {
        this.isAccessible = isAccessible;
    }
    
    public void print() {
        System.out.printf("Attraction name: %s, location: %s, "
                + "category: %s, description: %s, cityName: %s, "
                + "isAccessible %b%n", this.name, this.location, 
                this.category, this.description, this.cityName, 
                this.isAccessible);
    }
}