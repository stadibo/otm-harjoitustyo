/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.UserDao;

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

    public UserService(Database database) {
        this.userDao = new UserDao(database);
        this.database = database;
    }

    public boolean login(String username) {
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

    public boolean newUser(String username, String name, String motto) {
        if (userDao.getOne(username) != null) {
            return false;
        }
        userDao.create(new User(username, name, motto));
        return true;
    }

}
