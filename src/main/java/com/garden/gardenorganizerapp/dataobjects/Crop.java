package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import javafx.scene.paint.Color;

import java.sql.Date;

@DBEntity(tableName = "Crop")
public class Crop extends DBObject{

    @DBField(name = "Name")
    private String Name;

    @DBField(name="Defaultcolor")
    private Color defaultColor;

    @DBField(name="DefaultVariety_ID")
    private int defaultVarietyID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getDefaultVarietyID() {
        return defaultVarietyID;
    }

    public void setDefaultVarietyID(int defaultVarietyID) {
        this.defaultVarietyID = defaultVarietyID;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
}
