package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.Tour;

public class GetTourInfoFromIDCommand implements Serializable {
  //input
    private int tourID;
    
    //output
    private Tour tour;
    private boolean success;
    private String error = "";
    
    public GetTourInfoFromIDCommand(int tourID) {
        this.tourID = tourID;
    }
    
    //Setters
    public void setSuccess(boolean success)
    {
        this.success = success;
    }
    
    public void setTour(Tour tour) {
        this.tour = tour;
    }
    
    
    //Getters
    public boolean getSuccess() {
        return this.success;
    }
    
    public Tour getTour(){
        return this.tour;
    }
    
    public int getTourID() {
        return this.tourID;
    }
}
