package commands;
import java.io.Serializable;
import java.util.ArrayList;
import Entities.Map;

public class InsertMapCommand implements Serializable {
    
    // Input
    private Map map;
    
    // Output
    private boolean success;
    private String error = "";
    
    // Constructor
    public InsertMapCommand(Map map) {
        this.map = map;
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

    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getError() {
        return this.error;
    }
    
    public Map getMap() {
        return map;
    }
    
}