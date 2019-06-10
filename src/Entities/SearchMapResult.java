package Entities;

import java.util.ArrayList;
import java.io.Serializable;

public class SearchMapResult implements Serializable {
    
    private ArrayList<Map> maps;
    
    private boolean searchByCity;
    private boolean searchByAttraction;
    private boolean searchByDescription;

    // Search by city name
    private City city;

    // Search by attraction
    private String cityName;
    private int numOfMapsIncludeAttraction;
    private String attractionDescription;

    //----------------------------------------//

    public SearchMapResult() {
        maps = new ArrayList<Map>();
        setCity(new City());
        this.searchByCity = false;
        this.searchByAttraction = false;
        this.searchByDescription = false;
    }

    //Setters
    public void setMaps(ArrayList<Map> maps) {
        this.maps = maps;
    }

    public void addMap(Map map) {
        this.maps.add(map);
    }

    public void setSearchByCity(boolean bool) {
        this.searchByCity = bool;
    }
    
    public void setSearchByAttraction(boolean bool) {
        this.searchByAttraction = bool;
    }
    
    public void setSearchByDescription(boolean bool) {
        this.searchByDescription = bool;
    }
    
    public void setCityNameOfAttraction(String name) {
        this.cityName = name;
    }
    
    public void setNumOfMapsIncludeAttraction(int num) {
        this.numOfMapsIncludeAttraction = num;
    }
    
    public void setAttractionDescription(String attractionDescription) {
        this.attractionDescription = attractionDescription;
    }

    public void setCity(City city) {
        this.city = city;
    }
    
    //----------------------------------------//
    
    //Getters
    public boolean getSearchByCity() {
        return this.searchByCity;
    }
    
    public boolean getSearchByAttraction() {
        return this.searchByAttraction;
    }
    
    public boolean getSearchByDescription() {
        return this.searchByDescription;
    }
    
    public ArrayList<Map> getMaps() {
        return this.maps;
    }
    
    public String getCityNameOfAttraction() {
        return this.cityName;
    }
    
    public int getNumOfMapsIncludeAttraction() {
        return this.numOfMapsIncludeAttraction;
    }

    public String getAttractionDescription() {
        return this.attractionDescription;
    }

    public City getCity() {
        return city;
    }
}
