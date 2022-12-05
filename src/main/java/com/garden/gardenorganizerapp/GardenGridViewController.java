package com.garden.gardenorganizerapp;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class GardenGridViewController {

    private VBox gardenSettingsLayer;

    private Scene gardenScene;

    private Garden garden;

    private GardenWidget gardenWidget;

    public GardenGridViewController() throws IOException
    {

    }

    public void setNode(Parent gardenSettingsLayer)
    {
        this.gardenSettingsLayer = (VBox)gardenSettingsLayer;
        this.gardenScene = new Scene(this.gardenSettingsLayer, 600, 600);
        OurStage.THE_STAGE.setScene(gardenScene);
    }

    public void setGarden(Garden garden)
    {
        this.garden = garden;
        gardenWidget = new GardenWidget(this.garden);
        createGardenLayer();
    }

    public void createGardenLayer() {

        gardenSettingsLayer.getChildren().add(gardenWidget);
        gardenSettingsLayer.setAlignment(Pos.CENTER);
        this.gardenScene.setFill(Color.GREEN);

        OurStage.THE_STAGE.setTitle(this.garden.getName());
    }
}