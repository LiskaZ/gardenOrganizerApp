package com.garden.gardenorganizerapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewLoader <T>{
    private FXMLLoader fxmlLoader;

    private Parent node;

    public ViewLoader(String xml) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource(xml));
        node = fxmlLoader.load();
    }

    public T getController()
    {
        return fxmlLoader.getController();
    }

    public Parent getNode()
    {
        return node;
    }
}