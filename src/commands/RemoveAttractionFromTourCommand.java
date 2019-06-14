package commands;
import java.io.Serializable;

public class RemoveAttractionFromTourCommand implements Serializable {

	//input 
	private String attractionID;
	private int tourID;
	
	//output
    private boolean success;
    private String error = "";
    
    // Constructor
        public RemoveAttractionFromTourCommand(String attractionID, int tourID){
        	this.success = false;
        	this.attractionID = attractionID;
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

	public String getAttractionID() {
		return attractionID;
	}

	public void setAttractionID(String attractionID) {
		this.attractionID = attractionID;
	}

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}
    
}