package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.DBObject;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBPrimaryKey;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public abstract class AbstractDAO<T extends DBObject> implements IDAO<T>{

    private DBQueryCreator<T> queryCreator;
    private DBObjectMetaInfoHelper<T> infoHelper;

    public AbstractDAO(T t){
        infoHelper = new DBObjectMetaInfoHelper<>(t);
        queryCreator = new DBQueryCreator<T>(infoHelper);
    }
    @Override
    public boolean store(T obj)
    {
        if(obj.getID() == DBConnection.INVALID_ID)
        {
            return insert(obj);
        }
        else {
            return update(obj);
        }
    }

    @Override
    public T load(int id) {
        var res = loadInternal(id);
        return res.isEmpty() ? null : res.firstElement();
    }

    @Override
    public boolean remove(T obj) {
        Integer s;
        return remove(infoHelper.<Integer>getFieldValueT(infoHelper.getPKField(), obj));
    }

    @Override
    public boolean remove(int id) {

        DBConnection c = GardenApplication.getDBConnection();

        if(c.deleteQuery(queryCreator.createDeleteQuery(id)) != DBConnection.INVALID_ID)
        {
            return true;
        }

        return false;
    }

    private boolean update(T obj) {

        DBConnection c = GardenApplication.getDBConnection();

        int id = c.insertQuery(queryCreator.createUpdateQuery(obj));
        if(id != DBConnection.INVALID_ID)
        {
            obj.setID(id);
            return true;
        }

        return false;
    }
    private boolean insert(T obj) {

        DBConnection c = GardenApplication.getDBConnection();

        int id = c.insertQuery(queryCreator.createInsertQuery(obj));
        if(id != DBConnection.INVALID_ID)
        {
            obj.setID(id);
            return true;
        }

        return false;
    }

    protected Vector<T> loadInternal(int id) {

        Vector<T> v = new Vector<T>();
        DBConnection c = GardenApplication.getDBConnection();

        try {
            ResultSet res = c.selectQuery(queryCreator.createSelectQuery(id));

            while (res.next()) {
                T obj = (T) infoHelper.createInstance();
                if(obj != null && readFromResultSet(res, obj))
                {
                    v.add(obj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;
    }

    protected boolean readFromResultSet(ResultSet res, T obj) {
        try {
            for (Field f : infoHelper.getAnnotationFieldsAccessible(DBPrimaryKey.class)) {
                if (f.getType() == Integer.TYPE) {
                    f.set(obj, res.getInt(f.getAnnotation(DBPrimaryKey.class).name()));
                }
            }
            for (Field f : infoHelper.getDBFieldsAccessible()) {
                if (f.getType() == Integer.TYPE) {
                    f.set(obj, res.getInt(f.getAnnotation(DBField.class).name()));
                } else if (f.getType() == String.class) {
                    f.set(obj, res.getString(f.getAnnotation(DBField.class).name()));
                } else if (f.getType() == Color.class) {
                    String s = res.getString(f.getAnnotation(DBField.class).name());
                    f.set(obj, s == null || s.isEmpty() ? null : Color.valueOf(s));
                }
            }
        }
        catch (SQLException | IllegalAccessException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    protected boolean hasPK()
    {
        return infoHelper.hasPK();
    }

    protected String getPKColName()
    {
        return infoHelper.getPKColName();
    }

}