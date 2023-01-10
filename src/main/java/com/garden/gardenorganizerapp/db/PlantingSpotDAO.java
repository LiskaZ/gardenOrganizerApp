package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;
import com.garden.gardenorganizerapp.db.daobase.AbstractDAO;
import com.garden.gardenorganizerapp.db.daobase.IDAO;
import javafx.geometry.Point2D;

import java.util.Vector;

public class PlantingSpotDAO extends AbstractDAO<PlantingSpot> implements IDAO<PlantingSpot> {

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

    public boolean checkSpotExistence(int x, int y){
        boolean spotExists = false;
        Vector<PlantingSpot> plantingSpots = loadAll();
        if (plantingSpots != null){
            for (PlantingSpot plantingSpot : plantingSpots) {
                if (plantingSpot.getX() == x && plantingSpot.getY() == y){
                    spotExists = true;
                    break;
                }
            }
        }
        return  spotExists;
    }
}
