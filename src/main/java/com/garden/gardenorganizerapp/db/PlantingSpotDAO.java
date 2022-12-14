package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;
import com.garden.gardenorganizerapp.db.daobase.AbstractAllDAO;

import java.util.Vector;

public class PlantingSpotDAO extends AbstractAllDAO<PlantingSpot> {

    public PlantingSpotDAO()
    {
        super(new PlantingSpot());
    }

    @Override
    public Vector<PlantingSpot> loadAllLazy() {
        return null;
    }

    @Override
    public PlantingSpot loadLazy(int id) {
        return null;
    }
}
