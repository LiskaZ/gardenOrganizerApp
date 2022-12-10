package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Item;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemDAO implements IDAO<Item>{

    public boolean store(Item s)
    {
        System.out.println("Items werden gestored.");
        return true;
    }

    public Item load(int itemID)
    {
        DBConnection c = GardenApplication.getDBConnection();

        Item item = null;

        String sql = "SELECT Item_ID, Name, Color FROM Item WHERE ID = " + itemID + ";";
        try {
            Statement s = c.getConnection().createStatement();
            ResultSet res = s.executeQuery(sql);

            if(res.next())
            {
                item = new Item(res.getString("Name"), Color.valueOf(res.getString("Color")));
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
}
