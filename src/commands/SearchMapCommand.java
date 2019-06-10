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
    private SearchMapResult searchMapResult;

    // Constructor
    public SearchMapCommand(String attraction, String cityName, String description) {
        this.attraction = attraction;
        this.cityName = cityName;
        this.description = description;
        this.success = false;
        this.searchMapResult = new SearchMapResult();
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setSearchMapResult(SearchMapResult searchMapResult) {
        this.searchMapResult = searchMapResult;
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

    public SearchMapResult getSearchMapResult() {
        return searchMapResult;
    }
}