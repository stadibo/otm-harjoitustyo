package habitrpg.dao;

import habitrpg.domain.Habit;
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
public class HabitDaoTest {

    private Database db;
    private HabitDao habitDao;

    public HabitDaoTest() {
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
    public void canCreateHabit() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        Habit h = new Habit("yes", 1);
        habitDao.create(h);
        
        assertEquals("yes", habitDao.getOne(1).getContent());
        assertEquals(1, habitDao.getOne(1).getDifficulty());
        assertEquals(0, habitDao.getOne(1).getCurrentStreak());
        assertEquals("test", habitDao.getOne(1).getUser().getUsername());
    }
    
    @Test
    public void creationOfDailyFailReturnNull() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);

        Connection conn;
        String sql = "DROP TABLE Habit";
        try {
            conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        }
        assertEquals(null, habitDao.create(new Habit("yes", 1)));
    }
    
    @Test
    public void canGetListOfDailies() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        Habit h1 = new Habit("yes", 1);
        Habit h2 = new Habit("no", 2);
        Habit h3 = new Habit("maybe", 3);
        habitDao.create(h1);
        habitDao.create(h2);
        habitDao.create(h3);

        List<Habit> habits = habitDao.getAll();
        assertEquals("yes", habits.get(0).getContent());
        assertEquals("no", habits.get(1).getContent());
        assertEquals("maybe", habits.get(2).getContent());
    }
    
    @Test
    public void canDeleteHabit() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        Habit h = new Habit("yes", 1);
        habitDao.create(h);

        assertEquals("yes", habitDao.getOne(1).getContent());
        assertTrue(habitDao.delete(1));
        assertEquals(null, habitDao.getOne(1));
    }
    
    @Test
    public void cannotDeleteNonexistingHabit() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        
        assertFalse(habitDao.delete(1));
    }
    
    @Test
    public void canUntrackHabitWithSetDone() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        Habit h = new Habit("yes", 1);
        habitDao.create(h);
        
        assertEquals(true, habitDao.setDone(1));
        assertEquals(true, habitDao.getOne(1).isRetired());
    }
    
    @Test
    public void cannotUntrackHabitWithSetDoneForNonexistingHabit() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        
        assertEquals(false, habitDao.setDone(1));
    }
    
    @Test
    public void canAddToStreak() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        Habit h = new Habit("yes", 1);
        habitDao.create(h);
        
        assertEquals(true, habitDao.addToOrRemoveFromStreak(1, h, 1));
        assertEquals(1, habitDao.getOne(1).getCurrentStreak());
    }
    
    @Test
    public void canDecreaseStreak() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        Habit h1 = new Habit("yes", 1);
        habitDao.create(h1);
        habitDao.addToOrRemoveFromStreak(1, h1, 3);
        Habit h2 = habitDao.getOne(1);
        
        assertEquals(true, habitDao.addToOrRemoveFromStreak(1, h2, -1));
        assertEquals(2, habitDao.getOne(1).getCurrentStreak());
    }
    
    @Test
    public void cannotModifyStreakOnNonExistingHabit() {
        User u = new User("test", "real", 0, 1, 100);
        habitDao = new HabitDao(db);
        habitDao.setUser(u);
        Habit h1 = new Habit("yes", 1);
        assertEquals(false, habitDao.addToOrRemoveFromStreak(1, h1, 1));
    }
    
}
