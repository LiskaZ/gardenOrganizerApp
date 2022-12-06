package com.garden.gardenorganizerapp;

import java.util.Vector;

public class Garden {

    private int width;

    private Vector<PlantingArea> areas;

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
        this.width = normalizeCoord(w);
        this.height = normalizeCoord(h);
        this.name = name;
        this.areas = new Vector<PlantingArea>();
    }

    private int normalizeCoord(int coord)
    {
        if (coord % this.gridSize >= this.gridSize/2){
            return coord + (this.gridSize - coord % this.gridSize);
        } else {
            return coord - (coord % this.gridSize);
        }
    }

    public int normalizeCoordToGrid(double coord)
    {
        return (int)coord / this.gridSize;
    }

    public Vector<PlantingArea> getAreas() {
        return areas;
    }

    public void addPlantingArea(PlantingArea area)
    {
        areas.add(area);
    }
}
