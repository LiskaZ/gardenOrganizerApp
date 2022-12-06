package com.garden.gardenorganizerapp;

import com.garden.gardenorganizerapp.viewcontrollers.IViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewLoader <T extends IViewController>{
    private FXMLLoader fxmlLoader;

    private Parent parent;

    public ViewLoader(String xml) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource(xml));
        parent = fxmlLoader.load();
        getController().createScene(parent, GardenApplication.THE_STAGE);
    }

    public T getController()
    {
        return fxmlLoader.getController();
    }
}