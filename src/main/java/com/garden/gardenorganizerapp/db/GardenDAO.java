package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;
import com.garden.gardenorganizerapp.db.daobase.IAllDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class GardenDAO implements IAllDAO<Garden> {

    public boolean store(Garden g) {
        if(DBConnection.isIdValid(g.getID())) {
            return updateExistingGarden(g);
        }
        else {
            return insertNewGarden(g);
        }
    }

    @Override
    public boolean remove(Garden obj) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
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
                a.setGardenId(g.getID());
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

    public Vector<Garden> loadAll() {
        return loadInternal(EAGER, DBConnection.INVALID_ID);
    }

    public Vector<Garden> loadAllLazy() {
        return loadInternal(LAZY, DBConnection.INVALID_ID);
    }

    private Vector<Garden> loadInternal(boolean eager, int id)
    {
        DBConnection c = GardenApplication.getDBConnection();

        Vector<Garden> gardens = new Vector<Garden>();

        String sql = "SELECT ID, Name, Width, Height, GridSize FROM Garden";
        if(DBConnection.isIdValid(id))
        {
            sql += " WHERE ID = " + id + " LIMIT 1";
        }
        try {
            ResultSet res = c.selectQuery(sql);

            while(res.next())
            {
                Garden g = new Garden(
                        res.getInt("Width"),
                        res.getInt("Height"),
                        res.getString("Name"),
                        res.getInt("GridSize"),
                        100);
                g.setID(res.getInt("ID"));

                if(eager) {
                    PlantingAreaDAO area = new PlantingAreaDAO();
                    g.setPlantingAreas(area.loadForGarden(g.getID()));
                }
                gardens.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gardens;
    }

    public Garden load(int gardenID) {
        Vector<Garden> gardens = loadInternal(EAGER, gardenID);
        return gardens.isEmpty() ? null : gardens.firstElement();
    }

    public Garden loadLazy(int gardenID) {
        Vector<Garden> gardens = loadInternal(LAZY, DBConnection.INVALID_ID);
        return gardens.isEmpty() ? null : gardens.firstElement();
    }
}
