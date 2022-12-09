package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.ViewLoader;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.db.GardenDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;

public class SelectExistingGardenViewController implements IViewController{

    private Scene gardenScene;

    private int sceneSize;

    @FXML
    private ListView gardenList;

    public SelectExistingGardenViewController() throws IOException {

    }

    public void createScene(Parent p, Stage s, int sceneSize) {
        this.sceneSize = sceneSize;
        this.gardenScene = new Scene(p, sceneSize, sceneSize);
        s.setScene(gardenScene);

        GardenDAO d = new GardenDAO();
        Vector<Garden> gardens = d.loadAllLazy();

        ObservableList observableList = FXCollections.observableArrayList();
        for(Garden g: gardens) {
            observableList.add(g);
        }
        gardenList.setItems(observableList);

        gardenList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Garden>() {
            @Override
            public void changed(ObservableValue observableValue, Garden old, Garden newObj) {
                try {
                    GardenDAO d = new GardenDAO();
                    Garden garden = d.load(newObj.getID());
                    ViewLoader<GardenGridViewController> l = new ViewLoader<GardenGridViewController>("garden-grid-view.fxml");
                    l.getController().setGarden(garden);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}