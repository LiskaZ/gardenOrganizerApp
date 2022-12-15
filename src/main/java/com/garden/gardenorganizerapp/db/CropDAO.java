package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.Crop;
import com.garden.gardenorganizerapp.db.daobase.AbstractDAO;
import com.garden.gardenorganizerapp.db.daobase.IDAO;

public class CropDAO extends AbstractDAO<Crop> implements IDAO<Crop> {

    public CropDAO()
    {
        super(new Crop());
    }
}
