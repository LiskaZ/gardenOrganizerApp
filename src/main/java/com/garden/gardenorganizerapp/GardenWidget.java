package com.garden.gardenorganizerapp;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Vector;

public class GardenWidget extends Canvas {

    private Garden TheGarden;

    private Point2D mouseDraggingStartCoord = null;

    private Point2D currentMouseCoord = null;

    private PlantingArea area = null;

    public GardenWidget(Garden garden)
    {
        super(garden.getWidth(), garden.getHeight());
        this.TheGarden = garden;

        setOnMouseClicked(e -> {
            onMouseClicked(e.getX(), e.getY());
        });

        setOnMousePressed(e -> {
            onMousePressed(e.getX(), e.getY());
        });

        setOnMouseDragged(e -> {
            onMouseDragged(e.getX(), e.getY());
        });

        setOnMouseReleased(e -> {
            onMouseReleased(e.getX(), e.getY());
        });

        drawGarden();
    }

    public void onMousePressed(double x, double y) {
        System.out.println(String.format("onMousePressed: %b", shouldDrawSelectionRect()));
        this.mouseDraggingStartCoord = new Point2D(x,y);
    }

    public void onMouseDragged(double x, double y){
        System.out.println(String.format("onMouseDragged: %b", shouldDrawSelectionRect()));

        this.currentMouseCoord = new Point2D(x, y);

        drawGarden();
    }

    public void onMouseReleased(double x, double y)
    {
        if(currentMouseCoord != null) {
            addSelectedSpotsToPlantingArea();
            currentMouseCoord = null;
        }
        drawGarden();
    }

    public void onMouseClicked(double x, double y)
    {
        System.out.println(String.format("onMouseClicked: Dragging: %b", isAllowedToHandleClick()));
        if (isAllowedToHandleClick())
        {
            currentMouseCoord = new Point2D(x, y);
            addSelectedSpotsToPlantingArea();
        }
        else
        {
            enableHandleClick();
        }

        drawGarden();
    }

    private void addSelectedSpotsToPlantingArea()
    {
        if(isAllowedToHandleClick())
        {
            int gridX = TheGarden.normalizeCoordToGrid(currentMouseCoord.getX());
            int gridY = TheGarden.normalizeCoordToGrid(currentMouseCoord.getY());

            area.addSpot(new PlantingSpot(gridX, gridY, Color.YELLOWGREEN));
        }
        else
        {
            double posStartX = mouseDraggingStartCoord.getX();
            double posStartY = mouseDraggingStartCoord.getY();

            double posEndX = this.currentMouseCoord.getX();
            double posEndY = this.currentMouseCoord.getY();

            if (posStartX > posEndX) {
                posStartX = this.currentMouseCoord.getX();
                posEndX = mouseDraggingStartCoord.getX();
            }
            if (posStartY > posEndY) {
                posStartY = this.currentMouseCoord.getY();
                posEndY = mouseDraggingStartCoord.getY();
            }

            if(area == null)
            {
                area = new PlantingArea();
            }
            for(int x = TheGarden.normalizeCoordToGrid(posStartX); x < TheGarden.normalizeCoordToGrid(posEndX); ++x)
            {
                for(int y = TheGarden.normalizeCoordToGrid(posStartY); y < TheGarden.normalizeCoordToGrid(posEndY); ++y)
                {
                    area.addSpot(new PlantingSpot(x, y, Color.YELLOWGREEN));
                }
            }
        }
    }

    private boolean isAllowedToHandleClick()
    {
        return currentMouseCoord == null;
    }

    private void enableHandleClick()
    {
        currentMouseCoord = null;
    }

    private void drawGrid()
    {
        GraphicsContext gc = getGraphicsContext2D();
        gc.beginPath();

        gc.setFill(Paint.valueOf("#847743"));
        gc.fillRect(0, 0, this.TheGarden.getWidth(), this.TheGarden.getHeight());

        gc.setStroke(Paint.valueOf("#625932"));

        for (int i = 0; i <= this.TheGarden.getWidth(); i+= TheGarden.getGridSize()) {
            gc.strokeLine(0, i, this.TheGarden.getWidth(), i);
        }

        for (int i = 0; i <=  this.TheGarden.getHeight(); i+= TheGarden.getGridSize()) {
            gc.strokeLine(i, 0, i,  this.TheGarden.getHeight());
        }

    }

    private void drawGarden()
    {
        drawGrid();
        drawSelectionRect();
        drawPlantingAreas();
        drawPlantingArea(area);
    }

    private boolean shouldDrawSelectionRect()
    {
        return this.currentMouseCoord != null;
    }

    private void drawSelectionRect()
    {
        if(shouldDrawSelectionRect()) {
            GraphicsContext gc = getGraphicsContext2D();
            gc.setFill(new Color(0.5, 0.5, 0.5, 0.5));

            gc.beginPath();
            double posStartX = mouseDraggingStartCoord.getX();
            double posStartY = mouseDraggingStartCoord.getY();

            double posEndX = this.currentMouseCoord.getX();
            double posEndY = this.currentMouseCoord.getY();

            if (posStartX > posEndX) {
                posStartX = this.currentMouseCoord.getX();
                posEndX = mouseDraggingStartCoord.getX();
            }
            if (posStartY > posEndY) {
                posStartY = this.currentMouseCoord.getY();
                posEndY = mouseDraggingStartCoord.getY();
            }
            gc.fillRect(posStartX, posStartY, posEndX - posStartX, posEndY - posStartY);
        }
    }

    private void drawPlantingAreas()
    {
        for(PlantingArea area: TheGarden.getAreas())
        {
            drawPlantingArea(area);
        }
    }

    private void drawPlantingArea(PlantingArea area)
    {
        if(area != null) {
            GraphicsContext gc = getGraphicsContext2D();
            for (PlantingSpot s : area.getSpots()) {
                gc.setFill(s.getColor());
                gc.fillRect(s.getX() * TheGarden.getGridSize() + 1, s.getY() * TheGarden.getGridSize() + 1, TheGarden.getGridSize() - 2, TheGarden.getGridSize() - 2);
            }
        }
    }
}
