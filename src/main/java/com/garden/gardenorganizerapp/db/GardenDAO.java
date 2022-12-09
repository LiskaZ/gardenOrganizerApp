package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class

GardenDAO {

    private static final boolean EAGER = true;
    private static final boolean LAZY = !EAGER;

    public boolean store(Garden g)
    {
        if(DBConnection.isIdValid(g.getID())) {
            return updateExistingGarden(g);
        }
        else {
            return insertNewGarden(g);
        }
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

    public Vector<Garden> loadGardens()
    {
        return loadInternal(EAGER);
    }

    public Vector<Garden> loadGardensLazy()
    {
        return loadInternal(LAZY);
    }

    private Vector<Garden> loadInternal(boolean eager)
    {
        DBConnection c = GardenApplication.getDBConnection();

        Vector<Garden> gardens = new Vector<Garden>();

        String sql = "SELECT ID, Name, Width, Height, GridSize FROM Garden;";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

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
            System.out.println(e.toString());
        }

        return gardens;
    }

    public Garden loadGarden(int gardenID)
    {
        return loadSingleInternal(gardenID, EAGER);
    }

    public Garden loadGardenLazy(int gardenID)
    {
        return loadSingleInternal(gardenID, LAZY);
    }

    private Garden loadSingleInternal(int gardenID, boolean eager)
    {
        DBConnection c = GardenApplication.getDBConnection();
        Garden garden = null;
        String sql = "SELECT ID, Name, Width, Height, GridSize FROM Garden WHERE ID = " + gardenID + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            if(res.next()) {

                garden = new Garden(
                        res.getInt("Width"),
                        res.getInt("Height"),
                        res.getString("Name"),
                        res.getInt("GridSize"),
                        100);
                garden.setID(res.getInt("ID"));

                if(eager) {
                    PlantingAreaDAO area = new PlantingAreaDAO();
                    garden.setPlantingAreas(area.loadForGarden(garden.getID()));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return garden;
    }
}
