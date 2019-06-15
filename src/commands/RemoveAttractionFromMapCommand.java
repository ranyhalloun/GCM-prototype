package commands;

import java.io.Serializable;

public class RemoveAttractionFromMapCommand implements Serializable {
    
    //input
    private int mapID;
    private int attractionID;
    
    //output
    private boolean success;
    
    public RemoveAttractionFromMapCommand(int mapID, int attractionID) {
        this.mapID = mapID;
        this.attractionID = attractionID;
        this.success = false;
    }

    //Getters
    public boolean getSuccess() {
        return success;
    }

    public int getAttractionID() {
        return attractionID;
    }

    public int getMapID() {
        return mapID;
    }
    
    //Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setAttractionID(int attractionID) {
        this.attractionID = attractionID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }
}
