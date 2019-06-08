package commands;
import java.io.Serializable;

public class InsertMapCommand implements Serializable {
    // Input
	private int id;
    private String cityName;
    private String description;
    private String imagePath;
    
    // Output
    private boolean success;
    private String error = "";
    
    // Constructor
    public InsertMapCommand(int id, String cityName, String description, String imagePath) {
        this.id = id;
        this.cityName = cityName;
        this.description = description;
        this.imagePath = imagePath;
        this.success = false;
    }
    
    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setError(String error) {
    	this.error = error;
    }
    
    // Getters
    public int getID() {
        return this.id;
    }
    
    public String getCityName() {
        return this.cityName;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getImagePath() {
        return this.imagePath;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getError() {
    	return this.error;
    }
    
}
