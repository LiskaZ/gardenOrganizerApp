package com.garden.gardenorganizerapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GardenWidget extends Canvas {

    private static int GRID_WIDTH = 20;

    private Garden TheGarden;
    public GardenWidget(Garden garden)
    {
        super(garden.getWidth(), garden.getHeight());
        this.TheGarden = garden;

        setOnMouseClicked(e -> {
            onMouseMoved(e.getX(), e.getY());
        });
        drawLines();
    }

    public void onMouseMoved(double x, double y)
    {
        GraphicsContext gc = getGraphicsContext2D();

        drawLines();

        gc.setFill(Color.ALICEBLUE);

        int gridX = (int)x / GRID_WIDTH;
        int gridY = (int)y / GRID_WIDTH;

        TheGarden.addSpot(new PlantingSpot(gridX, gridY, Color.GREEN));

        drawPlantingSpots();
    }

    private void drawLines()
    {
        GraphicsContext gc = getGraphicsContext2D();
        gc.beginPath();

        gc.setFill(Paint.valueOf("#847743"));
        gc.fillRect(0, 0, this.TheGarden.getWidth(), this.TheGarden.getHeight());

        gc.setStroke(Paint.valueOf("#625932"));

        for (int i = 0; i <= this.TheGarden.getWidth(); i+= GRID_WIDTH) {
            gc.strokeLine(0, i, this.TheGarden.getWidth(), i);
        }

        for (int i = 0; i <=  this.TheGarden.getHeight(); i+= GRID_WIDTH) {
            gc.strokeLine(i, 0, i,  this.TheGarden.getHeight());
        }

    }

    private void drawPlantingSpots()
    {
        GraphicsContext gc = getGraphicsContext2D();
        for(PlantingSpot s: TheGarden.getSpots())
        {
            gc.setFill(s.getC());
            gc.fillRect(s.getX() * GRID_WIDTH + 1, s.getY() * GRID_WIDTH + 1, GRID_WIDTH - 2, GRID_WIDTH - 2);
        }
    }
}
