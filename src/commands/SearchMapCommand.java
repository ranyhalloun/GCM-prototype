package commands;
import java.io.Serializable;
import java.util.ArrayList;

import Entities.Map;
import Entities.SearchMapResult;

public class SearchMapCommand implements Serializable {

    // Input
    private String attraction;
    private String cityName;
    private String description;

    // Output
    private boolean success;
    private ArrayList<Map> maps;

    // Constructor
    public SearchMapCommand(String attraction, String cityName, String description) {
        this.attraction = attraction;
        this.cityName = cityName;
        this.description = description;
        this.success = false;
        this.maps = new ArrayList<Map>();
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMaps(ArrayList<Map> maps) {
        this.maps = maps;
    }

    // Getters
    public String getAttraction() {
        return this.attraction;
    }

    public String getcityName() {
        return this.cityName;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }
}