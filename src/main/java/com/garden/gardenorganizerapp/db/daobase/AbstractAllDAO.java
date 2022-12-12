package com.garden.gardenorganizerapp.db.daobase;

import com.garden.gardenorganizerapp.dataobjects.DBObject;
import com.garden.gardenorganizerapp.db.DBConnection;

import java.util.Vector;

public abstract class AbstractAllDAO<T extends DBObject> extends AbstractDAO<T> implements IAllDAO<T>{

    public AbstractAllDAO(T obj) {
        super(obj);
    }

    public Vector<T> loadAll(){
        return loadInternal(DBConnection.INVALID_ID);
    }
}