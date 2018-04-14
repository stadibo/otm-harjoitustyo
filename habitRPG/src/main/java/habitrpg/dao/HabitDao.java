/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import habitrpg.domain.Habit;
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
public class HabitDao implements Dao<Habit, Integer> {

    private Database database;
    private User user;

    public HabitDao(Database database, User user) throws SQLException {
        this.database = database;
        this.user = user;
        createTable();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Habit getOne(Integer key) throws SQLException {
        Habit found = null;
        String sql = "SELECT id, content, retired, difficulty, streak "
                + "FROM Habit WHERE id = ? AND username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setInt(1, key);
            stmt.setString(2, user.getUsername());

            ResultSet res = stmt.executeQuery();

            //create habit
            found = new Habit(res.getInt("id"),
                    res.getString("content"),
                    res.getBoolean("retired"),
                    res.getInt("difficulty"),
                    res.getInt("streak"),
                    this.user);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return found;

    }

    @Override
    public List<Habit> getAll() throws SQLException {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT id, content, retired, difficulty, streak "
                + "FROM Habit WHERE username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, user.getUsername());
            ResultSet res = stmt.executeQuery();

            //loop through res
            while (res.next()) {
                habits.add(new Habit(res.getInt("id"),
                        res.getString("content"),
                        res.getBoolean("retired"),
                        res.getInt("difficulty"),
                        res.getInt("streak"),
                        this.user));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (habits.isEmpty()) {
            return null;
        } else {
            return habits;
        }
    }

    @Override
    public Habit create(Habit object) throws SQLException {
        String sql = "INSERT INTO "
                + "Habit(content, retired, difficulty, streak) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setString(1, object.getContent());
            stmt.setBoolean(2, object.isRetired());
            stmt.setInt(3, object.getDifficulty());
            stmt.setInt(4, object.getCurrentStreak());
            stmt.setString(5, this.user.getUsername());

            //set id for Habit
            int rowAffected = stmt.executeUpdate();
            object.setId(rowAffected);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return object;
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
        String sql = "DELETE FROM Habit WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, this.user.getUsername());
            stmt.setInt(2, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean setDone(Integer key) throws SQLException {
        String sql = "UPDATE Habit SET retired = ? WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setBoolean(1, true);
            stmt.setString(2, user.getUsername());
            stmt.setInt(3, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean addToStreak(Integer key, Habit object) throws SQLException {
        String sql = "UPDATE Habit SET streak = ? WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setInt(1, object.getCurrentStreak() + 1);
            stmt.setString(2, this.user.getUsername());
            stmt.setInt(3, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            createTable();
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Habit (\n"
                + "id INTEGER PRIMARY KEY,\n"
                + "username TEXT,\n"
                + "content TEXT NOT NULL,\n"
                + "retired INTEGER NOT NULL,\n"
                + "difficulty INTEGER NOT NULL,\n"
                + "streak INTEGER NOT NULL,\n"
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
