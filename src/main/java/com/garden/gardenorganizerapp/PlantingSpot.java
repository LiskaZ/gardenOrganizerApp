package com.garden.gardenorganizerapp;

import javafx.scene.paint.Color;

public class PlantingSpot {

    private int x;
    private int y;
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
}
