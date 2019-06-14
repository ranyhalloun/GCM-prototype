package Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class ViewDetails implements Serializable{

	private String cityName;
	private LocalDate date;
	private int mapID;
	
	public ViewDetails(String cityName, LocalDate date, int mapID) {
		this.cityName = cityName;
		this.date = date;
		this.setMapID(mapID);
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getMapID() {
		return mapID;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
}
