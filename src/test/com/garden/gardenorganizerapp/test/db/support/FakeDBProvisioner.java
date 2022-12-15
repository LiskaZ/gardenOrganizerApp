package com.garden.gardenorganizerapp.test.db.support;

import com.garden.gardenorganizerapp.db.DBConnection;
import org.testng.Assert;

import java.io.*;
import java.sql.Connection;

public class FakeDBProvisioner {

    private MemoryDBConnecter connecter;
    private Connection connection;
    private DBConnection conn;

    public FakeDBProvisioner(DBConnection conn) {
        connecter = new MemoryDBConnecter();
        conn = conn;
        DBConnection.setDBConnecter(connecter);

        try {
            InputStream stream = new FileInputStream("garden.db.sql");
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(streamReader);
            String query = "";
            while(reader.ready()){
                query += reader.readLine().replace("\t", "").replace("\n", "").replace("\r", "");

                if(query.endsWith(";"))
                {
                    boolean res = conn.query(query);
                    Assert.assertEquals(true, res, String.format("Error during query: \"%s\"", query));
                    query = "";
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
