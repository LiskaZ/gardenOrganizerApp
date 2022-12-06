package com.garden.gardenorganizerapp;

import java.util.Vector;

public class PlantingArea {

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
}
