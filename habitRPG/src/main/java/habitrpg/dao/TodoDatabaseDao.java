package habitrpg.dao;

import habitrpg.domain.Todo;
import habitrpg.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for Todo objects that interfaces with a database
 * 
 */
public class TodoDatabaseDao implements TodoDao {

    private Database database;
    private User user;
    
    /**
     * Constructs DAO for Todo objects and creates table for object type 
     * if it doesn't exist
     * @param database (database to be accessed)
     */
    public TodoDatabaseDao(Database database) {
        this.database = database;
        createTable();
    }

    /**
     * Sets current user
     * @param user (owner of Todo objects to be queried)
     */
    @Override
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Query from database and create a single Todo object 
     * @param key (database id)
     * @return Todo object that was queried based on user and id; otherwise null
     */
    @Override
    public Todo getOne(Integer key) {
        Todo found = null;
        String sql = "SELECT id, content, complete, difficulty "
                + "FROM Todo WHERE id = ? AND username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setInt(1, key);
            stmt.setString(2, user.getUsername());

            ResultSet res = stmt.executeQuery();

            //create habit
            found = new Todo(res.getInt("id"),
                    res.getString("content"),
                    res.getBoolean("complete"),
                    res.getInt("difficulty"),
                    this.user);

        } catch (SQLException e) {
        }
        return found;
    }

    /**
     * Query from database and create List of Todo objects
     * @return List of Todo objects based on current user
     */
    @Override
    public List<Todo> getAll() {

        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT id, content, complete, difficulty "
                + "FROM Todo WHERE username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, user.getUsername());
            ResultSet res = stmt.executeQuery();

            //loop through res
            while (res.next()) {
                todos.add(new Todo(res.getInt("id"),
                        res.getString("content"),
                        res.getBoolean("complete"),
                        res.getInt("difficulty"),
                        this.user));
            }
        } catch (SQLException e) {
        }

        return todos;
    }

    /**
     * Inserts a newly created Todo object into database
     * @param object (A new default Todo object with missing user; 
     * this is added in statement)
     * @return created Todo object with User and id added
     */
    @Override
    public Todo create(Todo object) {
        String sql = "INSERT INTO "
                + "Todo(content, complete, difficulty, username) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setString(1, object.getContent());
            stmt.setBoolean(2, object.isComplete());
            stmt.setInt(3, object.getDifficulty());
            stmt.setString(4, this.user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            return null;
        }
        return object;
    }

    /**
     * Delete representation of Todo object found by current user and id from database
     * @param key (database id)
     * @return true if deletion succeeded; otherwise false
     */
    @Override
    public boolean delete(Integer key) {
        String sql = "DELETE FROM Todo WHERE username = ? AND id = ?";

        int deleted = 0;
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, this.user.getUsername());
            stmt.setInt(2, key);
            deleted = stmt.executeUpdate();

        } catch (SQLException e) {

        }

        if (deleted == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Set Todo, found by current user and id, as done (complete = true) in database
     * @param key (database id)
     * @return true if setting succeeded; otherwise false
     */
    @Override
    public boolean setDone(Integer key) {
        String sql = "UPDATE Todo SET complete = ? WHERE username = ? AND id = ?";
        int affected = 0;
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setBoolean(1, true);
            stmt.setString(2, this.user.getUsername());
            stmt.setInt(3, key);
            affected = stmt.executeUpdate();

        } catch (SQLException e) {
        }

        if (affected == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Todo (\n"
                + "id INTEGER PRIMARY KEY,\n"
                + "username TEXT,\n"
                + "content TEXT NOT NULL,\n"
                + "complete INTEGER NOT NULL,\n"
                + "difficulty INTEGER NOT NULL,\n"
                + "date TEXT,\n"
                + "FOREIGN KEY (username) REFERENCES User (username)\n"
                + ");";

        try (Connection conn = database.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {

        }
    }

}
