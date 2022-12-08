package com.garden.gardenorganizerapp.db;

import java.sql.*;

public class DBConnection {

    public static int INVALID_ID = -1;
    public Connection getConnection() {

        if(null == conn)
        {
            connect();
        }
        return conn;
    }

    private Connection conn = null;

    public DBConnection()
    {
    }

    public int insertQuery(String sql)
    {
        int insertId = INVALID_ID;
        try {
            Statement s = getConnection().createStatement();
            s.execute(sql);
            if(s.getUpdateCount() > 0) {
                insertId = lastInsertId();
            }
            //close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return insertId;
    }

    public boolean query(String sql)
    {
        boolean res = false;
        try {
            Statement s = getConnection().createStatement();
            if(!s.execute(sql)) {
                if (s.getUpdateCount() > 0)
                {
                    res = true;
                }
            }
            //close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        return res;
    }

    private Connection connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite:garden.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static boolean isIdValid(int id)
    {
        return id != INVALID_ID;
    }

    private int lastInsertId()
    {
        if(null != conn)
        {
            try {
                Statement s = conn.createStatement();
                ResultSet set = s.executeQuery("SELECT last_insert_rowid() as id;");
                if(set.next())
                {
                    return set.getInt("id");
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }

        return INVALID_ID;
    }

    public boolean close()
    {
        boolean res = false;
        if(null != conn)
        {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            res = true;
        }

        return res;
    }

}
