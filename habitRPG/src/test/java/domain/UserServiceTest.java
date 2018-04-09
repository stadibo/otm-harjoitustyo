/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import dao.Database;
import dao.UserDao;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class UserServiceTest {
    
    private UserService us;
    private UserDao userDao;

    public UserServiceTest() {

        try {
            Database db = new Database();
            db.createDatabase("test.db");
            us = new UserService(db);
            userDao = new UserDao(db);
        } catch (SQLException e) {
        }

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        User user = new User("tester", "elon musk", "going to mars");
        try {
            userDao.create(user);
        } catch (SQLException e) {
        }
    }

    @After
    public void tearDown() {
        File file = new File("db", "test.db");
        file.delete();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {}
     
     @Test
     public void userCanLogIn() {
         String username = "tester";
        try {
            us.login(username);
        } catch (SQLException e) {
        }
        assertEquals("tester", us.getLoggedUser().getUsername());
        assertEquals("elon musk", us.getLoggedUser().getName());
        assertEquals("going to mars", us.getLoggedUser().getMotto());
         
     }
}
