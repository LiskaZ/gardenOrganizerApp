package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartViewController implements IViewController {
    @FXML
    private Label statusText;

    @FXML
    private Button stopButton;

    public StartViewController()
    {

    }

    @FXML
    protected void onNewButtonClick() throws IOException {
        statusText.setText("Ein neuer Garten wird angelegt...");
        ViewLoader<NewGardenViewController> l = new ViewLoader<NewGardenViewController>("new-garden-view.fxml");
    }

    @FXML
    protected void onEditButtonClick() {
        statusText.setText("Bestehender Garten wird aufgesucht...");

    }

    @FXML
    protected void onStopButtonClick() {
        statusText.setText("Anwendung wird beendet...");
        Stage stage = (Stage) this.stopButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void createScene(Parent p, Stage s, int sceneSize) {
        Scene scene = new Scene(p, sceneSize, sceneSize);
        s.setTitle("New Garden");
        s.setScene(scene);
        s.show();
    }
}