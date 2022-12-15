package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import javafx.scene.paint.Color;

@DBEntity(tableName = "Item")
public class Item extends DBObject{
    @DBField(name = "Color")
    private Color color;

    @DBFKEntity(name = "Variety_ID")
    private Variety variety;

    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @DBFKEntity(name = "Environment_ID")
    private Environment environment;

    @DBField(name = "Anzahl")
    private int count;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
