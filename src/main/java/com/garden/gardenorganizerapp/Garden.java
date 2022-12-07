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

    // Prozent, um die der Garten verkleinert wurde
    private double percentage;

    public int getGridSize() {
        return gridSize;
    }

    private int gridSize = 20;

    public Garden(int w, int h, String name, int gridSize, double percentage)
    {
        this.gridSize = gridSize;
        this.width = normalizeGrid(w);
        System.out.println(String.format("Breite: %d, normalisierte Breite: %d", w, normalizeGrid(w)));
        this.height = normalizeGrid(h);
        System.out.println(String.format("Hoehe: %d, normalisierte Hoehe: %d", h, normalizeGrid(h)));
        this.name = name;
        this.areas = new Vector<PlantingArea>();
        this.percentage = percentage;
    }

    private int normalizeGrid(int n)
    {
        if (n % this.gridSize >= this.gridSize/2){
            return n + (this.gridSize - n % this.gridSize);
        } else {
            return n - (n % this.gridSize);
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
