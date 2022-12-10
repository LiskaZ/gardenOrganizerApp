package com.garden.gardenorganizerapp.db;

public interface IDAO<T> {

    static final boolean EAGER = true;
    static final boolean LAZY = false;

    public T load(int id);
    public T loadLazy(int id);
    public boolean store(T obj);
}
