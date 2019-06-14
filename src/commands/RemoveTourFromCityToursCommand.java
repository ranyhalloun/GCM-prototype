package commands;
import java.io.Serializable;

public class RemoveTourFromCityToursCommand implements Serializable {
    
    //input 
    private int tourID;
    
    //output
    private boolean success;
    private String error = "";
    
    // Constructor
    public RemoveTourFromCityToursCommand(int tourID){
        this.success = false;
        this.tourID = tourID;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public boolean getSuccess() {
        return this.success;
    }
    
    public String getError() {
        return this.error;
    }

    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }
}
