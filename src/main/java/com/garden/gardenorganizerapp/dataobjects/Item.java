package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;

@DBEntity(tableName = "Item")
public class Item extends DBObject{

    @DBField(name = "PlantingArea_ID")
    private int plantingAreaId = DBConnection.INVALID_ID;

    @DBField(name = "Color")
    private Color color;

    @DBField(name = "Variety_ID")
    private Integer variety_ID;

    @DBField(name = "Environment_ID")
    private Integer environment_ID;

    @DBField(name = "Anzahl")
    private int count;

    @DBFKEntity(name= "Item_ID")
    private Item item;

    public Item getItem() { return item; }

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

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
