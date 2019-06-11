package Entities;

import java.util.ArrayList;
import java.io.Serializable;


public class Tour implements Serializable {

    private int id;
    private String description;
    private ArrayList<StringIntPair> attractionsName;
    
    //------------------------------------------//

    public Tour()
    {
        this.id = -1;
        this.description = "";
        this.attractionsName = new ArrayList<StringIntPair>();
    }
    
    public Tour(int id, String description, ArrayList<StringIntPair> attractionsName)
    {
        this.id = id;
        this.description = description;
        this.attractionsName = attractionsName;
    }
    
    public Tour(int id, String description)
    {
        this.id = id;
        this.description = description;
    }
    
    //------------------------------------------//
    
    //Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<StringIntPair> getAttractionsName() {
        return attractionsName;
    }
    

    //------------------------------------------//
    
    //Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setAttractionsName(ArrayList<StringIntPair> attractionsName) {
        this.attractionsName = attractionsName;
    }
    
}
