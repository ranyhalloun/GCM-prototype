package commands;

import java.io.Serializable;

import application.arrayOfStrings;

public class GetCustomersCitiesCommand implements Serializable{

	//output
	private arrayOfStrings cities;

	public arrayOfStrings getCities() {
		return cities;
	}

	public void setCities(arrayOfStrings cities) {
		this.cities = cities;
	}
}
