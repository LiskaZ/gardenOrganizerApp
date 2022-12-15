package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;
import com.garden.gardenorganizerapp.dataobjects.PlantingSpot;
import com.garden.gardenorganizerapp.db.daobase.AbstractDAO;
import com.garden.gardenorganizerapp.db.daobase.IDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class PlantingAreaDAO extends AbstractDAO<PlantingArea> implements IDAO<PlantingArea> {

    public PlantingAreaDAO() {
        super(new PlantingArea());
    }

    private boolean updateExistingArea(PlantingArea a) {
        DBConnection c = GardenApplication.getDBConnection();
        String s = "UPDATE PlantingArea SET Garden_ID = " + a.getGarden().getID() + " WHERE ID = " + a.getID();
        boolean success = false;
        if(c.query(s))
        {
            success = true;
            PlantingSpotDAO dao = new PlantingSpotDAO();
            for(PlantingSpot spot: a.getSpots()){
                spot.getPlantingArea().setID(a.getID());
                success &= dao.store(spot);
            }
        }
        return success;
    }

    private boolean insertNewArea(PlantingArea a)
    {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "INSERT INTO PlantingArea (Garden_ID) VALUES (" + a.getGarden().getID() + ");";
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
                spot.getPlantingArea().setID(a.getID());
                success &= dao.store(spot);
            }
        }
        return success;
    }

    // TODO generic load
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
