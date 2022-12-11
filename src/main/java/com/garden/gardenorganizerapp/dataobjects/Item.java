package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;

public class Item extends DBObject{

    private int plantingAreaId = DBConnection.INVALID_ID;

    private Color color;

    private Integer variety_ID;

    private Integer environment_ID;

    public Item(Color color, Integer variety_ID, Integer environment_ID) {
        this.color = color;
        this.variety_ID = variety_ID;
        this.environment_ID = environment_ID;
    }

    public void setPlantingAreaId(int plantingAreaId) {
        this.plantingAreaId = plantingAreaId;
    }

    public int getPlantingAreaId() {
        return plantingAreaId;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setVariety_ID(Integer variety_ID) {
        this.variety_ID = variety_ID;
    }

    public Integer getVariety_ID() {
        return variety_ID;
    }

    public void setEnvironment_ID(Integer environment_ID) {
        this.environment_ID = environment_ID;
    }

    public Integer getEnvironment_ID() {
        return environment_ID;
    }
}
