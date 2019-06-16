package commands;

import java.io.Serializable;


public class IncrementNumViewOfMapCommand implements Serializable {

    //input
    private int mapID;
    private String cityName;

    public IncrementNumViewOfMapCommand(int mapID, String cityName) {
        this.mapID = mapID;
        this.cityName = cityName;
    }

    //Getters

    public String getCityName() {
        return cityName;
    }

    public int getMapID() {
        return mapID;
    }

    //Setters
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }
}