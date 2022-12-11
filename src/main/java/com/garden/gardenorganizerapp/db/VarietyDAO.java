package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.Variety;
import javafx.scene.paint.Color;

import java.util.Vector;

public class VarietyDAO extends AbstractAllDAO<Variety>{

    public VarietyDAO()
    {
        super(new Variety());
    }

    public Variety load(int id)
    {
        Variety v = super.load(id);
        loadColorFromCrop(v);
        return v;
    }

    public Vector<Variety> loadAll()
    {
        Vector<Variety> varieties = super.loadAll();
        for(var v: varieties) {
            loadColorFromCrop(v);
        }
        return varieties;
    }

    private void loadColorFromCrop(Variety v)
    {
        if(null == v.getDefaultColor())
        {
            v.setDefaultColor(new Color(0, 0, 0, 0));
        }
    }

    @Override
    public Vector<Variety> loadAllLazy() {
        return null;
    }

    @Override
    public Variety loadLazy(int id) {
        return null;
    }
    @Override
    public boolean store(Variety obj) {
        return false;
    }
}
