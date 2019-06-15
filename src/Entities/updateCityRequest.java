package Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class updateCityRequest implements Serializable{
	private String cityName;
	private String answer;
	private LocalDate date;
	private int version;
	
	public updateCityRequest(String cityName, String answer, LocalDate date, int version) {
		this.cityName = cityName;
		this.answer = answer;
		this.date = date;
		this.version = version;
	}
	
	public updateCityRequest(String cityName, LocalDate date, int version) {
		this.cityName = cityName;
		this.date = date;
		this.version = version;
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
