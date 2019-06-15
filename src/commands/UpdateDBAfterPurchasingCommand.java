package commands;

import java.io.Serializable;

public class UpdateDBAfterPurchasingCommand implements Serializable{

	//input
	private String cityName;
	private int purchaseType;
	
	public UpdateDBAfterPurchasingCommand(String cityName, String purchaseType) {
		this.setCityName(cityName);
		this.purchaseType = purchaseType == "OneTime" ? 0:1;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(int purchaseType) {
		this.purchaseType = purchaseType;
	}

}
