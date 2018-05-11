package habitrpg.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class DaysShownDaoTest {
    
    private Database db;
    private DaysShownDao dsDao;
    
    public DaysShownDaoTest() {
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
    public void canCreateDaysShown() {
        dsDao = new DaysShownDatabaseDao(db);
        boolean[] days = new boolean[8];
        days[1] = true;
        days[3] = true;
        days[5] = true;
        days[7] = true;
        dsDao.create(days, 1);
        assertEquals(true, dsDao.getDays(1)[1]);
        assertEquals(false, dsDao.getDays(1)[2]);
    }
    
    @Test
    public void failedDaysShownCreationReturnNull() {
        dsDao = new DaysShownDatabaseDao(db);
        boolean[] days = new boolean[8];
        
        Connection conn;
        String sql = "DROP TABLE DaysShown";
        try {
            conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        }
        assertEquals(false, dsDao.create(days, 1));
    }
    
    @Test
    public void cannotGetDaysShownForNonexistingDaily() {
        dsDao = new DaysShownDatabaseDao(db);
        assertEquals(null, dsDao.getDays(2));
    }
    
    @Test
    public void canDeleteDaysShown() {
        dsDao = new DaysShownDatabaseDao(db);
        boolean[] days = new boolean[8];
        days[1] = true;
        days[3] = true;
        days[5] = true;
        days[7] = true;
        dsDao.create(days, 1);
        assertEquals(true, dsDao.delete(1));
        assertEquals(null, dsDao.getDays(1));
    }
    
    @Test
    public void cannotDeleteDaysShownForNonexistingDaily() {
        dsDao = new DaysShownDatabaseDao(db);
        assertEquals(false, dsDao.delete(1));
    }
    
}
