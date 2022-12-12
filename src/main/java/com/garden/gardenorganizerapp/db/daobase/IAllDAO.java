package com.garden.gardenorganizerapp.db.daobase;

import com.garden.gardenorganizerapp.dataobjects.DBObject;

import java.util.Vector;

public interface IAllDAO<T extends DBObject> extends IDAO<T> {
    public Vector<T> loadAll();
    public Vector<T> loadAllLazy();
}
