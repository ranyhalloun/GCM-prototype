package commands;

import java.io.Serializable;

public class GetPricesCommand implements Serializable{

	//output
	private String oldSubs;
	private String oldOne;
	private String newSubs;
	private String newOne;
	
	public String getOldSubs() {
		return oldSubs;
	}
	public void setOldSubs(String oldSubs) {
		this.oldSubs = oldSubs;
	}
	public String getOldOne() {
		return oldOne;
	}
	public void setOldOne(String oldOne) {
		this.oldOne = oldOne;
	}
	public String getNewSubs() {
		return newSubs;
	}
	public void setNewSubs(String newSubs) {
		this.newSubs = newSubs;
	}
	public String getNewOne() {
		return newOne;
	}
	public void setNewOne(String newOne) {
		this.newOne = newOne;
	}
	
}
