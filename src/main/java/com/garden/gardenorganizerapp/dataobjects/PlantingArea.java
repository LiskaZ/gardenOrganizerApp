package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.geometry.Point2D;

import java.util.Vector;

public class PlantingArea extends DBObject {

    private int gardenId = DBConnection.INVALID_ID;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    private int itemID;

    private Vector<PlantingSpot> spots;

    public PlantingArea(int itemID) {
        this.itemID = itemID;
        this.spots = new Vector<>();
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getGardenId() {
        return gardenId;
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
