package commands;
import java.io.Serializable;

public class SearchMapCommand implements Serializable {

    // Input
    private String attraction;
    private String cityName;
    private String description;

    // Output
    private int success;
    private String error = "";

    // Constructor
    public SearchMapCommand(String attraction, String cityName, String description) {
        this.attraction = attraction;
        this.cityName = cityName;
        this.description = description;
        this.success = -1;
    }

    // Setters
    public void setSuccess(int success) {
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
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

    public int getSuccess() {
        return this.success;
    }

    public String getError() {
        return this.error;
    }

}