package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.updateCityRequest;

public class GetNewVersionsCommand implements Serializable{

	//output
	private ArrayList<updateCityRequest> newVersions;

	public ArrayList<updateCityRequest> getNewVersions() {
		return newVersions;
	}

	public void setNewVersions(ArrayList<updateCityRequest> newVersions) {
		this.newVersions = newVersions;
	}
}