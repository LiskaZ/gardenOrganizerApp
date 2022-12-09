package com.garden.gardenorganizerapp;

import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;
import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GardenWidget extends Canvas {

    private Garden TheGarden;

    private Point2D mouseDraggingStartCoord = null;

    private Point2D currentMouseCoord = null;

    private PlantingArea area = null;

    private Color color = Color.YELLOWGREEN;

    public GardenWidget(Garden garden) {
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
        this.mouseDraggingStartCoord = new Point2D(x, y);
    }

    public void onMouseDragged(double x, double y) {
        this.currentMouseCoord = new Point2D(x, y);
        drawGarden();
    }

    public void onMouseReleased(double x, double y) {
        if (currentMouseCoord != null) {
            addSelectedSpotsToPlantingArea();
        }
        drawGarden();
    }

    public void onMouseClicked(double x, double y) {
        if (isAllowedToHandleClick()) {
            addSingleSpotToPlantingArea(x, y);
        } else {
            enableHandleClick();
        }

        drawGarden();
    }

    private void addSingleSpotToPlantingArea(double x, double y) {
        if (area == null) {
            area = new PlantingArea();
        }

        Vector<PlantingSpot> spots = area.getSpots();

        int gridX = TheGarden.normalizeCoordToGrid(x);
        int gridY = TheGarden.normalizeCoordToGrid(y);
        boolean spotDeleted = spots.removeIf(spot -> {
            return spot.getX() == gridX && spot.getY() == gridY;
        });

        if (!spotDeleted){
            area.addSpot(new PlantingSpot(gridX, gridY, color));
        }

    }


    private void addSelectedSpotsToPlantingArea() {
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

        if (area == null) {
            area = new PlantingArea();
        }
        for (int x = TheGarden.normalizeCoordToGrid(posStartX); x <= TheGarden.normalizeCoordToGrid(posEndX); ++x) {
            for (int y = TheGarden.normalizeCoordToGrid(posStartY); y <= TheGarden.normalizeCoordToGrid(posEndY); ++y) {
                area.addSpot(new PlantingSpot(x, y, color));
            }
        }
    }

    private boolean isAllowedToHandleClick() {
        return this.currentMouseCoord == null;
    }

    private void enableHandleClick() {
        currentMouseCoord = null;
    }

    private void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.beginPath();

        gc.setFill(Paint.valueOf("#847743"));
        gc.fillRect(0, 0, this.TheGarden.getWidth(), this.TheGarden.getHeight());

        gc.setStroke(Paint.valueOf("#625932"));

        for (int i = 0; i <= this.TheGarden.getHeight(); i += TheGarden.getGridSize()) {
            gc.strokeLine(0, i, this.TheGarden.getWidth(), i);
        }

        for (int i = 0; i <= this.TheGarden.getWidth(); i += TheGarden.getGridSize()) {
            gc.strokeLine(i, 0, i, this.TheGarden.getHeight());
        }

    }

    private void drawGarden() {
        drawGrid();
        drawSelectionRect();
        drawPlantingAreas();
        drawPlantingArea(area);
    }

    private boolean shouldDrawSelectionRect() {
        return this.currentMouseCoord != null;
    }

    private void drawSelectionRect() {
        if (shouldDrawSelectionRect()) {
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

    private void drawPlantingAreas() {
        for (PlantingArea area : TheGarden.getAreas()) {
            drawPlantingArea(area);
        }
    }

    private void drawPlantingArea(PlantingArea area) {
        if (area != null) {
            GraphicsContext gc = getGraphicsContext2D();
            double gSize = TheGarden.getGridSize();
            for (PlantingSpot s : area.getSpots()) {
                gc.setFill(s.getColor());
                gc.fillRect(s.getX() * gSize + 1, s.getY() * gSize + 1, gSize - 2, gSize - 2);
            }
        }
    }

    public void newPlantingArea() {
        ArrayList<Color> colors = new ArrayList(
            List.of(Color.YELLOWGREEN, Color.ROYALBLUE, Color.ORANGE, Color.OLIVEDRAB, Color.INDIGO)
        );

        this.area = null;
        this.color = colors.get( ( colors.indexOf(color) +1) % colors.size() );
    }

    public PlantingArea getCurrentPlantingArea() {
        return area;
    }
}
