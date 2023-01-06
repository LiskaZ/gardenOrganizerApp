package com.garden.gardenorganizerapp;

import com.garden.gardenorganizerapp.dataobjects.*;
import com.garden.gardenorganizerapp.viewcontrollers.GardenGridViewController;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class GardenWidget extends Canvas {

    private final Garden TheGarden;

    private final GardenGridViewController controller;
    private PlantingArea area = new PlantingArea();

    private Point2D mouseDraggingStartCoord = null;
    private Point2D currentMouseCoord = null;
    private Point2D currentMouseMoveCoordStartRec = null;
    private Point2D currentMouseMoveCoordEndRec = null;

    private int hoverlength = 0;
    private int hoverwidth = 0;
    private boolean turned = false;
    private double gridSize;

    public GardenWidget(Garden garden, GardenGridViewController gardenGridViewController) {
        super(garden.getWidth(), garden.getHeight());
        this.controller = gardenGridViewController;
        this.TheGarden = garden;

        drawGarden();
    }

    public PlantingArea getCurrentPlantingArea() {
        return area;
    }

    public void setPlantingArea() {
        this.area = new PlantingArea();
    }

    public void setItemInPlantingArea(Item item) {
        if (this.area.getItem() == null && item != null) {
            activateGridActions();
        }
        this.area.setItem(item);
        hoverwidth = 0;
        hoverlength = 0;
    }

    public void activateGridActions() {
        setOnMouseMoved(e -> onMouseMoved(e.getX(), e.getY()));
        setOnMouseClicked(e -> onMouseClicked(e.getX(), e.getY()));
        setOnMousePressed(e -> onMousePressed(e.getX(), e.getY()));
        setOnMouseDragged(e -> onMouseDragged(e.getX(), e.getY()));
        setOnMouseReleased(e -> onMouseReleased());
        setOnScroll(e -> System.out.println("Mausrad gerdreht"));
    }

    private void onMouseMoved(double x, double y) {
        // Maus entered Canvas

        int normX = TheGarden.normalizeCoordToGrid(x);
        int normY = TheGarden.normalizeCoordToGrid(y);
        if (currentMouseMoveCoordStartRec == null) {
            this.currentMouseMoveCoordStartRec = new Point2D(normX, normY);
        } else if (currentMouseMoveCoordStartRec.getX() != normX || currentMouseMoveCoordStartRec.getY() != normY) {
            this.currentMouseMoveCoordStartRec = new Point2D(normX, normY);
        }
        drawGarden();
    }

    public void onMousePressed(double x, double y) {
        this.mouseDraggingStartCoord = new Point2D(x, y);
    }

    public void onMouseDragged(double x, double y) {
        this.currentMouseCoord = new Point2D(x, y);
        drawGarden();
    }

    public void onMouseClicked(double x, double y) {
        if (isAllowedToHandleClick()) {
            Point2D gridCoords = toGridCoords(x, y);
            PlantingArea containingArea = getAreaContainingSpotCoords(gridCoords);
            if (containingArea != null && containingArea.removeSpot(gridCoords)) {
                controller.removeSpotFromDB(containingArea.getID(), new PlantingSpot(TheGarden.normalizeCoordToGrid(x), TheGarden.normalizeCoordToGrid(y)));
            } else {
                addSingleSpotToPlantingArea();
            }
        } else {
            enableHandleClick();
        }

        drawGarden();
    }

    public void onMouseReleased() {
        if (currentMouseCoord != null) {
            addSelectedSpotsToPlantingArea();
        }
        drawGarden();
    }


    private boolean isAllowedToHandleClick() {
        return this.currentMouseCoord == null;
    }

    private void enableHandleClick() {
        currentMouseCoord = null;
    }

    private PlantingArea getAreaContainingSpotCoords(Point2D gridCoords) {
        PlantingArea area = null;
        for (PlantingArea a : TheGarden.getAreas()) {
            if (a.containsSpotAt(gridCoords)) {
                area = a;
                break;
            }
        }
        if (null == area && this.area.containsSpotAt(gridCoords)) {
            area = this.area;
        }

        return area;
    }

    private Point2D toGridCoords(double x, double y) {
        return new Point2D(TheGarden.normalizeCoordToGrid(x), TheGarden.normalizeCoordToGrid(y));
    }

    private void addSingleSpotToPlantingArea() {
        assert currentMouseMoveCoordEndRec != null;
        for (int i = (int) currentMouseMoveCoordStartRec.getX(); i < currentMouseMoveCoordEndRec.getX(); i++) {
            for (int j = (int) currentMouseMoveCoordStartRec.getY(); j < currentMouseMoveCoordEndRec.getY(); j++) {
                area.addSpot(new Point2D(i, j));
            }
        }
    }

    private void addSelectedSpotsToPlantingArea() {
        int posStartX = TheGarden.normalizeCoordToGrid(mouseDraggingStartCoord.getX());
        int posStartY = TheGarden.normalizeCoordToGrid(mouseDraggingStartCoord.getY());

        int posEndX = TheGarden.normalizeCoordToGrid(this.currentMouseCoord.getX());
        int posEndY = TheGarden.normalizeCoordToGrid(this.currentMouseCoord.getY());

        if (posStartX > posEndX) {
            posStartX = TheGarden.normalizeCoordToGrid(this.currentMouseCoord.getX());
            posEndX = TheGarden.normalizeCoordToGrid(mouseDraggingStartCoord.getX());
        }
        if (posStartY > posEndY) {
            posStartY = TheGarden.normalizeCoordToGrid(this.currentMouseCoord.getY());
            posEndY = TheGarden.normalizeCoordToGrid(mouseDraggingStartCoord.getY());
        }

        double posFinalX = posStartX + hoverlength / gridSize - 1;
        double posFinalY = posStartY + hoverwidth / gridSize - 1;

        if (posFinalX < posEndX) {
            posFinalX = posEndX;
        }
        if (posFinalY < posEndY) {
            posFinalY = posEndY;
        }

        for (int x = posStartX; x <= posFinalX; ++x) {
            for (int y = posStartY; y <= posFinalY; ++y) {
                boolean spotAt = false;
                for (PlantingArea plantingArea : TheGarden.getAreas()) {
                    if (plantingArea.containsSpotAt(new Point2D(x, y))) {
                        spotAt = true;
                        break;
                    }
                }

                if (!spotAt) {
                    area.addSpot(new PlantingSpot(x, y));
                }
            }
        }

    }

    public void drawGarden() {
        drawGrid();
        drawSelectedSpots();
        drawPlantingAreas();
        drawPlantingArea(area);
        if (currentMouseMoveCoordStartRec != null) {
            drawHoverRect();
        }
    }

    private void drawGrid() {
        double sceneSize = controller.getGardenCanvas().getScene().getHeight();
        double gardenSize = sceneSize - 150;
        double width = TheGarden.getWidth();
        double height = TheGarden.getHeight();
        double gridSize = TheGarden.getGridSize();

        if (width > gardenSize && width > height){
            double percentage = gardenSize/ width;
            width = gardenSize - gardenSize % gridSize;
            height = (height * percentage) - ((height * percentage) % gridSize);
            this.gridSize = gridSize * percentage;
        }
        if (height > gardenSize && height > width){
            double percentage = gardenSize/ height;
            height = gardenSize - gardenSize % gridSize;
            width = (width * percentage) - ((width * percentage) % gridSize);
            this.gridSize = gridSize * percentage;
        }


        GraphicsContext gc = getGraphicsContext2D();
        gc.beginPath();
        gc.setFill(Paint.valueOf("#847743"));
        gc.fillRect(0, 0, width, height);

        gc.setStroke(Paint.valueOf("#625932"));

        for (int i = 0; i <= height; i += gridSize) {
            gc.strokeLine(0, i, width, i);
        }

        for (int i = 0; i <= width; i += gridSize) {
            gc.strokeLine(i, 0, i, height);
        }

    }

    private void drawSelectedSpots() {
        if (shouldDrawSelectionRect()) {

            double posStartX = mouseDraggingStartCoord.getX();
            double posEndX = this.currentMouseCoord.getX();

            double posStartY = mouseDraggingStartCoord.getY();
            double posEndY = this.currentMouseCoord.getY();

            if (posStartX > posEndX) {
                posStartX = this.currentMouseCoord.getX();
                posEndX = mouseDraggingStartCoord.getX();
            }

            if (posStartY > posEndY) {
                posStartY = this.currentMouseCoord.getY();
                posEndY = mouseDraggingStartCoord.getY();
            }

            posStartX = posStartX - posStartX % gridSize;
            posStartY = posStartY - posStartY % gridSize;

            double posFinalX = posEndX - posStartX;
            double posFinalY = posEndY - posStartY;

            GraphicsContext gc = getGraphicsContext2D();
            gc.setFill(new Color(0.5, 0.5, 0.5, 0.5));

            gc.beginPath();

            if (area.getItem() != null) {
                if (area.getItem().getVariety() == null) {
                    gc.fillRect(posStartX, posStartY, posFinalX, posFinalY);
                } else {
                    Variety v = area.getItem().getVariety();
                    setHoverlength(v);
                    if (posFinalX < hoverlength) {
                        posFinalX = hoverlength;
                    }
                    if (posFinalY < hoverwidth) {
                        posFinalY = hoverwidth;
                    }
                    gc.fillRect(posStartX, posStartY, posFinalX, posFinalY);
                }
            }
        }
    }

    private boolean shouldDrawSelectionRect() {
        return this.currentMouseCoord != null;
    }

    private void drawPlantingAreas() {
        for (PlantingArea area : TheGarden.getAreas()) {
            drawPlantingArea(area);
        }
    }

    private void drawPlantingArea(PlantingArea area) {
        if (area != null) {
            GraphicsContext gc = getGraphicsContext2D();
            double gSize = gridSize;
            for (PlantingSpot s : area.getSpots()) {
                gc.setFill(area.getItem().getColor());
                gc.fillRect(s.getX() * gSize + 1, s.getY() * gSize + 1, gSize - 2, gSize - 2);
            }
        }
    }

    private void drawHoverRect() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(new Color(0.5, 0.5, 0.5, 0.5));
        gc.beginPath();

        double posStartX = currentMouseMoveCoordStartRec.getX();
        double posStartY = currentMouseMoveCoordStartRec.getY();
        double g = gridSize;
        if (area.getItem() != null) {
            if (area.getItem().getVariety() == null) {
                gc.fillRect(posStartX * g, posStartY * g, g, g);
                this.currentMouseMoveCoordEndRec = new Point2D(posStartX + 1, posStartY + 1);
            } else {
                Variety v = area.getItem().getVariety();
                setHoverlength(v);
                gc.fillRect(posStartX * g, posStartY * g, hoverlength, hoverwidth);
                if (turned) {
                    this.currentMouseMoveCoordEndRec = new Point2D(posStartX + normalizeCoordToArea(v.getRowSpacing()), posStartY + normalizeCoordToArea(v.getPlantSpacing()));
                } else {
                    this.currentMouseMoveCoordEndRec = new Point2D(posStartX + normalizeCoordToArea(v.getPlantSpacing()), posStartY + normalizeCoordToArea(v.getRowSpacing()));
                }
            }
        }
    }

    private void setHoverlength(Variety v) {
        if (hoverwidth == 0 && hoverlength == 0){
            this.hoverlength = TheGarden.normalizeGrid(v.getPlantSpacing());
            this.hoverwidth = TheGarden.normalizeGrid(v.getRowSpacing());
        }
    }

    private int normalizeCoordToArea(double n) {
        if (n % gridSize == 0) {
            return (int) (n / gridSize);
        } else {
            return (int) (n / gridSize) + 1;
        }
    }

    public void turnHoverRect() {
        int turner = hoverlength;
        hoverlength = hoverwidth;
        hoverwidth = turner;
        turned = !turned;
    }
}