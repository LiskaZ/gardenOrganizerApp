package com.garden.gardenorganizerapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label statusText;

    @FXML
    private Button stopButton;

    @FXML
    protected void onNewButtonClick() throws IOException {
        statusText.setText("Ein neuer Garten wird angelegt...");
        ViewLoader<NewGardenViewController> l = new ViewLoader<NewGardenViewController>("new-garden-view.fxml");
        l.getController().setNode(l.getNode());
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
}