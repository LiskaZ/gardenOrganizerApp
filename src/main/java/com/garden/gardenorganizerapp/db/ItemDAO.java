package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.Item;
import com.garden.gardenorganizerapp.db.daobase.AbstractDAO;
import com.garden.gardenorganizerapp.db.daobase.IDAO;

public class ItemDAO extends AbstractDAO<Item> implements IDAO<Item> {

    public ItemDAO() { super(new Item());}
}
