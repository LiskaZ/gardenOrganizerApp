package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;

public class PlantingSpot extends DBObject{

    private int x;
    private int y;

    private int plantingAreaId = DBConnection.INVALID_ID;

    public PlantingSpot(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void setPlantingAreaId(int plantingAreaId) {
        this.plantingAreaId = plantingAreaId;
    }

    public int getPlantingAreaId() {
        return plantingAreaId;
    }
}
