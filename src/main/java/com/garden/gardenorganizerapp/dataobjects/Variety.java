package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBFKEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import javafx.scene.paint.Color;

import java.sql.Date;
import java.util.Optional;

@DBEntity(tableName = "Variety")
public class Variety extends DBObject{

    @DBField(name = "Name")
    private String Name;
    @DBField(name="HarvestTime_Begin")
    private java.sql.Date harvestTimeBegin;
    private java.sql.Date harvestTimeEnd;
    private java.sql.Date plantTimeBegin;
    private java.sql.Date plantTimeEnd;
    @DBField(name="PlantSpacing")
    private int plantSpacing;
    @DBField(name="RowSpacing")
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

    @DBFKEntity(name = "Crop_ID")
    private Crop crop;

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setHarvestTimeBegin(Date harvestTimeBegin) {
        this.harvestTimeBegin = harvestTimeBegin;
    }

    public Date getHarvestTimeBegin() {
        return harvestTimeBegin;
    }

    public void setHarvestTimeEnd(Date harvestTimeEnd) {
        this.harvestTimeEnd = harvestTimeEnd;
    }

    public Date getHarvestTimeEnd() {
        return harvestTimeEnd;
    }

    public void setPlantTimeBegin(Date plantTimeBegin) {
        this.plantTimeBegin = plantTimeBegin;
    }

    public Date getPlantTimeBegin() {
        return plantTimeBegin;
    }

    public void setPlantTimeEnd(Date plantTimeEnd) {
        this.plantTimeEnd = plantTimeEnd;
    }

    public Date getPlantTimeEnd() {
        return plantTimeEnd;
    }

    public void setPlantSpacing(int plantSpacing) {
        this.plantSpacing = plantSpacing;
    }

    public int getPlantSpacing() {
        return plantSpacing;
    }

    public void setRowSpacing(int rowSpacing) {
        this.rowSpacing = rowSpacing;
    }

    public int getRowSpacing() {
        return rowSpacing;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSeedingDepth(int seedingDepth) {
        this.seedingDepth = seedingDepth;
    }

    public int getSeedingDepth() {
        return seedingDepth;
    }

    public void setSeedingTemp(int seedingTemp) {
        this.seedingTemp = seedingTemp;
    }

    public int getSeedingTemp() {
        return seedingTemp;
    }

    public void setSeedingBedDepth(int seedingBedDepth) {
        this.seedingBedDepth = seedingBedDepth;
    }

    public int getSeedingBedDepth() {
        return seedingBedDepth;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public Color getDefaultColor() {
        return defaultColor != null ? defaultColor : crop.getDefaultColor();
    }

    @Override
    public String toString()
    {
        return getName();
    }



    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Crop getCrop() {
        return crop;
    }
}
