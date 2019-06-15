package Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class PurchaseDetails implements Serializable {

	private String cityName;
	private LocalDate date;
	private String purchaseType;
	
	public PurchaseDetails(String cityName, LocalDate date, int purchaseType) {
		this.cityName = cityName;
		this.date = date;
		this.purchaseType = purchaseType == 1 ? "Subscription":"OneTime";
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
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
}
