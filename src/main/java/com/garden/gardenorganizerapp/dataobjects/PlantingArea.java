package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntityList;
import javafx.geometry.Point2D;

import java.util.Vector;

@DBEntity(tableName = "PlantingArea")
public class PlantingArea extends DBObject {

    @DBFKEntity(name = "Garden_ID", cascade = false)
    private Garden garden;

    @DBFKEntity(name = "Item_ID")
    private Item item;

    @DBFKEntityList(foreignType = PlantingSpot.class)
    private Vector<PlantingSpot> spots;

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        if(item != null) {
            this.item = item;
        }
    }

    public PlantingArea() {
        this.spots = new Vector<>();
    }

    public PlantingArea(Item item) {
        this();
        this.item = item;
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
        return this.spots.removeIf(spot -> spot.getX() == coord.getX() && spot.getY() == coord.getY());
    }
}
