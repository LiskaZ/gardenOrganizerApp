package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;

public class PlantingSpot extends DBObject{

    private Color c;
    private int x;
    private int y;

    private int plantingAreaId = DBConnection.INVALID_ID;

    public PlantingSpot(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public Color getColor() {
        return c;
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
