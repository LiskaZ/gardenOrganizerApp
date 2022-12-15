package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;

import java.time.LocalDate;

public class PlantingSpot extends DBObject{

    private int x;
    private int y;
    private LocalDate date;

    private int plantingAreaId = DBConnection.INVALID_ID;

    public PlantingSpot(int x, int y) {
        this.x = x;
        this.y = y;
        this.date = LocalDate.now();
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
        return  date.toString();
    }

    public void setDate(LocalDate plantDate) {
        this.date = plantDate;
    }

    public void setPlantingAreaId(int plantingAreaId) {
        this.plantingAreaId = plantingAreaId;
    }

    public int getPlantingAreaId() {
        return plantingAreaId;
    }
}
