package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Item extends DBObject{

    private String name;

    private Color color;

    private int itemId = DBConnection.INVALID_ID;

    public Item(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public Color getColor() {
        return color;
    }
}
