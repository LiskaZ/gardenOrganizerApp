package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.GardenWidget;
import com.garden.gardenorganizerapp.dataobjects.Item;
import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;
import com.garden.gardenorganizerapp.db.GardenDAO;
import com.garden.gardenorganizerapp.db.PlantingSpotDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GardenGridViewController implements IViewController {

    private BorderPane gardenSettingsLayer;

    @FXML
    private VBox gardenCanvas;

    private Scene gardenScene;

    private Garden garden;

    private GardenWidget gardenWidget;

    private Stage stage;

    @FXML
    private MenuBar menuBar;

    @FXML
    private ListView itemList;

    @FXML
    Button newBed;

    public GardenGridViewController() throws IOException {}

    public void createScene(Parent gardenSettingsLayer, Stage s, int sceneSize)
    {
        this.stage = s;
        this.gardenSettingsLayer = (BorderPane) gardenSettingsLayer;
        this.gardenScene = new Scene(this.gardenSettingsLayer, sceneSize, sceneSize);
        s.setScene(gardenScene);
        createMenu(menuBar);
        createItemList();
    }

    private void createItemList() {
        ObservableList<String> items = FXCollections.observableArrayList("Tomate", "Gurke", "Zucchini", "Kürbis", "Karotten", "Steine", "Gras");
        itemList.setItems(items);

        itemList.getSelectionModel().selectedItemProperty().addListener(x -> {
            String selectedItem = (String) itemList.getSelectionModel().getSelectedItem();
            gardenWidget.setItem(new Item(selectedItem, Color.INDIGO));
        });

    }

    public void setGarden(Garden garden)
    {
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
        gardenWidget.newPlantingArea();
        GardenDAO dao = new GardenDAO();
        dao.store(this.garden);
    }

    public void onAddPlantsClick(ActionEvent actionEvent) {
    }

    public void removeSpotFromDB(int areaID, PlantingSpot gridCoords) {
        PlantingSpotDAO dao = new PlantingSpotDAO();
        dao.removeSpot(areaID, gridCoords);
    }
}