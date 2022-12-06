package com.garden.gardenorganizerapp;

import java.util.Vector;

public class Garden {

    private int width;

    private Vector<PlantingSpot> spots;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getGridSize() {
        return gridSize;
    }

    private int gridSize = 20;

    public Garden(int w, int h, String name, int gridSize)
    {
        this.gridSize = gridSize;
        this.width = evaluateGardenSize(w);
        this.height = evaluateGardenSize(h);
        this.name = name;
        this.spots = new Vector<PlantingSpot>();
    }

    private int evaluateGardenSize(int length) {

        if (length % this.gridSize >= this.gridSize/2){
            return length + (this.gridSize - length % this.gridSize);
        } else {
            return length - (length % this.gridSize);
        }
    }

    public Vector<PlantingSpot> getSpots() {
        return spots;
    }

    public void addSpot(PlantingSpot spot)
    {
        spots.add(spot);
    }
}
