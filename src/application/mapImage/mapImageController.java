package application.mapImage;


import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Entities.Attraction;
import Entities.Coordinates;
import Entities.Map;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;


public class mapImageController {

    private enum Operation
    {
        AddOperation(1),
        EditOperation(2),
        RemoveOperation(3),
        UNDEFINED(4);

        private int operationValue;

        Operation(int value) {
             this.operationValue = value;
        }

        public int getOperationValue() {
             return this.operationValue;
        }

        public String getOperationName() {
             return this.name();
        }
    }

    private Map map;
    private String attractionName;
    private String cityName;
    private String description;
    private Coordinates coordinates;
    private ArrayList<Attraction> attractions;
    private ArrayList<Circle> markers;
    private ArrayList<Text> texts;
    private int markerIndex;
    private Operation op;
    private boolean firstAttemptRemove;
    private Image image;


    public mapImageController(Map map, String attractionName, String cityName, String description) {
        this.map = map;
        this.image = new Image(this.map.getImagePath());
        this.attractionName = attractionName;
        this.cityName = cityName;
        this.description = description;
        this.attractions = map.getAttractions();
        this.op = Operation.UNDEFINED;
        this.coordinates = new Coordinates();
        this.markers = new ArrayList<Circle>();
        this.texts = new ArrayList<Text>();
        this.markerIndex = -1;
        this.firstAttemptRemove = true;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addAttractionBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField attractionNameField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button editAttractionBtn;

    @FXML
    private ImageView imgView;

    @FXML
    private Label msgToUser;

    @FXML
    private TextField isAccessbleField;

    @FXML
    private Button removeAttractionBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button discardBtn;

    @FXML
    private AnchorPane imgViewWrapAnchorPane;

    
    @FXML
    void download(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)imgViewWrapAnchorPane.getWidth() + 20,
                        (int)imgViewWrapAnchorPane.getHeight() + 20);
                imgViewWrapAnchorPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) { ex.printStackTrace(); }
        }
        switch (Main.getInstance().getUserType()) {
        case Customer:
            try {
                Main.getInstance().incrementNumDownloadsOfMap(this.map.getMapID(), this.cityName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        break;
        default:
            break;
        }
        this.msgToUser.setText("Downloaded.");
    }

    @FXML
    void addAttractionToMap(ActionEvent event) {
        resetInfo();
        resetFields();
        this.op = Operation.AddOperation;
        this.msgToUser.setText("Mark a location to add attraction.");
        setEnableEditing(true);
    }

    @FXML
    void editAttractionInMap(ActionEvent event) {
        this.firstAttemptRemove = true;
        this.op = Operation.EditOperation;
        this.msgToUser.setText("Your are editing now.");
        setEnableEditing(true);
    }

    @FXML
    void removeAttractionFromMap(ActionEvent event) {
        if (markerIndex == -1) {
            this.msgToUser.setText("Select an attraction you would like to remove.");
            return;
        }
        setEnableEditing(false);
        this.op = Operation.RemoveOperation;
        if (this.firstAttemptRemove) {
            this.msgToUser.setText("Select 'Remove Attraction' again to remove the selected attraction.");
            this.firstAttemptRemove = false;
        } else {
            handleRemoveAttraction();
        }
    }

    @FXML
    void saveAttractionChanges(ActionEvent event) {

        switch (this.op) {
        case AddOperation:
            saveAddAttraction();
            break;
        case EditOperation:
            saveEditAttraction();
            break;
        default:
            break;
        }

        // Current attractions after the change
        System.out.println("Attractions in this map: ");
        for (Attraction attr : this.attractions) {
            attr.print();
        }
        System.out.println("Num of Markers: " + this.markers.size());
        System.out.println("Num of attractions: " + this.attractions.size());
        this.texts.get(markerIndex).setText(this.attractions.get(markerIndex).getName());
        // Update the database with the new attractions.
        this.markerIndex = -1;
        resetInfo();
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveAddAttraction() {
        System.out.println("saveAddAttraction");
        Attraction attraction = new Attraction(this.attractionNameField.getText(), this.categoryField.getText(), this.descriptionField.getText(),
                Boolean.valueOf(this.isAccessbleField.getText()), cityName, this.coordinates);
        this.attractions.add(attraction);
        // update the database
        try {
            Main.getInstance().addNewAttractionToMap(this.map.getMapID(), attraction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveEditAttraction() {
        System.out.println("saveEditAttraction");
//        Attraction attraction = new Attraction(this.attractionNameField.getText(), this.categoryField.getText(), this.descriptionField.getText(),
//                Boolean.valueOf(this.isAccessbleField.getText()), cityName, this.coordinates);
        if (markerIndex < 0 || markerIndex >= attractions.size()) {
            System.out.println("Error occured when saving edited attraction.");
            return;
        }
        Attraction attraction = this.attractions.get(markerIndex);
        attraction.setName(this.attractionNameField.getText());
        attraction.setCategory(this.categoryField.getText());
        attraction.setDescription(this.descriptionField.getText());
        attraction.setAccessible(Boolean.valueOf(this.isAccessbleField.getText()));
        attraction.setLocation(this.coordinates);


        //TODO:
        //update the database

        try {
            Main.getInstance().editAttractionInMap(this.map.getMapID(), attraction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void handleRemoveAttraction() {
        System.out.println("handleRemoveAttraction");
        if (markerIndex < 0 || markerIndex >= attractions.size()) {
            System.out.println("Error occured when removing...");
            resetInfo();
            return;
        }
        imgViewWrapAnchorPane.getChildren().remove(markers.get(markerIndex));
        imgViewWrapAnchorPane.getChildren().remove(texts.get(markerIndex));
        try {
            Main.getInstance().removeAttractionFromMap(this.map.getMapID(), this.attractions.get(markerIndex).getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.attractions.remove(markerIndex);
        this.markers.remove(markerIndex);
        this.texts.remove(markerIndex);
        resetInfo();
        resetFields();
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.msgToUser.setText("Attraction removed!");
    }

    @FXML
    void discardChanges(MouseEvent event) {
        resetInfo();
        resetFields();
    }

    @FXML
    void getCoordinates(MouseEvent event) {
        if (this.op == Operation.AddOperation) {
            handleAddOperationWhenClicked(event);
        } else if (this.op == Operation.EditOperation) {
            handleEditOperationWhenClicked(event);
        }
    }

    void handleAddOperationWhenClicked(MouseEvent event) {
        System.out.println("handleAddOperationWhenClicked");
        this.coordinates.setX_cord(event.getX());
        this.coordinates.setY_cord(event.getY());
        if (markerIndex != -1) {
            imgViewWrapAnchorPane.getChildren().remove(markers.get(markerIndex));
            imgViewWrapAnchorPane.getChildren().remove(texts.get(markerIndex));
            markers.get(markerIndex).setCenterX(this.coordinates.getX_cord());
            markers.get(markerIndex).setCenterY(this.coordinates.getY_cord());
            texts.get(markerIndex).setX(this.coordinates.getX_cord());
            texts.get(markerIndex).setY(this.coordinates.getY_cord()-15);

        } else {
            Circle marker = new Circle(this.coordinates.getX_cord(), this.coordinates.getY_cord(), 10, Color.PURPLE);
            marker.setOnMouseClicked(me -> handleMarkerPressed(this.markers.indexOf(marker)));
            markers.add(marker);
            markerIndex = markers.size()-1;
            Text text = new Text(this.coordinates.getX_cord(), this.coordinates.getY_cord()-15, "");
            text.setFont(new Font(20));
            texts.add(text);
        }
        imgViewWrapAnchorPane.getChildren().add(markers.get(markerIndex));
        imgViewWrapAnchorPane.getChildren().add(texts.get(markerIndex));
        this.msgToUser.setText("Enter Attraction Details and press 'Save'");
        setEnableEditing(true);
    }

    void handleEditOperationWhenClicked(MouseEvent event) {
        System.out.println("handleEditOperationWhenClicked");
        System.out.println("MARKERS SIZE: " + this.markers.size());
        if (markerIndex != -1) {
            this.coordinates.setX_cord(event.getX());
            this.coordinates.setY_cord(event.getY());
            if (!imgViewWrapAnchorPane.getChildren().remove(markers.get(markerIndex))) {
                System.out.println("MONIKAAAAAAA");
                System.out.println("Marker Index: " + markerIndex);
            }
            imgViewWrapAnchorPane.getChildren().remove(texts.get(markerIndex));
            markers.get(markerIndex).setCenterX(event.getX());
            markers.get(markerIndex).setCenterY(event.getY());
            texts.get(markerIndex).setX(event.getX());
            texts.get(markerIndex).setY(event.getY()-15);
            imgViewWrapAnchorPane.getChildren().add(markers.get(markerIndex));
            imgViewWrapAnchorPane.getChildren().add(texts.get(markerIndex));
        }
    }

    private void handleMarkerPressed(int markerIndex) {
        if (this.op == Operation.AddOperation)
            return;
        // Deleted
        if (markerIndex == -1) {
            System.out.println("You are selecting deleted marker!");
            return;
        }
        this.markerIndex = markerIndex;
        System.out.printf("Marker Index: %d%nMarker Coordinates: (%f,%f)%nAttraction: %n", markerIndex, markers.get(markerIndex).getCenterX(),
                markers.get(markerIndex).getCenterY());
        Attraction attr = attractions.get(markerIndex);
        attr.print();
        this.attractionNameField.setText(attr.getName());
        this.categoryField.setText(attr.getCategory());
        this.isAccessbleField.setText(Boolean.toString(attr.getIsAccessible()));
        this.descriptionField.setText(attr.getDescription());
    }

    void setEnableEditing(boolean bool) {
        this.attractionNameField.setEditable(bool);
        this.categoryField.setEditable(bool);
        this.isAccessbleField.setEditable(bool);
        this.descriptionField.setEditable(bool);
    }

    private void showAttractionsInMap() {
        System.out.printf("Showing %d attractions in the map!%n", this.attractions.size());
        for (Attraction attr : this.attractions) {
            attr.print();
            Circle marker = new Circle(attr.getLocation().getX_cord(), attr.getLocation().getY_cord(), 10, Color.PURPLE);
            markers.add(marker);
            marker.setOnMouseClicked(me -> handleMarkerPressed(this.markers.indexOf(marker)));
            imgViewWrapAnchorPane.getChildren().add(marker);
            Text text = new Text(attr.getLocation().getX_cord(), attr.getLocation().getY_cord()-15, attr.getName());
            text.setFont(new Font(20));
            texts.add(text);
            imgViewWrapAnchorPane.getChildren().add(text);
        }
    }

    private void resetInfo() {
        if ((this.op == Operation.AddOperation) &&  markerIndex == this.attractions.size()) {
            imgViewWrapAnchorPane.getChildren().remove(markers.get(markerIndex));
            imgViewWrapAnchorPane.getChildren().remove(texts.get(markerIndex));
            this.markers.remove(markerIndex);
            this.texts.remove(markerIndex);
        }
        if (this.op == Operation.EditOperation) {
            if (!(markerIndex < 0 || markerIndex >= this.attractions.size())) {
                markers.get(markerIndex).setCenterX(attractions.get(markerIndex).getLocation().getX_cord());
                markers.get(markerIndex).setCenterY(attractions.get(markerIndex).getLocation().getY_cord());
                texts.get(markerIndex).setX(attractions.get(markerIndex).getLocation().getX_cord());
                texts.get(markerIndex).setY(attractions.get(markerIndex).getLocation().getY_cord()-15);
            }

//            imgViewWrapAnchorPane.getChildren().remove(markers.get(markerIndex));
//            imgViewWrapAnchorPane.getChildren().remove(texts.get(markerIndex));

//            imgViewWrapAnchorPane.getChildren().add(markers.get(markerIndex));
//            imgViewWrapAnchorPane.getChildren().add(texts.get(markerIndex));
        }
        this.firstAttemptRemove = true;
        this.markerIndex = -1;
        this.coordinates = new Coordinates();
        setEnableEditing(false);
        this.op = Operation.UNDEFINED;
        this.msgToUser.setText("");
    }

    private void resetFields() {
        this.attractionNameField.setText("");
        this.categoryField.setText("");
        this.isAccessbleField.setText("");
        this.descriptionField.setText("");
        this.msgToUser.setText("");
    }
    
    private void save() throws IOException {
        this.map = Main.getInstance().GetMapInfoFromID(map.getMapID());
        imgViewWrapAnchorPane.getChildren().clear();
        imgViewWrapAnchorPane.getChildren().add(imgView);
        this.attractions.clear();
        this.attractions = map.getAttractions();
        this.op = Operation.UNDEFINED;
        this.coordinates = new Coordinates();
        this.markers.clear();
        this.texts.clear();
        this.markerIndex = -1;
        this.firstAttemptRemove = true;
        initialize();
        this.msgToUser.setText("Saved.");
    }

    @FXML
    void initialize() {
        this.imgView.setImage(image);
        System.out.println("Image URL: " + this.map.getImagePath());
        setEnableEditing(false);
        showAttractionsInMap();
        switch (Main.getInstance().getUserType()) {
        case Customer:
        case Worker:
            this.addAttractionBtn.setVisible(false);
            this.removeAttractionBtn.setVisible(false);
            this.editAttractionBtn.setVisible(false);
            this.saveBtn.setVisible(false);
            this.discardBtn.setVisible(false);
            break;
        default:
            break;
        }
    }
}