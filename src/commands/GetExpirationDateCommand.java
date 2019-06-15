package commands;

import java.io.Serializable;

public class GetExpirationDateCommand implements Serializable{

	//input
	private String cityName;
	
	//output
	private String expirationDate;

	public GetExpirationDateCommand(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
}
