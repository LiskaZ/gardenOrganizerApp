package com.garden.gardenorganizerapp;

import com.garden.gardenorganizerapp.db.DBConnection;
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

// TODO 03: scaling nicht immer angewandt, Beispelgröße benötigt

// TODO 04: gute Nachbarn einbauen

// TODO 05: Empfehlungen für Anzahl Pflanzen

// TODO 06: Seasons einführen mit Button zum Starten einer neuen Saison
// TODO 07: gute Pflanznachfolger anhand Season einbauen
// TODO 08: Übersicht Seasons auswählbar machen
// TODO 09: Übersicht gepflanzter Pflanzen
// TODO 10: Kalender einbauen

// TODO 11: Entfernen von Pflanzen erleichtern - größere Mengen entfernbar, nicht nur per Einzelklick, evtl. eigener Button?

// TODO Sobald Area ohne Spots, muss Area archiviert werden
