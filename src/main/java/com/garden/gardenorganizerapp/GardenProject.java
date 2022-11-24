package com.garden.gardenorganizerapp;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GardenProject {

    private final VBox gardenSettingsLayer;
    private final GardenController gardenController;

    private FXMLLoader fxmlLoader;
    private Stage stage;

    private Scene gardenSettings;
    private Scene garden;

    private int maxHeight;
    private int maxWidth;
    private String identifier;

    private Paint paint;

    public GardenProject(Stage stage) throws IOException {
        this.stage = stage;
        fxmlLoader = new FXMLLoader(getClass().getResource("garden-view.fxml"));
        gardenSettingsLayer = fxmlLoader.load();
        this.gardenSettings = new Scene(gardenSettingsLayer, 600, 600);
        stage.setScene(gardenSettings);

        this.gardenController = fxmlLoader.getController();
        gardenController.setProject(this);
    }

    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    public void setIdentifier(String name) {
        this.identifier = name;
    }

    public void createGardenLayer() {
        Canvas canvas = new Canvas(this.maxWidth, this.maxHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Paint.valueOf("#847743"));
        drawLines(gc);

        VBox gardenLayer = new VBox();
        gardenLayer.setAlignment(Pos.CENTER);
        gardenLayer.setBackground(new Background(
                new BackgroundFill(
                        new LinearGradient(0, 0, 0, 1, true,
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.GREEN),
                                new Stop(1, Color.GREEN)
                        ), CornerRadii.EMPTY, Insets.EMPTY
                )
        ));
        gardenLayer.getChildren().add(canvas);

        this.garden = new Scene(gardenLayer, 600, 600, Color.GREEN);

        stage.setScene(garden);
        stage.setTitle(identifier);
    }

    private void drawLines(GraphicsContext gc) {

        gc.beginPath();

        // TODO Skalieren, falls Garten größer als 600cm
        gc.fillRect(0,0, maxWidth, maxHeight);

    }
}

// https://zetcode.com/gui/javafx/canvas/