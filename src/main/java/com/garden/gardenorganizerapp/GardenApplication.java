package com.garden.gardenorganizerapp;

import com.garden.gardenorganizerapp.dataobjects.Variety;
import com.garden.gardenorganizerapp.db.DBConnection;
import com.garden.gardenorganizerapp.db.VarietyDAO;
import com.garden.gardenorganizerapp.viewcontrollers.StartViewController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class GardenApplication extends Application {

    public static Stage THE_STAGE = null;

    private static DBConnection conn = null;

    @Override
    public void start(Stage stage) throws IOException {
        THE_STAGE = stage;
        new ViewLoader<StartViewController>("start-view.fxml");
    }

    public static void main(String[] args) {

        launch();
    }

    public static DBConnection getDBConnection()
    {
        if(null == conn)
        {
            conn = new DBConnection();
        }
        return conn;
    }
}

// TODO Gartengröße muss sich am Canvas anpassen, nicht an den Daten selbst

// TODO Markieren nicht mehr außerhalb des Gartens möglich

// TODO scaling nicht immer angewandt

// TODO remove spots from overlapping area definition