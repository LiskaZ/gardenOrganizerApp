package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.dataobjects.*;
import com.garden.gardenorganizerapp.GardenWidget;
import com.garden.gardenorganizerapp.db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

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
    private Spinner plantCount;

    @FXML
    Button newBed;

    public GardenGridViewController() throws IOException {
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

    public void createScene(Parent gardenSettingsLayer, Stage s, int sceneSize) {
        this.stage = s;
        this.gardenSettingsLayer = (BorderPane) gardenSettingsLayer;
        this.gardenScene = new Scene(this.gardenSettingsLayer, sceneSize, sceneSize);
        s.setScene(gardenScene);
        createMenu(menuBar);
        loadCrops();
        loadVariety(1);
        loadEnvironmentItem();
    }

    private void loadCrops() {

        CropDAO dao = new CropDAO();
        ArrayList<Crop> crops = new ArrayList<>();
        crops.addAll(dao.loadAll());

        ObservableList<Crop> cropList = FXCollections.observableArrayList(crops);
        cropDropDown.setItems(cropList);
        cropDropDown.getSelectionModel().select(0);

        cropDropDown.getSelectionModel().selectedItemProperty().addListener(x -> {

            Crop selectedCrop = (Crop) cropDropDown.getSelectionModel().getSelectedItem();
            loadVariety(selectedCrop.getID());
        });
    }

    private void loadVariety(int cropID) {
        VarietyDAO vDao = new VarietyDAO();
        ArrayList<Variety> varietyNames = new ArrayList<>();
        varietyNames.addAll(vDao.loadVarietyForCrop(cropID));

        ObservableList<Variety> varietyItems = FXCollections.observableArrayList(varietyNames);
        varietyList.setItems(varietyItems);
        varietyList.getSelectionModel().select(0);
        varietyList.getSelectionModel().selectedItemProperty().addListener(x -> {
            Variety selectedVariety = (Variety) varietyList.getSelectionModel().getSelectedItem();
            loadVarietyItem(selectedVariety);
        });
    }

    private void loadVarietyItem(Variety selectedVariety) {
        if (selectedVariety != null) {
            Item item = new Item();
            item.setVariety_ID(selectedVariety.getID());
            item.setColor(selectedVariety.getDefaultColor());
            gardenWidget.setItemInPlantingArea(item);
        }
    }

    private void loadEnvironmentItem() {

        EnvironmentDAO dao = new EnvironmentDAO();
        Vector<Environment> environments = dao.loadAll();
        for (Environment env : environments) {
            Button envButton = new Button(env.getName());
            envButton.setPrefWidth(124.0);
            envButton.setId(String.format("envButton_%d", env.getID()));
            envButton.setOnAction(e -> {
                int s = Integer.parseInt(envButton.getId().split("_")[1]);
                Item item = new Item();
                item.setColor(env.getColor());
                item.setEnvironment_ID(s);
                item.setCount(1);
                gardenWidget.setItemInPlantingArea(item);
            });
            buttonLayout.getChildren().add(envButton);
        }
    }

    public void onAddPlantsClick(ActionEvent actionEvent) {
        PlantingArea currentArea = gardenWidget.getCurrentPlantingArea();
        currentArea.getItem().setCount((Integer) plantCount.getValue());
        garden.addPlantingArea(currentArea);

        GardenDAO dao = new GardenDAO();
        dao.store(this.garden);
        gardenWidget.setPlantingArea();
    }

    //TODO Spot hier wirklich entfernen oder Pflanze entfernen??
    public void removeSpotFromDB(int areaID, PlantingSpot gridCoords) {
        PlantingSpotDAO dao = new PlantingSpotDAO();
        dao.removeSpot(areaID, gridCoords);
    }

    public void turnItem(ActionEvent actionEvent) {
        gardenWidget.turnHoverRect();
        gardenWidget.drawGarden();
    }
}