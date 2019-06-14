package application.gcmManager;
import java.io.IOException;
import java.util.ArrayList;

import Entities.Map;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class requestListController {

    @FXML
    private Button acceptBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<String> tableView;

    @FXML
    private TableColumn<String, String> cityNameColumn;

    @FXML
    private Button declineBtn;

    @FXML
    void userClickOnTable() {
        if(tableView.getSelectionModel().getSelectedItem()!=null) {
            this.acceptBtn.setDisable(false);
            this.declineBtn.setDisable(false);
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        System.out.println("Going back to GCM Manger services");
        Main.getInstance().goToGCMManagerServices();
    }

    @FXML
    void accept(ActionEvent event) throws IOException {
        String city = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(city);
        this.acceptBtn.setDisable(true);
        this.declineBtn.setDisable(true);
        Main.getInstance().updateDBAfterAccept(city);
    }

    @FXML
    void decline(ActionEvent event) throws IOException {
        String city = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(city);
        this.acceptBtn.setDisable(true);
        this.declineBtn.setDisable(true);
        Main.getInstance().updateDBAfterDecline(city);
    }

    public void getCities(ArrayList<String> cities) {
        ObservableList<String> tempCities = FXCollections.observableArrayList(cities);
        cityNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        tableView.setItems(tempCities);
    }

    @FXML
    void initialize() {
        this.acceptBtn.setDisable(true);
        this.declineBtn.setDisable(true);
    }

}