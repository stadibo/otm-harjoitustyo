/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author peje
 */
public class TodoDao implements Dao<Todo, Integer> {

    private Database database;
    private User user;

//    public TodoDao(Database database, User user) {
//        this.database = database;
//        this.user = user;
//        createTable();
//    }

    public TodoDao(Database database) {
        this.database = database;
        createTable();
    }

    public void setUser(User user) {
        this.user = user;
    }

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
            //System.out.println(e.getMessage());
        }
        return found;
    }

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

            //System.out.println(e.getMessage());
        }

        return todos;
    }

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

            //set id for Habit
            int rowAffected = stmt.executeUpdate();
            object.setId(rowAffected);
        } catch (SQLException e) {
            return null;
            //System.out.println(e.getMessage());
        }
        return object;
    }

    @Override
    public boolean delete(Integer key) {
        String sql = "DELETE FROM Todo WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, this.user.getUsername());
            stmt.setInt(2, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean setDone(Integer key) {
        String sql = "UPDATE Todo SET complete = ? WHERE username = ? AND id = ?";
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setBoolean(1, true);
            stmt.setString(2, this.user.getUsername());
            stmt.setInt(3, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        return true;
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
