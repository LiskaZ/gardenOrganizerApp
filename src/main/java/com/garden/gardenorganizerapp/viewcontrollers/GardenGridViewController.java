package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.Garden;
import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.GardenWidget;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GardenGridViewController implements IViewController {

    private VBox gardenSettingsLayer;

    @FXML
    private VBox gardenCanvas;

    @FXML
    private Label gardenTitle;

    private Scene gardenScene;

    private Garden garden;

    private GardenWidget gardenWidget;

    private Stage stage;

    public GardenGridViewController() throws IOException
    {

    }

    public void createScene(Parent gardenSettingsLayer, Stage s, int sceneSize)
    {
        this.stage = s;
        this.gardenSettingsLayer = (VBox) gardenSettingsLayer;
        this.gardenScene = new Scene(this.gardenSettingsLayer, sceneSize, sceneSize);
        s.setScene(gardenScene);
    }

    public void setGarden(Garden garden)
    {
        this.garden = garden;
        this.gardenTitle.setText(garden.getName());
        gardenWidget = new GardenWidget(this.garden);
        createGardenLayer();
    }

    public void createGardenLayer() {

        gardenCanvas.getChildren().add(gardenWidget);
        gardenSettingsLayer.setAlignment(Pos.CENTER);
        this.gardenScene.setFill(Color.GREEN);

        stage.setTitle(this.garden.getName());
    }

    public void onNewBedClick(ActionEvent actionEvent) {

    }

    public void onAddPlantsClick(ActionEvent actionEvent) {
    }
}