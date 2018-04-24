/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author peje
 */

/**
 * Class encapsulating database
 */

public class Database {
    
    private String path;
    
    public Database(File file) {
        this.path = file.getAbsolutePath();
    }
    
    public Database() {
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }
    
    public void createDatabase(String fileName) throws SQLException {
        String url = "db/" + fileName;
        
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        } finally {
            this.setPath(url);
        }
    }

    public void setPath(String path) {
        System.out.println("New path: " + path);
        this.path = path;
    }
    
}
