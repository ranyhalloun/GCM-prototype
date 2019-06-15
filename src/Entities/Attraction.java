package Entities;

import java.io.Serializable;

public class Attraction implements Serializable {
    
    private int id;
    private String name;
    private Coordinates location;
    private String category;
    private String description;
    private String cityName;
    private boolean isAccessible;

    public Attraction()
    {
        this.name = "";
        this.location = new Coordinates();
        this.category = "";
        this.description = "";
        this.setCityName("");
        this.isAccessible = false;
    }
    
    public Attraction(String name, Coordinates location)
    {
        this.name = name;
        this.location = location;
    }
    
    public Attraction(String name, String category, String description, boolean isAccessible)
    {
    	this.name = name;
    	this.category = category;
    	this.isAccessible = isAccessible;
    	this.description = description;
    }
    
    public Attraction(String name, String category, String description, boolean isAccessible, String  cityName)
    {
    	this.name = name;
    	this.category = category;
    	this.isAccessible = isAccessible;
    	this.description = description;
    	this.cityName = cityName;
    }
    
    public Attraction(int id, String name, String category, String description, boolean isAccessible, String cityName)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isAccessible = isAccessible;
        this.description = description;
        this.cityName = cityName;
    }
    
    public Attraction(int id, String name, String category, String description, boolean isAccessible, String cityName, Coordinates location)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isAccessible = isAccessible;
        this.description = description;
        this.cityName = cityName;
        this.location = location;
    }
    
    public Attraction(String name, String category, String description, boolean isAccessible, String cityName, Coordinates location)
    {
        this.name = name;
        this.category = category;
        this.isAccessible = isAccessible;
        this.description = description;
        this.cityName = cityName;
        this.location = location;
    }
    
    //----------------------------------//
    
    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getLocation() {
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

    public boolean getIsAccessible() {
        return isAccessible;
    }
    
    //----------------------------------//
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLocation(Coordinates location) {
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
        System.out.printf("Attraction ID: %d, Attraction name: %s, category: %s, description: %s, cityName: %s, "
                + "isAccessible %b, X_COORD = %f, Y_COORD = %f%n", this.id, this.name, this.category, this.description, this.cityName, this.isAccessible, 
                this.location.getX_cord(), this.location.getY_cord());
    }
}