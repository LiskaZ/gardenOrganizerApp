package com.garden.gardenorganizerapp.db;

import java.util.Vector;

public abstract class AbstractAllDAO<T> extends AbstractDAO<T> implements IAllDAO<T>{

    public AbstractAllDAO(T obj) {
        super(obj);
    }

    public Vector<T> loadAll(){
        return loadInternal(DBConnection.INVALID_ID);
    }
}