package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.GardenWidget;
import com.garden.gardenorganizerapp.db.GardenDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public GardenGridViewController() throws IOException
    {

    }

    public void createScene(Parent gardenSettingsLayer, Stage s, int sceneSize)
    {
        this.stage = s;
        this.gardenSettingsLayer = (BorderPane) gardenSettingsLayer;
        this.gardenScene = new Scene(this.gardenSettingsLayer, sceneSize, sceneSize);
        s.setScene(gardenScene);
        createMenu(menuBar);
    }

    public void setGarden(Garden garden)
    {
        this.garden = garden;
        gardenWidget = new GardenWidget(this.garden);
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
}