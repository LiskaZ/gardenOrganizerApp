package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import javafx.scene.paint.Color;

import java.sql.Date;

@DBEntity(tableName = "Crop")
public class Crop extends DBObject{

    @DBField(name = "Name")
    private String Name;

    @DBField(name="Defaultcolor")
    private Color defaultColor;

    @DBFKEntity(name="DefaultVariety_ID", cascade = false)
    private Variety defaultVariety;

    public Variety getDefaultVariety() {
        return defaultVariety;
    }

    public void setDefaultVariety(Variety defaultVariety) {
        this.defaultVariety = defaultVariety;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
