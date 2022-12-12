package com.garden.gardenorganizerapp.db.daobase;

import com.garden.gardenorganizerapp.dataobjects.DBObject;

public interface IDAO<T extends DBObject> {

    static final boolean EAGER = true;
    static final boolean LAZY = false;

    public T load(int id);
    public T loadLazy(int id);
    public boolean store(T obj);
    public boolean remove(T obj);
    public boolean remove(int id);
}
