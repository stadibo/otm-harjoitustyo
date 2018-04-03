/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author peje
 */

/**
 * Class encapsulating database-operations
 */

public class Database {
    
    private String path;
    
    public Database(File file) throws ClassNotFoundException {
        this.path = file.getAbsolutePath();
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }
    
}
