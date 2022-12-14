package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;

import java.util.Vector;

@DBEntity(tableName = "Garden")
public class Garden extends DBObject{

    @Override
    public String toString()
    {
        return getName();
    }

    @DBField(name = "Width")
    private int width;

    @DBField(name = "Height")
    private int height;

    @DBField(name = "Name")
    private String name;

    @DBField(name = "GridSize")
    private int gridSize = 20;
    
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Prozent, um die der Garten verkleinert wurde
    private double percentage;

    public int getGridSize() {
        return gridSize;
    }

    public Garden()
    {
        this.gridSize = 0;
        this.width = 0;
        this.height = 0;
        this.name = "";
        this.areas = new Vector<PlantingArea>();
        this.percentage = 0.0;
    }

    public Garden(int w, int h, String name, int gridSize, double percentage)
    {
        this.gridSize = gridSize;
        this.width = normalizeGrid(w);
        this.height = normalizeGrid(h);
        this.name = name;
        this.areas = new Vector<PlantingArea>();
        this.percentage = percentage;
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

    public Vector<PlantingArea> getAreas() {
        return areas;
    }

    public void addPlantingArea(PlantingArea area)
    {
        if(null != area) {
            areas.add(area);
        }
    }

    public void setPlantingAreas(Vector<PlantingArea> areas) {
        this.areas = areas;
    }
}
