package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import javafx.scene.paint.Color;

@DBEntity(tableName = "Crop")
public class Crop extends DBObject{

    @Override
    public String toString()
    {
        return getName();
    }

    @DBField(name = "Name")
    private String name;

    @DBField(name = "Defaultcolor")
    private Color defaultColor;

    @DBFKEntity(name= "DefaultVariety_ID", cascade = false)
    private Variety defaultVariety;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Variety getDefaultVariety() {
        return defaultVariety;
    }

    public void setDefaultVariety(Variety defaultVariety) {
        this.defaultVariety = defaultVariety;
    }
}
