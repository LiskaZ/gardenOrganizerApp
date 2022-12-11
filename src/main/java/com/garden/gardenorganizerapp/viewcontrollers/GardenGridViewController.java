package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.dataobjects.Environment;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.GardenWidget;
import com.garden.gardenorganizerapp.dataobjects.Item;
import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;
import com.garden.gardenorganizerapp.db.EnvironmentDAO;
import com.garden.gardenorganizerapp.db.GardenDAO;
import com.garden.gardenorganizerapp.db.PlantingSpotDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventListener;
import java.util.Vector;

public class GardenGridViewController implements IViewController {

    private BorderPane gardenSettingsLayer;

    @FXML
    private VBox gardenCanvas;

    private Scene gardenScene;

    private Button button;

    private Garden garden;

    private GardenWidget gardenWidget;

    private Stage stage;

    @FXML
    private MenuBar menuBar;

    @FXML
    private ComboBox cropDropDown;

    @FXML
    private ListView varietyList;

    @FXML
    private VBox buttonLayout;

    @FXML
    Button newBed;

    public GardenGridViewController() throws IOException {
    }

    public void createScene(Parent gardenSettingsLayer, Stage s, int sceneSize) {
        this.stage = s;
        this.gardenSettingsLayer = (BorderPane) gardenSettingsLayer;
        this.gardenScene = new Scene(this.gardenSettingsLayer, sceneSize, sceneSize);
        s.setScene(gardenScene);
        createMenu(menuBar);
        loadCrops();
        loadEnvironment();
    }

    private void loadEnvironment() {

        EnvironmentDAO dao = new EnvironmentDAO();
        Vector<Environment> environments = dao.loadAll();
        for (Environment env : environments) {
            Button envButton = new Button(env.getName());
            envButton.setPrefWidth(124.0);
            envButton.setId(String.format("envButton_%d", env.getID()));
            envButton.setOnAction(e -> {
                int s = Integer.parseInt(envButton.getId().split("_")[1]);
                gardenWidget.setItem(new Item(env.getColor(), null, s));
            });
            buttonLayout.getChildren().add(envButton);
        }
    }

    private void loadCrops() {
        // TODO Liste aus DB abrufen
        ObservableList<String> items = FXCollections.observableArrayList("Stein", "Gras", "Tomate", "Gurke");
        cropDropDown.setItems(items);

        cropDropDown.getSelectionModel().selectedItemProperty().addListener(x -> {

            String crop = cropDropDown.getSelectionModel().getSelectedItem().toString();
            ObservableList<String> varietyItems = FXCollections.observableArrayList(crop + "Stein", crop + "Gras", crop + "Tomate", crop + "Gurke");
            varietyList.setItems(varietyItems);
            varietyList.getSelectionModel().select(0);
        });

        cropDropDown.getSelectionModel().select(0);
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
        gardenWidget = new GardenWidget(this.garden);
        gardenWidget.setController(this);
        createGardenLayer();
    }

    public void createGardenLayer() {

        gardenCanvas.getChildren().add(gardenWidget);
        this.gardenScene.setFill(Color.GREEN);

        stage.setTitle(this.garden.getName());
    }

    public void onNewBedClick(ActionEvent actionEvent) {

        garden.addPlantingArea(gardenWidget.getCurrentPlantingArea());

        GardenDAO dao = new GardenDAO();
        dao.store(this.garden);
        gardenWidget.newPlantingArea();

    }

    public void onAddPlantsClick(ActionEvent actionEvent) {
    }

    //TODO Spot hier wirklich entfernen oder Pflanze entfernen??
    public void removeSpotFromDB(int areaID, PlantingSpot gridCoords) {
        PlantingSpotDAO dao = new PlantingSpotDAO();
        dao.removeSpot(areaID, gridCoords);
    }
}