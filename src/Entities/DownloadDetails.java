package Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class DownloadDetails implements Serializable{

	private String cityName;
	private LocalDate date;
	
	public DownloadDetails(String cityName, LocalDate date) {
		this.cityName = cityName;
		this.date = date;
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
}
