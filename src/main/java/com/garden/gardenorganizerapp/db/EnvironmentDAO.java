package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.Environment;
import com.garden.gardenorganizerapp.db.daobase.AbstractDAO;
import com.garden.gardenorganizerapp.db.daobase.IDAO;

public class EnvironmentDAO extends AbstractDAO<Environment> implements IDAO<Environment> {

    public EnvironmentDAO() {
        super(new Environment());
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
}
