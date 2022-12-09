package com.garden.gardenorganizerapp.db;

import java.util.Vector;

public interface IAllDAO<T> extends IDAO<T> {
    public Vector<T> loadAll();
    public Vector<T> loadAllLazy();
}
