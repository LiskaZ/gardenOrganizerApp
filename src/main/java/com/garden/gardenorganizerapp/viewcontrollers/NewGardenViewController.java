package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.Garden;
import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class NewGardenViewController implements IViewController{

    private Scene gardenScene;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField heightIntegerField;

    @FXML
    private TextField widthIntegerField;

    @FXML
    private TextField gridSizeIntegerField;

    @FXML
    private Button createGarden;

    public NewGardenViewController() throws IOException {

    }

    public void createScene(Parent p, Stage s)
    {
        this.gardenScene = new Scene(p, 600, 600);
        s.setScene(gardenScene);
    }
    public void onCreateGardenButtonClick() throws IOException {
        int width = readNumber(widthIntegerField);
        int height = readNumber(heightIntegerField);
        int gridSize = readNumber(gridSizeIntegerField);

        ViewLoader<GardenGridViewController> l = new ViewLoader<GardenGridViewController>("garden-grid-view.fxml");
        l.getController().setGarden(new Garden(height, width, titleTextField.getText(), gridSize));
    }

    private int readNumber (TextField t) {
        return parseInt(t.getText().isEmpty() ? "0" : t.getText());
    }
}