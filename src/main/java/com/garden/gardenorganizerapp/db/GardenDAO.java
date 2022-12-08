package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class GardenDAO {

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
        String s = "UPDATE Garden SET Name = '" + g.getName() + "', Width = " + g.getWidth() +", Height = " + g.getHeight() + ", GridSize = " + g.getGridSize() + " WHERE ID = " + g.getID() + ";";
        if(c.query(s))
        {
            PlantingAreaDAO areaDAO = new PlantingAreaDAO();
            for(PlantingArea a: g.getAreas()) {
                a.setGardenId(g.getID());
                areaDAO.store(a);
            }
            return true;
        }
        return false;
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

    public Vector<Garden> load()
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

                PlantingAreaDAO area = new PlantingAreaDAO();
                g.setPlantingAreas(area.loadForGarden(g.getID()));
                gardens.add(g);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return gardens;
    }
}
