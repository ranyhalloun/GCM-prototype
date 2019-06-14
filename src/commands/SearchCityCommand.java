package commands;

import java.io.Serializable;
import Entities.City;


public class SearchCityCommand implements Serializable {
    
    // Input
    private String cityName;

    // Output
    private boolean success;
    private City city;

    // Constructor
    public SearchCityCommand(String cityName) {
        this.cityName = cityName;
        this.success = false;
        setCity(new City());
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCity(City city) {
        this.city = city;
    }

    // Getters

    public String getcityName() {
        return this.cityName;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public City getCity() {
        return city;
    }
}
