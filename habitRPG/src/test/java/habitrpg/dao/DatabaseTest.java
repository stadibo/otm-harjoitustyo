package habitrpg.dao;

import java.io.File;
import java.sql.Connection;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    
    public DatabaseTest() {
    }
    
    @After
    public void tearDown() {
        File file = new File("test.db");
        file.delete();
    }
    
    @Test
    public void databaseCreatedSuccessfully() {
        Database db = new Database();
        db.createDatabase("test.db");
        File f = new File("test.db");
        assertTrue(f.exists());
        assertTrue(f.isFile());
    }
    
    @Test
    public void canConnectToDatabase() {
        Database db = new Database();
        db.createDatabase("test.db");
        Connection conn = null;
        try {
            conn = db.getConnection();
        } catch (Exception e) {
            
        }
        assertTrue(conn != null);
    }
    
}
