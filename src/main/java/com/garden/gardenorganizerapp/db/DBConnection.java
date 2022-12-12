package com.garden.gardenorganizerapp.db;

import java.sql.*;

public class DBConnection {

    public static final int INVALID_ID = -1;

    private Connection connection = null;

    public DBConnection()
    {
    }

    public Connection getConnection() {

        if(null == connection)
        {
            connection = connect();
        }
        return connection;
    }

    public int insertQuery(String sql)
    {
        int insertId = INVALID_ID;
        try {
            Statement s = getConnection().createStatement();
            s.execute(sanitizeQuery(sql));
            if(s.getUpdateCount() > 0) {
                insertId = lastInsertId();
            }
            //close();
        }
        catch (SQLException e) {
            System.out.println(String.format("During Query: \"%s\"", sql));
            e.printStackTrace();
        }

        return insertId;
    }

    public int deleteQuery(String sql)
    {
        int deleteId = INVALID_ID;
        try {
            Statement s = getConnection().createStatement();
            s.execute(sanitizeQuery(sql));
            if(s.getUpdateCount() > 0) {
                deleteId = lastInsertId();
            }
            //close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteId;
    }

    public boolean query(String sql)
    {
        boolean res = false;
        try {
            Statement s = getConnection().createStatement();
            if(!s.execute(sanitizeQuery(sql))) {
                if (s.getUpdateCount() > 0)
                {
                    res = true;
                }
            } else {
                res = true;
            }
            //close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    private Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:garden.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static boolean isIdValid(int id)
    {
        return INVALID_ID != id;
    }

    private int lastInsertId()
    {
        if(null != connection)
        {
            try {
                Statement s = connection.createStatement();
                ResultSet set = s.executeQuery(sanitizeQuery("SELECT last_insert_rowid() as id"));
                if(set.next())
                {
                    return set.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return INVALID_ID;
    }

    public boolean close()
    {
        boolean res = false;
        if(null != connection)
        {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            res = true;
        }

        return res;
    }

    private String sanitizeQuery(String s)
    {
        s = s.trim();
        if(!s.endsWith(";"))
        {
            s += ";";
        }

        return s;
    }

    public ResultSet selectQuery(String sql)
    {
        try {
            Statement s = getConnection().createStatement();
            return s.executeQuery(sanitizeQuery(sql));
        }
        catch (SQLException e) {
            System.err.println(String.format("During Query :\"%s\"", sql));
            e.printStackTrace();
        }

        return null;
    }
}
