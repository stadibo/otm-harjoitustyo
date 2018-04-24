/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import java.io.File;
import java.sql.Connection;
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
    
    public void createDatabase(String fileName) {
        String url = fileName;
        
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        } finally {
            this.setPath(url);
        }
    }

    private void setPath(String path) {
        //System.out.println("New path: " + path);
        this.path = path;
    }
    
}
