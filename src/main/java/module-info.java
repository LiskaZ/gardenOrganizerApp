module com.garden.gardenorganizerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.garden.gardenorganizerapp to javafx.fxml;
    exports com.garden.gardenorganizerapp;
    exports com.garden.gardenorganizerapp.viewcontrollers;
    opens com.garden.gardenorganizerapp.viewcontrollers to javafx.fxml;
    exports com.garden.gardenorganizerapp.db;
    opens com.garden.gardenorganizerapp.db to javafx.fxml;
    exports com.garden.gardenorganizerapp.dataobjects;
    opens com.garden.gardenorganizerapp.dataobjects to javafx.fxml;
    exports com.garden.gardenorganizerapp.dataobjects.annotations;
    opens com.garden.gardenorganizerapp.dataobjects.annotations to javafx.fxml;
}