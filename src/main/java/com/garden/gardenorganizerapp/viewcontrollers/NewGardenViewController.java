package com.garden.gardenorganizerapp.viewcontrollers;

import com.garden.gardenorganizerapp.ViewLoader;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.db.GardenDAO;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class NewGardenViewController implements IViewController{


    @FXML
    private MenuBar menuBar;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField heightIntegerField;

    @FXML
    private TextField widthIntegerField;

    @FXML
    private TextField gridSizeIntegerField;

    private Scene gardenScene;
    private int sceneSize;

    public NewGardenViewController() {}

    public void createScene(Parent p, Stage s, int sceneSize)
    {
        this.sceneSize = sceneSize;
        this.gardenScene = new Scene(p, sceneSize, sceneSize);
        createMenu(menuBar);
        s.setScene(gardenScene);
    }
    public void onCreateGardenButtonClick() throws IOException {
        double width = readNumber(widthIntegerField);
        double height = readNumber(heightIntegerField);
        double gridSize = readNumber(gridSizeIntegerField);


        Garden g = new Garden((int) width, (int) height, titleTextField.getText(), (int) gridSize);

        GardenDAO dao = new GardenDAO();
        if (dao.store(g)) {
            ViewLoader<GardenGridViewController> l = new ViewLoader<>("garden-grid-view.fxml");
            l.getController().setGarden(g);
        }
    }

    private int readNumber (TextField t) {
        return parseInt(t.getText().isEmpty() ? "0" : t.getText());
    }
}