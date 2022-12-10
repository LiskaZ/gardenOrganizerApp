package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Vector;

public class PlantingArea extends DBObject {

    private int gardenId = DBConnection.INVALID_ID;

    private Color color;

    private Vector<PlantingSpot> spots;

    public PlantingArea(Color c) {
        this.color = c;
        this.spots = new Vector<>();
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setSpots(Vector<PlantingSpot> spots) {
        this.spots = spots;
    }

    public Vector<PlantingSpot> getSpots() {
        return spots;
    }

    public void addSpot(PlantingSpot spot) {
        this.spots.add(spot);
    }

    public void addSpot(Point2D coord) {
        this.spots.add(new PlantingSpot((int) coord.getX(), (int) coord.getY()));
    }

    public boolean containsSpotAt(Point2D coord) {
        return this.spots.stream().anyMatch(spot -> coord.getX() == spot.getX() && coord.getY() == spot.getY());
    }

    public boolean removeSpot(Point2D coord) {
        boolean spotRemoved = this.spots.removeIf(spot -> spot.getX() == coord.getX() && spot.getY() == coord.getY());
        return spotRemoved;
    }
}
