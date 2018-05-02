/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.UserDao;


/**
 * A class for getting and creating "user" objects by interfacing with 
 * its corresponding DAO (Data access object), UserDao.
 */
public class UserService {

    private UserDao userDao;
    private User loggedIn;
    private Database database;

    
    public UserService(Database database) {
        this.userDao = new UserDao(database);
        this.database = database;
    }

    /**
     * Checks if username exists in database and sets user as logged in.
     * 
     * @param username  (user input)
     * @return true if login was successful, else if no such username exists
     * in database
     */
    public boolean login(String username) {
        User user = userDao.getOne(username);

        if (user == null) {
            return false;
        }

        this.loggedIn = user;
        return true;
    }

    /**
     * Returns currently logged in user
     * 
     * @return logged in user
     */
    public User getLoggedUser() {
        return this.loggedIn;
    }

    /**
     * Logs out user.
     * 
     */
    public void logout() {
        loggedIn = null;
    }

    /**
     * Creates a "User" object and passes it on to UserDao to be
     * stored in the database.
     * 
     * @param username  (user input)
     * @param name  (user input)
     * @param motto (user input)
     * @return if creation was successful
     */
    public boolean newUser(String username, String name, String motto) {
        if (userDao.getOne(username) != null) {
            return false;
        }
        userDao.create(new User(username, name, motto));
        return true;
    }

}
