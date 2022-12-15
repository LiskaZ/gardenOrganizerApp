package com.garden.gardenorganizerapp.db.daobase;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.DBObject;
import com.garden.gardenorganizerapp.dataobjects.annotations.*;
import com.garden.gardenorganizerapp.db.DBConnection;
import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public abstract class AbstractDAO<T extends DBObject> implements IDAO<T>{

    private DBQueryCreator<T> queryCreator;
    protected DBObjectMetaInfoHelper<T> infoHelper;

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

    public boolean store(Object obj)
    {
        return store((T)obj);
    }

    @Override
    public T load(int id) {
        var res = loadInternal(id, EAGER);
        return res.isEmpty() ? null : res.firstElement();
    }

    public Vector<T> loadAll(){
        return loadInternal(DBConnection.INVALID_ID, EAGER);
    }

    public Vector<T> loadAllFor(Class<? extends DBObject> clazz, int id)
    {
        return loadInternalFor(clazz, id, EAGER);
    }
    @Override
    public T loadLazy(int id) {
        var res = loadInternal(id, LAZY);
        return res.isEmpty() ? null : res.firstElement();
    }
    @Override
    public Vector<T> loadAllLazy(){
        return loadInternal(DBConnection.INVALID_ID, LAZY);
    }

    public Vector<T> loadAllLazyFor(Class<? extends DBObject> clazz, int id)
    {
        return loadInternalFor(clazz, id, LAZY);
    }

    @Override
    public boolean remove(T obj) {
        return remove(infoHelper.<Integer>getFieldValueT(infoHelper.getPKField(), obj));
    }

    @Override
    public boolean remove(int id) {

        DBConnection c = GardenApplication.getDBConnection();

        return c.deleteQuery(queryCreator.createDeleteQuery(id));
    }

    private boolean update(T obj) {

        DBConnection c = GardenApplication.getDBConnection();

        int id = c.insertQuery(queryCreator.createUpdateQuery(obj));
        if(id != DBConnection.INVALID_ID)
        {
            storeForeignEntitiesList(obj);
            deleteForeignEntitiesList(obj);
            return true;
        }

        return false;
    }

    private boolean deleteForeignEntitiesList(T obj) {
        boolean res = true;
        for (Field f : infoHelper.getAnnotationFields(DBFKEntityList.class)) {
            f.setAccessible(true);
            if (f.getType() == Vector.class) {
                String sql = queryCreator.createDeleteFKListQuery(f, obj);

                DBConnection c = GardenApplication.getDBConnection();
                res = c.query(sql);
            }
        }

        return res;
    }

    private boolean insert(T obj) {

        DBConnection c = GardenApplication.getDBConnection();

        int id = c.insertQuery(queryCreator.createInsertQuery(obj));
        if(id != DBConnection.INVALID_ID)
        {
            obj.setID(id);
            storeForeignEntitiesList(obj);

            return true;
        }

        return false;
    }

    protected Vector<T> loadInternal(int id, boolean eager) {

        Vector<T> v = new Vector<T>();
        DBConnection c = GardenApplication.getDBConnection();

        try {
            ResultSet res = c.selectQuery(queryCreator.createSelectQuery(id));

            while (res.next()) {
                T obj = (T) infoHelper.createInstance();
                if(obj != null && readFromResultSet(res, obj, eager))
                {
                    v.add(obj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;
    }

    protected Vector<T> loadInternalFor(Class<? extends  DBObject> clazz, int fkId, boolean eager)
    {
        for(Field f: infoHelper.getFKFields())
        {
            if(f.getType() == clazz)
            {
                Vector<T> v = new Vector<T>();
                DBConnection c = GardenApplication.getDBConnection();

                String fkCol = f.getAnnotation(DBFKEntity.class).name();

                try {
                    ResultSet res = c.selectQuery(queryCreator.createSelectQueryFK(fkCol, fkId));

                    while (res.next()) {
                        T obj = (T) infoHelper.createInstance();
                        if(obj != null && readFromResultSet(res, obj, eager))
                        {
                            v.add(obj);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return v;
            }
        }
        return null;
    }

    private boolean readFromResultSet(ResultSet res, T obj, boolean eager) {
        try {
            for (Field f : infoHelper.getAnnotationFields(DBPrimaryKey.class)) {
                if (f.getType() == Integer.TYPE) {
                    f.set(obj, res.getInt(f.getAnnotation(DBPrimaryKey.class).name()));
                }
            }
            for (Field f : infoHelper.getDBFields()) {
                if (f.getType() == Integer.TYPE) {
                    f.set(obj, res.getInt(f.getAnnotation(DBField.class).name()));
                } else if (f.getType() == String.class) {
                    f.set(obj, res.getString(f.getAnnotation(DBField.class).name()));
                } else if (f.getType() == Color.class) {
                    String s = res.getString(f.getAnnotation(DBField.class).name());
                    f.set(obj, s == null || s.isEmpty() ? null : Color.valueOf(s));
                }
            }
            if(eager) {
                for (Field f : infoHelper.getFKFields()) {
                    f.setAccessible(true);
                    AbstractDAO<? extends IDAO> dao = infoHelper.createDao(f.getType());
                    if (infoHelper.isFKFieldCascade(f)) {
                        f.set(obj, dao.load(res.getInt(f.getAnnotation(DBFKEntity.class).name())));
                    }
                    else {
                        f.set(obj, dao.loadLazy(res.getInt(f.getAnnotation(DBFKEntity.class).name())));
                    }
                }
                for (Field f : infoHelper.getAnnotationFields(DBFKEntityList.class)) {
                    f.setAccessible(true);
                    if (f.getType() == Vector.class) {
                        try {
                            Vector<? extends DBObject> vec = (Vector<? extends DBObject>) f.get(obj);
                            vec.clear();
                            Class<?> foreignType = f.getAnnotation(DBFKEntityList.class).foreignType();
                            AbstractDAO<? extends DBObject> dao = infoHelper.createDao(foreignType);

                            Vector<? extends DBObject> foreignObjects = new Vector<>();
                            if(eager) {
                                foreignObjects = dao.loadAllFor(infoHelper.getClassOfType(), obj.getID());
                            }
                            else {
                                foreignObjects = dao.loadAllLazyFor(infoHelper.getClassOfType(), obj.getID());
                            }

                            f.set(obj, foreignObjects);

/*                            for(DBObject o: foreignObjects)
                            {
                                vec.add((? extends DBObject)o);*/
//                            for (DBObject i : vec) {
//
//                                Field fkField = infoHelper.getForeignKeyField(i);
//                                fkField.setAccessible(true);
//                                DBObject foreignObject = ((DBObject) fkField.get(i));
//                                if (null != foreignObject) {
//                                    //fkField.set(i, infoHelper.createInstance());
//                                    dao.store(i);
//                                }
//                                ((DBObject) fkField.get(i)).setID(obj.getID());

//                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean storeForeignEntitiesList(T obj) {
        boolean res = true;
        for (Field f : infoHelper.getAnnotationFields(DBFKEntityList.class)) {
            f.setAccessible(true);
            if (f.getType() == Vector.class) {
                try {
                    Vector<? extends DBObject> vec = (Vector<? extends DBObject>)f.get(obj);
                    Class<?> foreignType = f.getAnnotation(DBFKEntityList.class).foreignType();

                    for(DBObject i: vec) {
                        AbstractDAO<? extends DBObject> dao = infoHelper.createDao(foreignType);

                        Field fkField = infoHelper.getForeignKeyField(i);
                        fkField.setAccessible(true);
                        DBObject foreignObject = ((DBObject)fkField.get(i));
                        if(null == foreignObject)
                        {
                            fkField.set(i, infoHelper.createInstance());
                        }
                        ((DBObject)fkField.get(i)).setID(obj.getID());

                        dao.store(i);
                    }
                }
                catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return res;
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