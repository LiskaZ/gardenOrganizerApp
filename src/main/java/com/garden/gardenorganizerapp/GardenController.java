package com.garden.gardenorganizerapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class GardenController {
    @FXML
    private Label titleText;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField heightIntegerField;

    @FXML
    private TextField widthIntegerField;

    @FXML
    private Button createGarden;

    private GardenProject project;
    private int height;
    private int width;

    @FXML
    protected void onEditGardenButtonClick() {

        this.width = parseInt(widthIntegerField.getText().isEmpty() ? "0" : widthIntegerField.getText());
        this.height = parseInt(heightIntegerField.getText().isEmpty() ? "0" : heightIntegerField.getText());

        project.setMaxHeight(height);
        project.setMaxWidth(width);
        project.setIdentifier(titleTextField.getText());
        project.createGardenLayer();
    }

    public void setProject(GardenProject project){
        this.project = project;
    }

}