package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.dataobjects.annotations.DBPrimaryKey;
import com.garden.gardenorganizerapp.db.DBConnection;

public class DBObject
{
    @DBPrimaryKey(name = "ID")
    private int ID = DBConnection.INVALID_ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
