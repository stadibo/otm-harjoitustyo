package habitrpg.dao;

import habitrpg.domain.Todo;
import habitrpg.domain.User;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class TodoDaoTest {
    
    private Database db;
    private TodoDao todoDao;
    
    public TodoDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
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
    public void canCreateTodo() {
        User u = new User("test", "real", 0, 1, 100);
        todoDao = new TodoDatabaseDao(db);
        todoDao.setUser(u);
        Todo t = new Todo("yes", 1);
        todoDao.create(t);
        
        assertEquals("yes", todoDao.getOne(1).getContent());
        assertEquals(1, todoDao.getOne(1).getDifficulty());
        assertEquals(false, todoDao.getOne(1).isComplete());
        assertEquals("test", todoDao.getOne(1).getUser().getUsername());
    }
    
    @Test
    public void creationOfDailyFailReturnNull() {
        User u = new User("test", "real", 0, 1, 100);
        todoDao = new TodoDatabaseDao(db);
        todoDao.setUser(u);

        Connection conn;
        String sql = "DROP TABLE Todo";
        try {
            conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        }
        assertEquals(null, todoDao.create(new Todo("yes", 1)));
    }
    
    @Test
    public void canGetListOfDailies() {
        User u = new User("test", "real", 0, 1, 100);
        todoDao = new TodoDatabaseDao(db);
        todoDao.setUser(u);
        Todo t1 = new Todo("yes", 1);
        Todo t2 = new Todo("no", 2);
        Todo t3 = new Todo("maybe", 3);
        todoDao.create(t1);
        todoDao.create(t2);
        todoDao.create(t3);

        List<Todo> todos = todoDao.getAll();
        assertEquals("yes", todos.get(0).getContent());
        assertEquals("no", todos.get(1).getContent());
        assertEquals("maybe", todos.get(2).getContent());
    }
    
    @Test
    public void canDeleteHabit() {
        User u = new User("test", "real", 0, 1, 100);
        todoDao = new TodoDatabaseDao(db);
        todoDao.setUser(u);
        Todo t = new Todo("yes", 1);
        todoDao.create(t);

        assertEquals("yes", todoDao.getOne(1).getContent());
        assertTrue(todoDao.delete(1));
        assertEquals(null, todoDao.getOne(1));
    }
    
    @Test
    public void cannotDeleteNonexistingHabit() {
        User u = new User("test", "real", 0, 1, 100);
        todoDao = new TodoDatabaseDao(db);
        todoDao.setUser(u);
        
        assertFalse(todoDao.delete(1));
    }
    
    @Test
    public void canSetDone() {
        User u = new User("test", "real", 0, 1, 100);
        todoDao = new TodoDatabaseDao(db);
        todoDao.setUser(u);
        Todo t = new Todo("yes", 1);
        todoDao.create(t);

        assertFalse(t.isComplete());
        assertTrue(todoDao.setDone(1));
        assertTrue(todoDao.getOne(1).isComplete());
    }

    @Test
    public void cannotSetDoneNonexistingDaily() {
        User u = new User("test", "real", 0, 1, 100);
        todoDao = new TodoDatabaseDao(db);
        todoDao.setUser(u);
        assertEquals(false, todoDao.setDone(1234));
    }
    
}
