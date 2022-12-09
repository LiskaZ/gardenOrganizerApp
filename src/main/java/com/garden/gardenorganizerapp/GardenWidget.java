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

    private static final ArrayList<Color> COLORS = new ArrayList(
            List.of(Color.YELLOWGREEN, Color.ROYALBLUE, Color.ORANGE, Color.OLIVEDRAB, Color.INDIGO)
    );

    private Garden TheGarden;

    private Point2D mouseDraggingStartCoord = null;

    private Point2D currentMouseCoord = null;

    private PlantingArea area = null;

    private Color color = COLORS.get(0);

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

    // TODO write changes to DB
    public void onMouseClicked(double x, double y) {
        if (isAllowedToHandleClick()) {
            Point2D gridCoords = toGridCoords(x, y);
            PlantingArea containingArea = getAreaContainingSpotCoords(gridCoords);
            if(containingArea != null)
            {
                containingArea.removeSpot(gridCoords);
            }
            else
            {
                addSingleSpotToPlantingArea(gridCoords);
            }
        } else {
            enableHandleClick();
        }

        drawGarden();
    }

    private PlantingArea getAreaContainingSpotCoords(Point2D gridCoords) {
        PlantingArea area = null;
        for(PlantingArea a: TheGarden.getAreas())
        {
            if(a.containsSpotAt(gridCoords))
            {
                area = a;
                break;
            }
        }
// TODO refactor!!!!
        if(null == area && null != this.area)
        {
            if(this.area.containsSpotAt(gridCoords))
            {
                area = this.area;
            }
        }

        return area;
    }

    private Point2D toGridCoords(double x, double y)
    {
        return new Point2D(TheGarden.normalizeCoordToGrid(x), TheGarden.normalizeCoordToGrid(y));
    }

    private void addSingleSpotToPlantingArea(Point2D coord) {
        if (area == null) {
            area = new PlantingArea(color);
        }

        area.addSpot(coord);
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
            area = new PlantingArea(color);
        }
        for (int x = TheGarden.normalizeCoordToGrid(posStartX); x <= TheGarden.normalizeCoordToGrid(posEndX); ++x) {
            for (int y = TheGarden.normalizeCoordToGrid(posStartY); y <= TheGarden.normalizeCoordToGrid(posEndY); ++y) {
                area.addSpot(new PlantingSpot(x, y));
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
                gc.setFill(area.getColor());
                gc.fillRect(s.getX() * gSize + 1, s.getY() * gSize + 1, gSize - 2, gSize - 2);
            }
        }
    }

    public void newPlantingArea() {
        this.area = null;
        this.color = COLORS.get( ( COLORS.indexOf(color) + 1) % COLORS.size() );
    }

    public PlantingArea getCurrentPlantingArea() {
        return area;
    }
}
