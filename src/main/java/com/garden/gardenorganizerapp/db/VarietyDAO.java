package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.dataobjects.Variety;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Variety> loadVarietyForCrop(int cropID) {
        Vector<Variety> varieties = loadAll();
        return varieties.stream().filter(variety -> cropID == variety.getCrop_ID()).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Vector<Variety> loadAllLazy() {
        return null;
    }

    @Override
    public Variety loadLazy(int id) {
        return null;
    }
}
