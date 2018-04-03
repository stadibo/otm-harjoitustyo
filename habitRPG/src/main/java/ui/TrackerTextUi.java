/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.Database;
import dao.UserDao;
import domain.UserService;
import java.io.File;

/**
 *
 * @author peje
 */
public class TrackerTextUi {
    
    private UserService userService;
    private Database database;

    public TrackerTextUi() throws ClassNotFoundException {
        File db = new File("db", "tracker.db");
        this.database = new Database(db);
        
        UserDao userDao = new UserDao(database);
        this.userService = new UserService(userDao, database);
    }
    
}
