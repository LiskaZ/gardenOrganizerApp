package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Item;
import com.garden.gardenorganizerapp.db.daobase.AbstractDAO;
import com.garden.gardenorganizerapp.db.daobase.IDAO;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class ItemDAO extends AbstractDAO<Item> implements IDAO<Item> {

    public ItemDAO() { super(new Item());}

    @Override
    public Item loadLazy(int id) {
        return null;
    }


    // TODO Generisch umbauen
    public Item loadForArea(int areaId) {
        DBConnection c = GardenApplication.getDBConnection();

        String sql = "SELECT ID, Color, Variety_ID, Environment_ID, PlantingArea_ID, Anzahl FROM Item WHERE PlantingArea_ID = " + areaId + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            if(res.next())
            {
                Item item = new Item();
                item.setColor(Color.valueOf(res.getString("Color")));
                item.setVariety_ID(Integer.valueOf(res.getInt("Variety_ID")));
                item.setEnvironment_ID(Integer.valueOf(res.getInt("Environment_ID")));
                item.setCount(res.getInt("Anzahl"));
                item.setPlantingAreaId(res.getInt("PlantingArea_ID"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Vector<Item> loadAllLazy() {
        return null;
    }
}
