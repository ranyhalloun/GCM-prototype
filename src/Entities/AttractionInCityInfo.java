package Entities;

public class AttractionInCityInfo {
    
    private String cityName;
    private int numOfMapsIncludeAttraction;
    private String cityDescription;
    
    public AttractionInCityInfo()
    {
        this.cityName = "";
        this.numOfMapsIncludeAttraction = -1;
        this.cityDescription = "";
    }
    
    public AttractionInCityInfo(String cityName)
    {
        this.cityName = cityName;
        this.numOfMapsIncludeAttraction = -1;
        this.cityDescription = "";
    }
    
    public AttractionInCityInfo(String cityName, int numOfMapsIncludeAttraction, String cityDescription)
    {
        this.cityName = cityName;
        this.numOfMapsIncludeAttraction = numOfMapsIncludeAttraction;
        this.cityDescription = cityDescription;
    }
    
    //Getters
    public String getCityName() {
        return cityName;
    }
    
    public int getNumOfMapsIncludeAttraction() {
        return numOfMapsIncludeAttraction;
    }
    
    public String getCityDescription() {
        return cityDescription;
    }
    
    //Setters
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setNumOfMapsIncludeAttraction(int numOfMapsIncludeAttraction) {
        this.numOfMapsIncludeAttraction = numOfMapsIncludeAttraction;
    }

    public void setCityDescription(String cityDescription) {
        this.cityDescription = cityDescription;
    }

}
