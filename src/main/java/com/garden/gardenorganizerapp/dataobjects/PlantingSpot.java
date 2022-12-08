package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;

public class PlantingSpot {

    private int ID = DBConnection.INVALID_ID;

    private int plantingAreaId = DBConnection.INVALID_ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int y;

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Color getC() {
        return c;
    }

    public void setC(Color c) {
        this.c = c;
    }

    private int x;
    private Color c;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return c;
    }

    public PlantingSpot(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public int getPlantingAreaId() {
        return plantingAreaId;
    }

    public void setPlantingAreaId(int plantingAreaId) {
        this.plantingAreaId = plantingAreaId;
    }
}
