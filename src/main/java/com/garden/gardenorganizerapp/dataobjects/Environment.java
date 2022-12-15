package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import javafx.scene.paint.Color;

@DBEntity(tableName = "Environment")
public class Environment extends DBObject{

    @DBField(name = "Name")
    private String name;
    @DBField(name = "Defaultcolor")
    private Color color;

    public Environment() {
    }
    
    public Environment(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

}
