package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Item;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;
import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class PlantingAreaDAO implements IDAO<PlantingArea> {

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
        String s = "UPDATE PlantingArea SET Garden_ID = " + a.getGardenId() + " WHERE ID = " + a.getID();
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
            ItemDAO idao = new ItemDAO();
            a.getItem().setPlantingAreaId(id);
            success &= idao.store(a.getItem());

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
        return loadInternal(areaId, EAGER);
    }

    public PlantingArea loadLazy(int areaId)
    {
        return loadInternal(areaId, LAZY);
    }

    private PlantingArea loadInternal(int areaId, boolean eager)
    {
        DBConnection c = GardenApplication.getDBConnection();

        PlantingArea a = null;

        String sql = "SELECT Garden_ID FROM PlantingArea WHERE ID = " + areaId + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            if(res.next())
            {
                ItemDAO idao = new ItemDAO();
                Item item = idao.loadForArea(areaId);
                a = new PlantingArea(item);
                a.setID(areaId);
                a.setGardenId(res.getInt("Garden_ID"));

                if(eager) {
                    PlantingSpotDAO dao = new PlantingSpotDAO();
                    a.setSpots(dao.loadForArea(areaId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return areas;
    }
}
