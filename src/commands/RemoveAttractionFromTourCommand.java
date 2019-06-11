package commands;
import java.io.Serializable;

public class RemoveAttractionFromTourCommand implements Serializable{

	//input 
	private String attractionName;
	private int tourID;
	
	//output
    private boolean success;
    private String error = "";
    
    // Constructor
        public RemoveAttractionFromTourCommand(String attractionName, int tourID){
        	this.success = false;
        	this.attractionName = attractionName;
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

	public String getAttractionName() {
		return attractionName;
	}

	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}
    
}