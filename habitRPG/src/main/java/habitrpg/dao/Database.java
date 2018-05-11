package habitrpg.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class encapsulating database
 * 
 * @author peje
 */
public class Database {

    private String path;

    /**
     * Constructor
     */
    public Database() {
    }

    /**
     * Opens a connection to the database using JDBC DriverManager
     * 
     * @return connection object representing connection to database
     * @throws SQLException (connection cannot be established)
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }

    /**
     * Creates new database in root of application
     * 
     * @param fileName (name of the database file as a string, e.g. tracker.db)
     */
    public void createDatabase(String fileName) {
        String url = fileName;
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);
            conn.close();
        } catch (Exception e) {
        } finally {
            this.setPath(url);
        }
    }

    private void setPath(String path) {
        this.path = path;
    }

}
