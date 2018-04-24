/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.HabitDao;
import habitrpg.dao.TodoDao;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
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
public class HabitServiceTest {
    
    private HabitService hs;
    private HabitDao habitDao;
    
    public HabitServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Database db = new Database();
        db.createDatabase("test.db");
        hs = new HabitService(db);
        habitDao = new HabitDao(db);
        
        User user = new User("tester", "elon musk", "going to mars");
        hs.updateUser(user);
        
        habitDao.setUser(user);
        habitDao.create(new Habit("Read", 1));
    }
    
    @After
    public void tearDown() {
        File file = new File("test.db");
        file.delete();
    }
    
    @Test
    public void canCreateNewHabitSuccess() {
        assertTrue(hs.createHabit("Less coffee", 2));
        assertEquals("Less coffee", habitDao.getOne(2).getContent());
    }

    @Test
    public void getHabitsUpdateSuccess() {
        List<Habit> habits = hs.getHabitsUpdate();
        assertEquals(1, habits.get(0).getId());
        assertEquals("Read", habits.get(0).getContent());
        assertEquals(1, habits.get(0).getDifficulty());
        assertEquals(false, habits.get(0).isRetired());
        assertEquals("tester", habits.get(0).getUser().getUsername());
    }
    
    @Test
    public void canMarkHabitAsUntrackedWhenHabitExists() {
        assertTrue(hs.untrack(1));
        assertTrue(habitDao.getOne(1).isRetired());
    }
    
    @Test
    public void canDeleteHabitWhenHabitExists() {
        habitDao.create(new Habit("Write code", 2));
        assertEquals("Write code", habitDao.getOne(2).getContent());
        assertTrue(hs.deleteHabitGui(2));
        assertEquals(null, habitDao.getOne(2));
    }
    
    @Test
    public void cannotDeleteHabitWhenHabitNotExists() {
        assertFalse(hs.deleteHabitGui(2));
    }

    @Test
    public void canAddToStreakWhenHabitExists() {
        assertEquals(0, habitDao.getOne(1).getCurrentStreak());
        
        assertTrue(hs.addToStreakGui(1));
        assertTrue(hs.addToStreakGui(1));
        assertTrue(hs.addToStreakGui(1));
        
        assertEquals(3, habitDao.getOne(1).getCurrentStreak());
    }
    
    @Test
    public void cannotAddToStreakWhenHabitNotExists() {
        assertFalse(hs.addToStreakGui(2));
    }
    
}
