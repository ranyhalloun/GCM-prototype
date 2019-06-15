package commands;

import java.io.Serializable;
import java.util.ArrayList;

import Entities.updateCityRequest;

public class GetManagerNotifCommand implements Serializable{

	//output
	private ArrayList<updateCityRequest> notifis;

	public ArrayList<updateCityRequest> getNotifis() {
		return notifis;
	}

	public void setNotifis(ArrayList<updateCityRequest> notifis) {
		this.notifis = notifis;
	}
}
