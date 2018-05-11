package habitrpg.dao;

import habitrpg.domain.Daily;
import habitrpg.domain.User;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class DailyDaoTest {

    private Database db;
    private DailyDao dailyDao;

    public DailyDaoTest() {
    }

    @Before
    public void setUp() {
        db = new Database();
        db.createDatabase("test.db");
    }

    @After
    public void tearDown() {
        File file = new File("test.db");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void canCreateDaily() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        Daily d = new Daily("yes", 1, "20180101");
        dailyDao.create(d);

        assertEquals("yes", dailyDao.getOne(1).getContent());
        assertEquals("20180101", dailyDao.getOne(1).getDate());
        assertEquals(1, dailyDao.getOne(1).getDifficulty());
        assertEquals("test", dailyDao.getOne(1).getUser().getUsername());
    }

    @Test
    public void creationOfDailyFailReturnNull() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);

        Connection conn;
        String sql = "DROP TABLE Daily";
        try {
            conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        }
        assertEquals(null, dailyDao.create(new Daily("yes", 1, "20180101")));
    }

    @Test
    public void canGetListOfDailies() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        Daily d1 = new Daily("yes", 1, "20180101");
        Daily d2 = new Daily("no", 2, "20180101");
        Daily d3 = new Daily("maybe", 3, "20180101");
        dailyDao.create(d1);
        dailyDao.create(d2);
        dailyDao.create(d3);

        List<Daily> dailies = dailyDao.getAll();
        assertEquals("yes", dailies.get(0).getContent());
        assertEquals("no", dailies.get(1).getContent());
        assertEquals("maybe", dailies.get(2).getContent());
    }

    @Test
    public void canDeleteDaily() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        Daily d = new Daily("yes", 1, "20180101");
        dailyDao.create(d);

        assertEquals("yes", dailyDao.getOne(1).getContent());
        assertTrue(dailyDao.delete(1));
        assertEquals(null, dailyDao.getOne(1));
    }

    @Test
    public void cannotDeleteNonexistingDaily() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);

        assertFalse(dailyDao.delete(1));
    }

    @Test
    public void canSetDone() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        Daily d = new Daily("yes", 1, "20180101");
        dailyDao.create(d);

        assertFalse(d.isComplete());
        assertTrue(dailyDao.setDone(1));
        assertTrue(dailyDao.getOne(1).isComplete());
    }

    @Test
    public void cannotSetDoneNonexistingDaily() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        assertEquals(false, dailyDao.setDone(1234));
    }
    
    @Test
    public void canSetUndone() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        Daily d = new Daily("yes", 1, "20180101");
        d.setComplete(true);
        dailyDao.create(d);
        assertTrue(dailyDao.setUndone(1));
        assertEquals(false, dailyDao.getOne(1).isComplete());
    }
    
    @Test
    public void cannotSetUndoneNonexistingDaily() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        assertEquals(false, dailyDao.setUndone(1234));
    }
    
    @Test
    public void canSetUntracked() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        Daily d = new Daily("yes", 1, "20180101");
        dailyDao.create(d);
        assertEquals(true, dailyDao.setUntracked(1));
        assertEquals(true, dailyDao.getOne(1).isRetired());
    }
    
    @Test
    public void cannotSetUntrackedNonexistingDaily() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        assertEquals(false, dailyDao.setUntracked(1));
    }
    
    @Test
    public void canUpdateDate() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        Daily d = new Daily("yes", 1, "20180101");
        dailyDao.create(d);
        assertEquals(true, dailyDao.updateDate(1, "20180202"));
        assertEquals("20180202", dailyDao.getOne(1).getDate());
    }
    
    @Test
    public void cannotUpdateDateNonexistingDaily() {
        User u = new User("test", "real", 0, 1, 100);
        dailyDao = new DailyDatabaseDao(db);
        dailyDao.setUser(u);
        assertEquals(false, dailyDao.updateDate(1, "20180202"));
    }

}
