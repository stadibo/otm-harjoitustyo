package habitrpg.dao;

import habitrpg.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data access object for User objects that interfaces with a database
 * 
 */
public class UserDao {

    private Database database;

    /**
     * Constructs DAO for User objects and creates table for object type 
     * if it doesn't exist
     * @param database (database to be accessed)
     */
    public UserDao(Database database) {
        this.database = database;
        createTable();
    }

    /**
     * Query from database and create a single User object 
     * @param key (database id = username)
     * @return User object that was queried based on username; otherwise null
     */
    public User getOne(String key) {
        User found = null;
        String sql = "SELECT username, name, exp, lvl, hp "
                + "FROM User WHERE username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setString(1, key);

            ResultSet res = stmt.executeQuery();

            //create user
            found = new User(res.getString("username"),
                    res.getString("name"), 
                    res.getInt("exp"), 
                    res.getInt("lvl"),
                    res.getInt("hp"));

        } catch (SQLException e) {
        }

        return found;
    }

    /**
     * Inserts a newly created User object into database
     * @param object (User object to be inserted)
     * @return created User object
     */
    public User create(User object) {
        String sql = "INSERT INTO "
                + "User(username, name, exp, lvl, hp) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setString(1, object.getUsername());
            stmt.setString(2, object.getName());
            stmt.setInt(3, object.getExperience());
            stmt.setInt(4, object.getLevel());
            stmt.setInt(5, object.getHealth());
            stmt.executeUpdate();
        } catch (SQLException e) {
        }

        return object;
    }
    
    /**
     * Update values for experience, level and health for User
     * @param object (User object to be modified)
     * @return true if setting succeeded; otherwise false
     */
    public boolean updateUser(User object) {
        String sql = "UPDATE User SET exp = ?, lvl = ?, hp = ? WHERE username = ?";
                
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setInt(1, object.getExperience());
            stmt.setInt(2, object.getLevel());
            stmt.setInt(3, object.getHealth());
            stmt.setString(4, object.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS User (\n"
                + "username TEXT PRIMARY KEY,\n"
                + "name TEXT NOT NULL,\n"
                + "exp INTEGER NOT NULL,\n"
                + "lvl INTEGER NOT NULL,\n"
                + "hp INTEGER NOT NULL\n"
                + ");";

        try (Connection conn = database.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

        } catch (Exception e) {
        }
    }

}
