package com.garden.gardenorganizerapp.db.daobase;

import com.garden.gardenorganizerapp.dataobjects.Garden;
import com.garden.gardenorganizerapp.dataobjects.PlantingArea;
import com.garden.gardenorganizerapp.db.DBConnection;
import com.garden.gardenorganizerapp.db.GardenDAO;
import com.garden.gardenorganizerapp.db.PlantingAreaDAO;
import com.garden.gardenorganizerapp.test.db.support.FakeDBProvisioner;
import com.garden.gardenorganizerapp.test.db.support.MemoryDBConnecter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GardenDAOTest {

    private FakeDBProvisioner dbProvisioner;
    private MemoryDBConnecter connecter;
    @BeforeClass
    public void setUp() {

        connecter = new MemoryDBConnecter();
        DBConnection.setDBConnecter(connecter);
        dbProvisioner = new FakeDBProvisioner(new DBConnection());
    }

    @AfterClass
    public void tearDown()
    {
        Assert.assertEquals(connecter.disconnect(), true);
    }

    @Test
    public void GivenGardenObjectWithPlantingAreasSetWhenInsertThenInsertPlantingAreaToDB()
    {
        Garden garden = new Garden();
        garden.setName("Testgarden");
        PlantingArea a = new PlantingArea();
        garden.addPlantingArea(a);

        GardenDAO gardenDao = new GardenDAO();
        Assert.assertEquals(gardenDao.store(garden), true);
        Assert.assertEquals(gardenDao.loadAll().size(), 1);

        garden = gardenDao.load(garden.getID());
        Assert.assertEquals(garden.getAreas().size(), 1);

        PlantingAreaDAO areaDao = new PlantingAreaDAO();
        var areas = areaDao.loadAll();
        Assert.assertEquals(1, areas.size());
        Assert.assertEquals(garden.getID(), areas.elementAt(0).getGarden().getID());

        PlantingArea b = new PlantingArea();
        garden.addPlantingArea(b);

        Assert.assertEquals(gardenDao.store(garden), true);
        areas = areaDao.loadAll();
        Assert.assertEquals(2, areas.size());
        Assert.assertEquals(garden.getID(), areas.elementAt(0).getGarden().getID());
        Assert.assertEquals(garden.getID(), areas.elementAt(1).getGarden().getID());

        garden.getAreas().remove(0);

        int expectedAreaId = areas.elementAt(1).getID();

        Assert.assertEquals(gardenDao.store(garden), true);

        areas = areaDao.loadAll();
        Assert.assertEquals(areas.size(), 1);
        Assert.assertEquals(garden.getID(), areas.elementAt(0).getGarden().getID());
        Assert.assertEquals(expectedAreaId, areas.elementAt(0).getID());

        Assert.assertEquals(gardenDao.remove(garden), true);
        Assert.assertEquals(gardenDao.loadAll().size(), 0);

        areas = areaDao.loadAll();
        Assert.assertEquals(areas.size(), 0);

        Assert.assertEquals(gardenDao.loadAll().size(), 0);
    }
}

