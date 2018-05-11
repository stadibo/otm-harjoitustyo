package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.UserDao;
import habitrpg.dao.UserDatabaseDao;
import java.io.File;
import org.junit.After;
import org.junit.Before;
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
        userDao = new UserDatabaseDao(db);
        us = new UserService(userDao);

        User user = new User("tester", "elon musk", 0, 1, 100);
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
        assertEquals(1, us.getLoggedUser().getLevel());
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
        boolean success = us.newUser(username, name);
        assertTrue(success);

        us.login(username);
        assertEquals("tester2", us.getLoggedUser().getUsername());
        assertEquals("mister mister", us.getLoggedUser().getName());
        assertEquals(1, us.getLoggedUser().getLevel());
        assertEquals(200, us.getLoggedUser().getHealth());
    }

    @Test
    public void canOnlyCreateUserUniqueUsername() {
        String username = "tester";
        String name = "elon tusk";
        boolean success = us.newUser(username, name);
        assertFalse(success);
    }

    @Test
    public void canAddExperience() {
        us.login("tester");
        us.addExp(1);
        assertEquals(25, us.getLoggedUser().getExperience());

        us.addExp(2);
        assertEquals(75, us.getLoggedUser().getExperience());

        us.addExp(3);
        assertEquals(175, us.getLoggedUser().getExperience());
    }

    @Test
    public void canRemoveExperience() {
        us.login("tester");
        us.addExp(3);
        us.removeExp(75);
        assertEquals(25, us.getLoggedUser().getExperience());
    }

    @Test
    public void levelIncreaseWithEnoughExperience() {
        us.login("tester");
        us.getLoggedUser().setExperience(1950);
        us.addExp(3);
        assertEquals(50, us.getLoggedUser().getExperience());
        assertEquals(2, us.getLoggedUser().getLevel());
        assertEquals(400, us.getLoggedUser().getHealth());
    }

    @Test
    public void levelDecreaseByPenalty() {
        us.login("tester");
        us.getLoggedUser().setExperience(0);
        us.getLoggedUser().setLevel(2);
        us.getLoggedUser().setHealth(400);
        us.experiencePenalty();
        assertEquals(0, us.getLoggedUser().getExperience());
        assertEquals(1, us.getLoggedUser().getLevel());
        assertEquals(200, us.getLoggedUser().getHealth());
    }

    @Test
    public void levelNotGetLowerThanOne() {
        us.login("tester");
        us.getLoggedUser().setExperience(50);
        us.getLoggedUser().setLevel(1);
        us.getLoggedUser().setHealth(200);
        us.experiencePenalty();
        assertEquals(0, us.getLoggedUser().getExperience());
        assertEquals(1, us.getLoggedUser().getLevel());
        assertEquals(200, us.getLoggedUser().getHealth());
    }

    @Test
    public void invalidDifficultyWillNotChangeLevelOrExp() {
        us.login("tester");
        us.getLoggedUser().setHealth(200);
        us.addExp(0);
        assertEquals(0, us.getLoggedUser().getExperience());
        assertEquals(1, us.getLoggedUser().getLevel());
        assertEquals(200, us.getLoggedUser().getHealth());
    }

}
