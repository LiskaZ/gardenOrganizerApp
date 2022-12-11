package com.garden.gardenorganizerapp.db;

import com.garden.gardenorganizerapp.GardenApplication;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBEntity;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBField;
import com.garden.gardenorganizerapp.dataobjects.annotations.DBPrimaryKey;
import javafx.scene.paint.Color;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public abstract class AbstractDAO<T> implements IDAO<T>{
    private T type;

    public AbstractDAO(T t)
    {
        type = t;
    }

    public T load(int id) {
        var res = loadInternal(id);
        return res.isEmpty() ? null : res.firstElement();
    }

    protected Vector<T> loadInternal(int id) {

        Vector<T> v = new Vector<T>();
        DBConnection c = GardenApplication.getDBConnection();

        try {
            try {
                Class<?> clazz = type.getClass();
                ResultSet res = c.selectQuery(createSelectQuery(id));

                while (res.next()) {
                    T obj = (T) type.getClass().getDeclaredConstructor().newInstance();
                    if(readFromResultSet(res, obj))
                    {
                        v.add(obj);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch(NoSuchMethodException | InvocationTargetException | InstantiationException |
              IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return v;
    }

    // internal helper method
    protected T getTypeObj()
    {
        return type;
    }

    protected boolean readFromResultSet(ResultSet res, T obj) {
        try {
            for (Field f : getAnnotationFields(DBPrimaryKey.class)) {
                f.setAccessible(true);
                if (f.getType() == Integer.TYPE) {
                    f.set(obj, res.getInt(f.getAnnotation(DBPrimaryKey.class).name()));
                }
            }
            for (Field f : getDBFields()) {
                f.setAccessible(true);
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
        return getPKField() != null;
    }

    protected String getPKColName()
    {
        var field = getPKField();
        if(field != null) {
            return field.getAnnotation(DBPrimaryKey.class).name();
        }

        return null;
    }

    protected String createSelectQuery(int id)
    {
        boolean addPK = id != DBConnection.INVALID_ID;
        String s =  "SELECT " + getColumnString() + " FROM " + getDbTableName();
        if(addPK) {
            s += " WHERE " + getPKColName() + " = " + id + " LIMIT 1";
        }
        System.out.println(s);
        return s;
    }

    private String getDbTableName()
    {
        Class<?> clazz = type.getClass();
        if(clazz.isAnnotationPresent(DBEntity.class))
        {
            return clazz.getAnnotation(DBEntity.class).tableName();
        }

        return "";
    }

    private String getColumnString()
    {
        String s = "";
        var fields = getDBFieldNames();
        if(hasPK()) {
            fields.add(getPKColName());
        }
        for(int i = 0; i < fields.size(); ++i){
            s += fields.elementAt(i);
            if(i < fields.size() - 1){
                s += ", ";
            }
        }

        return s;
    }

    private Vector<String> getDBFieldNames()
    {
        Vector<String> fields = new Vector<>();
        for(Field f: getDBFields()){
            fields.add(f.getAnnotation(DBField.class).name());
        }
        return fields;
    }

    protected Vector<Field> getDBFields()
    {
        return getAnnotationFields(DBField.class);
    }

    protected Vector<Field> getAnnotationFields(Class<? extends Annotation> ann)
    {
        return getAnnotationFields(type.getClass(), ann);
    }

    private Vector<Field> getAnnotationFields(Class<?> c, Class<? extends Annotation> ann)
    {
        Vector<Field> fields = new Vector<>();
        if(c != Object.class){
            for(Field f: c.getDeclaredFields()){
                if(f.isAnnotationPresent(ann)){
                    fields.add(f);
                }
            }
            fields.addAll(getAnnotationFields(c.getSuperclass(), ann));
        }

        return fields;
    }
    private Field getPKField(){
        for (var f : getAnnotationFields(DBPrimaryKey.class)) {
            if (f.isAnnotationPresent(DBPrimaryKey.class)) {
                return f;
            }
        }
        return null;
    }
}