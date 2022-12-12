package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Item;
import com.garden.gardenorganizerapp.db.daobase.IDAO;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemDAO implements IDAO<Item> {

    public boolean store(Item item)
    {
        if(DBConnection.isIdValid(item.getID())) {
            return updateExistingItem(item);
        }
        else {
            return insertNewItem(item);
        }
    }

    @Override
    public boolean remove(Item obj) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    private boolean insertNewItem(Item item) {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "INSERT INTO Item (Color, Variety_ID, Environment_ID, PlantingArea_ID) VALUES ('" + item.getColor() + "', " + item.getVariety_ID() + ", " + item.getEnvironment_ID() + ", " + item.getPlantingAreaId()+ ");";

        int id = c.insertQuery(sql);
        item.setID(id);
        return c.isIdValid(id);
    }

    private boolean updateExistingItem(Item item) {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "UPDATE Item SET Color = '" + item.getColor() + "', Variety_ID = " + item.getVariety_ID() + ", Environment_ID = " + item.getEnvironment_ID() +  " WHERE ID = " + item.getID();
        if(c.query(sql))
        {
            return true;
        }
        return false;
    }

    public Item load(int itemID)
    {
        DBConnection c = GardenApplication.getDBConnection();

        Item item = null;

        String sql = "SELECT Color, Variety_ID, Environment_ID FROM Item WHERE ID = " + itemID + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            if(res.next())
            {
                item = new Item(Color.valueOf(res.getString("Color")), res.getInt("Variety_ID"), res.getInt("Environment_ID"));
                item.setID(itemID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    public Item loadLazy(int itemId)
    {
        return load(itemId);
    }

    public Item loadForArea(int areaId) {
        DBConnection c = GardenApplication.getDBConnection();

        String sql = "SELECT ID, Color, Variety_ID, Environment_ID, PlantingArea_ID FROM Item WHERE PlantingArea_ID = " + areaId + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            if(res.next())
            {
                Item item = new Item(
                        Color.valueOf(res.getString("Color")),
                        Integer.valueOf(res.getInt("Variety_ID")),
                        Integer.valueOf(res.getInt("Environment_ID")));
                item.setPlantingAreaId(res.getInt("PlantingArea_ID"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
