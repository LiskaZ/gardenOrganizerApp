package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;

import java.time.LocalDate;

@DBEntity(tableName = "PlantingSpot")
public class PlantingSpot extends DBObject{

    @DBField(name = "x")
    private int x;
    @DBField(name = "y")
    private int y;
    @DBField(name = "PlantDate")
    private LocalDate plantDate;
    @DBField(name = "EndDate")
    private LocalDate endDate;

    @DBFKEntity(name = "PlantingArea_ID")
    private PlantingArea plantingArea;

    public LocalDate getPlantDate() {
        return plantDate;
    }

    public void setPlantDate(LocalDate plantDate) {
        this.plantDate = plantDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public PlantingArea getPlantingArea() {
        return plantingArea;
    }

    public void setPlantingArea(PlantingArea plantingArea) {
        this.plantingArea = plantingArea;
    }

    public PlantingSpot() {
    }

    public PlantingSpot(int x, int y) {
        this.x = x;
        this.y = y;
        this.plantDate = LocalDate.now();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public String getDate() {
        return  plantDate.toString();
    }
}
