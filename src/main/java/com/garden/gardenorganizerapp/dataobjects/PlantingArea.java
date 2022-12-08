package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;

import java.util.Vector;

public class PlantingArea {

    private int ID = DBConnection.INVALID_ID;

    private int gardenId = DBConnection.INVALID_ID;

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
