package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.Crop;

import java.util.Vector;

public class CropDAO extends com.garden.gardenorganizerapp.db.daobase.AbstractDAO<Crop> implements com.garden.gardenorganizerapp.db.daobase.IDAO<Crop> {

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
