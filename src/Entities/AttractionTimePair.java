package Entities;

import java.io.Serializable;

public class AttractionTimePair implements Serializable{
    private Attraction attraction;
    private int time;
    private String attractionName;
    
    public AttractionTimePair(Attraction attraction, int time)
    {
        this.attraction = attraction;
        this.time = time;
        this.attractionName = attraction.getName();
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public int getTime() {
        return time;
    }
    
    public String getAttractionName() {
        return attractionName;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
        this.attractionName = attraction.getName();
    }

    public void setTime(int time) {
        this.time = time;
    }
}
