package commands;

import java.io.Serializable;
import java.time.LocalDate;

import Entities.Report;

public class GetCityReportCommand implements Serializable{

	//input
	private String cityName;
	private LocalDate fromDate;
	private LocalDate toDate;
	
	//output
	private Report report;

	public GetCityReportCommand(String cityName, LocalDate fromDate, LocalDate toDate) {
		this.cityName = cityName;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
	
}
