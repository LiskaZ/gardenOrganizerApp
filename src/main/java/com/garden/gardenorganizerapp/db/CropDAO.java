package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.Crop;
import com.garden.gardenorganizerapp.db.daobase.AbstractAllDAO;

import java.util.Vector;

public class CropDAO extends AbstractAllDAO<Crop> {

    public CropDAO()
    {
        super(new Crop());
    }

    @Override
    public Vector<Crop> loadAllLazy() {
        return null;
    }

    @Override
    public Crop loadLazy(int id) {
        return null;
    }
}
