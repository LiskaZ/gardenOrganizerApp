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

    private int sceneSize;

    public NewGardenViewController() throws IOException {

    }

    public void createScene(Parent p, Stage s, int sceneSize)
    {
        this.sceneSize = sceneSize;
        this.gardenScene = new Scene(p, sceneSize, sceneSize);
        s.setScene(gardenScene);
    }
    public void onCreateGardenButtonClick() throws IOException {
        double width = readNumber(widthIntegerField);
        double height = readNumber(heightIntegerField);
        double gridSize = readNumber(gridSizeIntegerField);

        int gardenSize = sceneSize - 150;
        double percentage = 1;

        if (width > gardenSize && width > height){
            percentage = gardenSize/ width;
            width = gardenSize;
            height = (height * percentage);
            gridSize = gridSize * percentage;
        }
        if (height > gardenSize && height > width){
            percentage = gardenSize/ height;
            height = gardenSize;
            width = (width * percentage);
            gridSize = gridSize * percentage;
        }

        ViewLoader<GardenGridViewController> l = new ViewLoader<GardenGridViewController>("garden-grid-view.fxml");
        l.getController().setGarden(new Garden((int) width, (int) height, titleTextField.getText(), (int) gridSize, percentage));
    }

    private int readNumber (TextField t) {
        return parseInt(t.getText().isEmpty() ? "0" : t.getText());
    }
}