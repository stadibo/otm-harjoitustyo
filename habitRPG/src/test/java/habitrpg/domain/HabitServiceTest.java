/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.HabitDao;
import java.io.File;
import java.sql.SQLException;
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
        
        Database db = new Database();
        db.createDatabase("test.db");
        User user = new User("tester", "elon musk", "going to mars");
        hs = new HabitService(db, user);
        habitDao = new HabitDao(db, user);
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        habitDao.create(new Habit("run", 3));
        habitDao.create(new Habit("walk", 2));
        habitDao.create(new Habit("crawl", 1));
    }
    
    @After
    public void tearDown() {
        File file = new File("db", "test.db");
        file.delete();
    }

    
    
}
