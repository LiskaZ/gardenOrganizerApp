package com.garden.gardenorganizerapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecter implements IDBConnecter {

    private Connection conn = null;
    public Connection connect() {
        if(null == conn) {
            try {
                // db parameters
                String url = "jdbc:sqlite:garden.db";
                // create a connection to the database
                conn = DriverManager.getConnection(url);
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
        }

        return conn;
    }

    @Override
    public boolean disconnect() {
        boolean res = true;
        if(null == conn) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
                res = false;
            }
        }
        return res;
    }

    @Override
    public boolean isConnected() {
        return null != conn;
    }
}