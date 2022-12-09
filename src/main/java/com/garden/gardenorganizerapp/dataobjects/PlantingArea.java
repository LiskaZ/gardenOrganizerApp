package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;

import java.util.Vector;

public class PlantingArea extends DBObject{

    private int gardenId = DBConnection.INVALID_ID;

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    private Vector<PlantingSpot> spots;

    public PlantingArea()
    {
        this.spots = new Vector<PlantingSpot>();
    }

    public PlantingArea(Vector<PlantingSpot> spots)
    {
        this.spots = spots;
    }

    public void addSpot(PlantingSpot spot)
    {
        this.spots.add(spot);
    }

    public Vector<PlantingSpot> getSpots() {
        return spots;
    }

    public void setSpots(Vector<PlantingSpot> spots) {
        this.spots = spots;
    }
}
