package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Crop;
import com.garden.gardenorganizerapp.dataobjects.Item;
import com.garden.gardenorganizerapp.dataobjects.Variety;
import javafx.scene.paint.Color;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class CropDAO extends AbstractAllDAO<Crop>{

    public CropDAO() { super( new Crop() ); }

    @Override
    public Vector<Crop> loadAllLazy() {
        return null;
    }

    @Override
    public Crop loadLazy(int id) {
        return null;
    }
    @Override
    public boolean store(Crop obj) {
        return false;
    }

}
