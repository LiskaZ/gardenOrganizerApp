package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;
import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class PlantingAreaDAO {

    public boolean store(PlantingArea a)
    {
        if(DBConnection.isIdValid(a.getID())) {
            return updateExistingArea(a);
        }
        else {
            return insertNewArea(a);
        }
    }

    private boolean updateExistingArea(PlantingArea a) {
        DBConnection c = GardenApplication.getDBConnection();
        String s = "UPDATE PlantingArea SET GardenId = " + a.getGardenId() + " WHERE ID = " + a.getID();
        boolean success = false;
        if(c.query(s))
        {
            success = true;
            PlantingSpotDAO dao = new PlantingSpotDAO();
            for(PlantingSpot spot: a.getSpots()){
                spot.setPlantingAreaId(a.getID());
                success &= dao.store(spot);
            }
        }
        return success;
    }

    private boolean insertNewArea(PlantingArea a)
    {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "INSERT INTO PlantingArea (Garden_ID) VALUES (" + a.getGardenId() + ");";
        int id = c.insertQuery(sql);
        boolean success = false;
        if(DBConnection.isIdValid(id))
        {
            success = true;
            a.setID(id);
            PlantingSpotDAO dao = new PlantingSpotDAO();
            for(PlantingSpot spot: a.getSpots()){
                spot.setPlantingAreaId(a.getID());
                success &= dao.store(spot);
            }
        }
        return success;
    }

    public PlantingArea load(int areaId)
    {
        DBConnection c = GardenApplication.getDBConnection();

        PlantingArea a = new PlantingArea();

        String sql = "SELECT Garden_ID FROM PlantingArea WHERE ID = " + areaId + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            if(res.next())
            {
                a.setID(areaId);
                a.setGardenId(res.getInt("Garden_ID"));

                PlantingSpotDAO dao = new PlantingSpotDAO();
                a.setSpots(dao.loadForArea(areaId));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return a;
    }

    public Vector<PlantingArea> loadForGarden(int gardenid)
    {
        DBConnection c = GardenApplication.getDBConnection();

        Vector<PlantingArea> areas = new Vector<PlantingArea>();

        String sql = "SELECT ID FROM PlantingArea WHERE Garden_ID = " + gardenid + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            while(res.next())
            {
                areas.add(load(res.getInt("ID")));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return areas;
    }
}
