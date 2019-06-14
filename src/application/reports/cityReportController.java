package application.reports;
import java.io.IOException;

import Entities.Report;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class cityReportController {

	private Report report;
	private String city;
	public cityReportController(Report report, String city) {
		this.report =report;
		this.city = city;
	}
	
    @FXML
    private Label subscriptions;

    @FXML
    private Label cityName;

    @FXML
    private Label maps;

    @FXML
    private Label downloads;

    @FXML
    private Button backBtn;

    @FXML
    private Label oneTimePurchases;

    @FXML
    private Label views;

    @FXML
    void back(ActionEvent event) throws IOException {
    	Main.getInstance().goToCitiesReport("");
    }


    @FXML
    void initialize() {
    	cityName.setText(city);
        maps.setText(Integer.toString(report.getMapsCounter()));
        oneTimePurchases.setText(Integer.toString(report.getOneTimeCounter()));
        subscriptions.setText(Integer.toString(report.getSubscriptionsCounter()));
        views.setText(Integer.toString(report.getViewsCounter()));
        downloads.setText(Integer.toString(report.getDownloadsCounter()));
    }
}
