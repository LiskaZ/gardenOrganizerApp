package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.geometry.Point2D;

import java.util.Vector;

public class PlantingArea extends DBObject {

    private Item item;
    private Vector<PlantingSpot> spots;
    private int gardenId = DBConnection.INVALID_ID;


    public PlantingArea() {
        this.spots = new Vector<>();
    }

    public PlantingArea(Item item) {
        this();
        this.item = item;
    }


    public void setItem(Item item) {
        if(item != null) {
            this.item = item;
        }
    }

    public Item getItem() {
        return item;
    }

    public void setSpots(Vector<PlantingSpot> spots) {
        this.spots = spots;
    }

    public Vector<PlantingSpot> getSpots() {
        return spots;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getGardenId() {
        return gardenId;
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
        return this.spots.removeIf(spot -> spot.getX() == coord.getX() && spot.getY() == coord.getY());
    }
}
