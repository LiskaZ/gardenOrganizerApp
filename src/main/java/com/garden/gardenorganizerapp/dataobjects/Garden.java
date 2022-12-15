package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;

import java.util.Vector;

public class Garden extends DBObject {

    private String name;
    private int width;
    private int height;
    private Vector<PlantingArea> areas;
    private int gridSize = 20;
    private double percentage;

    public Garden(int w, int h, String name, int gridSize, double percentage) {
        this.gridSize = gridSize;
        this.width = normalizeGrid(w);
        this.height = normalizeGrid(h);
        this.name = name;
        this.areas = new Vector<PlantingArea>();
        this.percentage = percentage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setPlantingAreas(Vector<PlantingArea> areas) {
        this.areas = areas;
    }

    public Vector<PlantingArea> getAreas() {
        return areas;
    }

    public int getGridSize() {
        return gridSize;
    }

    @Override
    public String toString() {
        return getName();
    }


    public int normalizeGrid(int n) {
        if (n % this.gridSize >= this.gridSize / 2) {
            return n + (this.gridSize - n % this.gridSize);
        } else {
            return n - (n % this.gridSize);
        }
    }

    public int normalizeCoordToGrid(double coord) {
        return (int) coord / this.gridSize;
    }

    public void addPlantingArea(PlantingArea area) {
        if (null != area) {
            areas.add(area);
        }
    }
}
