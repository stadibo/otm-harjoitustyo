package habitrpg.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author peje
 */
/**
 * Class encapsulating database
 */
public class Database {

    private String path;

    public Database() {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }

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
