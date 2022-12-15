package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;
import com.garden.gardenorganizerapp.db.daobase.AbstractDAO;
import com.garden.gardenorganizerapp.db.daobase.IDAO;

public class GardenDAO extends AbstractDAO<Garden> implements IDAO<Garden> {

    public GardenDAO() {
        super(new Garden());
    }

    private boolean updateExistingGarden(Garden g) {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "UPDATE Garden SET Name = '" + g.getName() + "', Width = " + g.getWidth() +", Height = " + g.getHeight() + ", GridSize = " + g.getGridSize() + " WHERE ID = " + g.getID() + ";";
        boolean success = false;

        if(c.query(sql))
        {
            success = true;
            PlantingAreaDAO areaDAO = new PlantingAreaDAO();
            for(PlantingArea a: g.getAreas()) {
                a.setGarden(g);
                success &= areaDAO.store(a);
            }

        }
        return success;
    }

    private boolean insertNewGarden(Garden g)
    {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "INSERT INTO Garden (Name, Width, Height, GridSize) VALUES ('" + g.getName() + "', " + g.getWidth() + ", " +g.getHeight() + ", " + g.getGridSize() + ");";
        int id = c.insertQuery(sql);
        boolean success = false;
        if(DBConnection.isIdValid(id))
        {
            success = true;
            g.setID(id);
            PlantingAreaDAO areaDAO = new PlantingAreaDAO();
            for(PlantingArea a: g.getAreas()) {
                success &= areaDAO.store(a);
            }
        }

        return success;
    }

}
