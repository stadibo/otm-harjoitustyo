/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.UserDao;
import java.io.File;
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

    }

    @Before
    public void setUp() {
        Database db = new Database();
        db.createDatabase("test.db");
        userDao = new UserDao(db);
        us = new UserService(userDao);

        User user = new User("tester", "elon musk", "going to mars");
        userDao.create(user);
    }

    @After
    public void tearDown() {

        File file = new File("test.db");
        file.delete();
    }

    @Test
    public void userCanLogIn() {
        String username = "tester";
        us.login(username);
        assertEquals("tester", us.getLoggedUser().getUsername());
        assertEquals("elon musk", us.getLoggedUser().getName());
        assertEquals("going to mars", us.getLoggedUser().getMotto());
    }

    @Test
    public void userNotExistsAtLoginReturnFalse() {
        String username = "nope";
        boolean userExists = us.login(username);
        assertFalse(userExists);
    }

    @Test
    public void userCanLogOut() {
        String username = "tester";
        us.login(username);
        us.logout();
        assertEquals(null, us.getLoggedUser());
    }

    @Test
    public void canSuccessfullyCreateNewUniqueUser() {
        String username = "tester2";
        String name = "mister mister";
        String motto = "always testing";
        boolean success = us.newUser(username, name, motto);
        assertTrue(success);

        us.login(username);
        assertEquals("tester2", us.getLoggedUser().getUsername());
        assertEquals("mister mister", us.getLoggedUser().getName());
        assertEquals("always testing", us.getLoggedUser().getMotto());
    }

    @Test
    public void canOnlyCreateUserUniqueUsername() {
        String username = "tester";
        String name = "elon tusk";
        String motto = "always testing";
        boolean success = us.newUser(username, name, motto);
        assertFalse(success);
    }

}
