package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;

public class Environment extends DBObject{

    private String name;
    private Color color;
    private int environmentId = DBConnection.INVALID_ID;


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

    public void setEnvironmentId(int environmentId) {
        this.environmentId = environmentId;
    }

    public int getEnvironmentId() {
        return environmentId;
    }
}
