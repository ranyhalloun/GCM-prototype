package commands;

import java.io.Serializable;

import Entities.Attraction;

public class AddNewAttractionToMapCommand implements Serializable {
    
    //input
    private int mapID;
    private Attraction attraction;
    
    //output
    private boolean success;
    
    public AddNewAttractionToMapCommand(int mapID, Attraction attraction) {
        this.mapID = mapID;
        this.attraction = attraction;
        this.success = false;
    }

    //Getters
    public boolean getSuccess() {
        return success;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public int getMapID() {
        return mapID;
    }
    
    //Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }
}
