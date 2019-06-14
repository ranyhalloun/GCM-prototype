package commands;

import java.io.Serializable;

import Entities.Map;

public class GetMapInfoFromIDCommand implements Serializable {
    
    // Input
    private int mapID;

    // Output
    private boolean success;
    private Map map;

    // Constructor
    public GetMapInfoFromIDCommand(int mapID) {
        this.mapID = mapID;
        this.success = false;
        this.map = new Map();
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    // Getters
    public int getMapID() {
        return this.mapID;
    }
    public Map getMap() {
        return this.map;
    }

    public boolean getSuccess() {
        return this.success;
    }
}
