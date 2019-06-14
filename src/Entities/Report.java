package Entities;

import java.io.Serializable;

public class Report implements Serializable{

	private int mapsCounter;
	private int oneTimeCounter;
	private int subscriptionsCounter;
	private int viewsCounter;
	private int downloadsCounter;

	public Report(int mapsCounter, int oneTimeCounter, int subscriptionsCounter, int viewsCounter, int downloadsCounter) {
		this.mapsCounter = mapsCounter;
		this.oneTimeCounter = oneTimeCounter;
		this.subscriptionsCounter = subscriptionsCounter;
		this.viewsCounter = viewsCounter;
		this.downloadsCounter = downloadsCounter;
	}
	public int getMapsCounter() {
		return mapsCounter;
	}
	public void setMapsCounter(int mapsCounter) {
		this.mapsCounter = mapsCounter;
	}
	public int getOneTimeCounter() {
		return oneTimeCounter;
	}
	public void setOneTimeCounter(int oneTimeCounter) {
		this.oneTimeCounter = oneTimeCounter;
	}
	public int getSubscriptionsCounter() {
		return subscriptionsCounter;
	}
	public void setSubscriptionsCounter(int subscriptionsCounter) {
		this.subscriptionsCounter = subscriptionsCounter;
	}
	public int getViewsCounter() {
		return viewsCounter;
	}
	public void setViewsCounter(int viewsCounter) {
		this.viewsCounter = viewsCounter;
	}
	public int getDownloadsCounter() {
		return downloadsCounter;
	}
	public void setDownloadsCounter(int downloadsCounter) {
		this.downloadsCounter = downloadsCounter;
	}
}
