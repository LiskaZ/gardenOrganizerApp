module com.garden.gardenorganizerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.garden.gardenorganizerapp to javafx.fxml;
    exports com.garden.gardenorganizerapp;
}