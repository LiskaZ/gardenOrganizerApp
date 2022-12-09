package com.garden.gardenorganizerapp.dataobjects;

import com.garden.gardenorganizerapp.db.DBConnection;

public class DBObject
{
    private int ID = DBConnection.INVALID_ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public DBObject() {
    }
}
