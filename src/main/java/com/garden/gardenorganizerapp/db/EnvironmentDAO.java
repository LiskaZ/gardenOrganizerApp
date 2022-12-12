package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Environment;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class EnvironmentDAO implements IAllDAO<Environment>{

    public boolean store(Environment i) {
        if(DBConnection.isIdValid(i.getID())) {
            return updateExistingEnvironmentEnvironment(i);
        }
        else {
            return insertNewEnvironmentEnvironment(i);
        }
    }

    @Override
    public boolean remove(Environment obj) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    private boolean updateExistingEnvironmentEnvironment(Environment env) {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "UPDATE ENVIRONMENT SET Name = '" + env.getName() + "', Defaultcolor = " + env.getColor() +" WHERE ID = " + env.getID() + ";";
        if(c.query(sql))
        {
            return true;
        }
        return false;
    }

    private boolean insertNewEnvironmentEnvironment(Environment env)
    {
        DBConnection c = GardenApplication.getDBConnection();
        String sql = "INSERT INTO ENVIRONMENT (Name, Defaultcolor) VALUES ('" + env.getName() + "', " + env.getColor() + ");";
        int id = c.insertQuery(sql);
        env.setID(id);
        return c.isIdValid(id);
    }

    private Vector<Environment> loadInternal(boolean eager, int id)
    {
        DBConnection c = GardenApplication.getDBConnection();

        Vector<Environment> envs = new Vector<Environment>();

        String sql = "SELECT ID, Name, Defaultcolor FROM Environment";
        if(DBConnection.isIdValid(id))
        {
            sql += " WHERE ID = " + id + " LIMIT 1";
        }
        try {
            ResultSet res = c.selectQuery(sql);

            while(res.next())
            {
                Environment env = new Environment(
                        res.getString("Name"),
                        Color.valueOf(res.getString("Defaultcolor")));
                env.setID(res.getInt("ID"));

                envs.add(env);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return envs;
    }

    public Environment load(int envID) {
        Vector<Environment> envs = loadInternal(EAGER, envID);
        return envs.isEmpty() ? null : envs.firstElement();
    }

    public Environment loadLazy(int envID) {
        return load(envID);
    }

    public Vector<Environment> loadAll() {
        return loadInternal(EAGER, DBConnection.INVALID_ID);
    }

    public Vector<Environment> loadAllLazy() {
        return loadAll();
    }
}
