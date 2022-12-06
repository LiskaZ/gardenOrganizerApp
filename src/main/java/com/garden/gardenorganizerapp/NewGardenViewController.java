package com.garden.gardenorganizerapp;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class NewGardenViewController {

    private Scene gardenScene;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField heightIntegerField;

    @FXML
    private TextField widthIntegerField;

    @FXML
    private Button createGarden;

    public NewGardenViewController() throws IOException {

    }

    public void setNode(Parent p)
    {
        this.gardenScene = new Scene((VBox)p, 600, 600);
        OurStage.THE_STAGE.setScene(gardenScene);
    }
    public void onCreateGardenButtonClick() throws IOException {
        int width = parseInt(widthIntegerField.getText().isEmpty() ? "0" : widthIntegerField.getText());
        int height = parseInt(heightIntegerField.getText().isEmpty() ? "0" : heightIntegerField.getText());

        ViewLoader<GardenGridViewController> l = new ViewLoader<GardenGridViewController>("garden-grid-view.fxml");
        l.getController().setNode(l.getNode());
        l.getController().setGarden(new Garden(height, width, titleTextField.getText()));
    }
}