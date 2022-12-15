package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.ViewLoader;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.db.GardenDAO;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;

public class SelectExistingGardenViewController implements IViewController{

    private Scene gardenScene;

    private int sceneSize;

    @FXML
    private ListView gardenList;

    @FXML
    private MenuBar menuBar;

    public SelectExistingGardenViewController() {

    }

    public void createScene(Parent p, Stage s, int sceneSize) {
        this.sceneSize = sceneSize;
        this.gardenScene = new Scene(p, sceneSize, sceneSize);
        s.setScene(gardenScene);
        createMenu(menuBar);

        GardenDAO d = new GardenDAO();
        Vector<Garden> gardens = d.loadAllLazy();

        var observableList = FXCollections.observableArrayList();
        observableList.addAll(gardens);
        gardenList.setItems(observableList);

        gardenList.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Garden>) (observableValue, old, newObj) -> {
            try {
                GardenDAO d1 = new GardenDAO();
                Garden garden = d1.load(newObj.getID());
                ViewLoader<GardenGridViewController> l = new ViewLoader<>("garden-grid-view.fxml");
                l.getController().setGarden(garden);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}