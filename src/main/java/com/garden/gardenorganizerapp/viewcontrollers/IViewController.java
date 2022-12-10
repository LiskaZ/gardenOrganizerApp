package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.ViewLoader;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public interface IViewController {


    void createScene(Parent p, Stage s, int sceneSize);

    default void createMenu(MenuBar menuBar) {
        MenuItem newGarden = new MenuItem("Neuer Garten");
        newGarden.setOnAction(actionEvent -> {
            try {
                new ViewLoader<>("new-garden-view.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MenuItem loadGarden = new MenuItem("Garten laden");
        loadGarden.setOnAction(e -> {
            try {
                new ViewLoader<>("select-existing-garden-view.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        MenuItem saveGarden = new MenuItem("Garten speichern");
        MenuItem closeGardenApp = new MenuItem("Garten verlassen");
        Menu startMenu = new Menu("Start");
        startMenu.getItems().addAll(newGarden, loadGarden, saveGarden, closeGardenApp);

        MenuItem calendar = new MenuItem("Pflanzkalender");
        MenuItem countPlants = new MenuItem("Pflanzenübersicht");
        Menu gardenhelpersMenu = new Menu("Gartenhelfer");
        gardenhelpersMenu.getItems().addAll(countPlants, calendar);

        MenuItem plants = new MenuItem("Pflanzen");
        MenuItem beds = new MenuItem("Beete");
        Menu overview = new Menu("Übersicht");
        overview.getItems().addAll(plants, beds);

        menuBar.getMenus().addAll(startMenu, gardenhelpersMenu, overview);
    }

}
