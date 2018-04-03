/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.Database;
import dao.UserDao;
import java.sql.SQLException;

/**
 *
 * @author peje
 */

/**
 * Class responsible for actions aimed at user objects 
 */

public class UserService {
    
    private UserDao userDao;
    private User loggedIn;
    private Database database;
    
    public UserService(UserDao dao, Database database) {
        this.userDao = dao;
        this.database = database;
    }
    
    public boolean login(String username) throws SQLException {
        User user = userDao.getOne(username);
        
        if (user == null) {
            return false;
        }
        
        this.loggedIn = user;
        return true;
    }
    
    public User getLoggedUser() {
        return this.loggedIn;
    }
    
    public void logout() {
        loggedIn = null;
    }
    
    public boolean newUser(String username, String name, String motto) throws SQLException {
        if (userDao.getOne(username) != null) {
            return false;
        }
        
        User newUser = new User(username, name, motto);
        try {
            userDao.create(newUser);
        } catch(Exception e) {
            return false;
        }
        return true;
        
    }
    
}
