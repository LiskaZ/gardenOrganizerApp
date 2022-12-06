package com.garden.gardenorganizerapp;

import javafx.fxml.FXML;

import java.io.IOException;

public class GardenController {

    private GardenGridViewController project = null;
    private int height;
    private int width;

    @FXML
    protected void onEditGardenButtonClick() throws IOException {

    }

    public void setProject(GardenGridViewController project){
        this.project = project;
    }

}