package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import javafx.scene.paint.Color;

import java.sql.Date;

@DBEntity(tableName = "Variety")
public class Variety extends DBObject{

    @DBField(name = "Crop_ID")
    private int Crop_ID;
    @DBField(name = "Name")
    private String Name;
    @DBField(name="HarvestTime_Begin")
    private java.sql.Date harvestTimeBegin;
    private java.sql.Date harvestTimeEnd;
    private java.sql.Date plantTimeBegin;
    private java.sql.Date plantTimeEnd;
    @DBField(name="PlantSpacing")
    private int plantSpacing;
    @DBField(name="PlantSpacing")
    private int rowSpacing;
    @DBField(name="Size")
    private int size;
    @DBField(name="SeedingDepth")
    private int seedingDepth;
    @DBField(name="SeedingTemp")
    private int seedingTemp;
    @DBField(name="SeedingBedDepth")
    private int seedingBedDepth;
    @DBField(name="Defaultcolor")
    private Color defaultColor;

    public int getCrop_ID() {
        return Crop_ID;
    }

    public void setCrop_ID(int Crop_ID) {
        this.Crop_ID = Crop_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Date getHarvestTimeBegin() {
        return harvestTimeBegin;
    }

    public void setHarvestTimeBegin(Date harvestTimeBegin) {
        this.harvestTimeBegin = harvestTimeBegin;
    }

    public Date getHarvestTimeEnd() {
        return harvestTimeEnd;
    }

    public void setHarvestTimeEnd(Date harvestTimeEnd) {
        this.harvestTimeEnd = harvestTimeEnd;
    }

    public Date getPlantTimeBegin() {
        return plantTimeBegin;
    }

    public void setPlantTimeBegin(Date plantTimeBegin) {
        this.plantTimeBegin = plantTimeBegin;
    }

    public Date getPlantTimeEnd() {
        return plantTimeEnd;
    }

    public void setPlantTimeEnd(Date plantTimeEnd) {
        this.plantTimeEnd = plantTimeEnd;
    }

    public int getPlantSpacing() {
        return plantSpacing;
    }

    public void setPlantSpacing(int plantSpacing) {
        this.plantSpacing = plantSpacing;
    }

    public int getRowSpacing() {
        return rowSpacing;
    }

    public void setRowSpacing(int rowSpacing) {
        this.rowSpacing = rowSpacing;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSeedingDepth() {
        return seedingDepth;
    }

    public void setSeedingDepth(int seedingDepth) {
        this.seedingDepth = seedingDepth;
    }

    public int getSeedingTemp() {
        return seedingTemp;
    }

    public void setSeedingTemp(int seedingTemp) {
        this.seedingTemp = seedingTemp;
    }

    public int getSeedingBedDepth() {
        return seedingBedDepth;
    }

    public void setSeedingBedDepth(int seedingBedDepth) {
        this.seedingBedDepth = seedingBedDepth;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }
}
