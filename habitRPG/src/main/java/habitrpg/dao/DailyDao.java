/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import habitrpg.domain.Daily;
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
public class DailyDao implements Dao<Daily, Integer> {

    private Database database;
    private User user;

    public DailyDao(Database database) {
        this.database = database;
        createTable();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Daily getOne(Integer key) {
        Daily found = null;
        String sql = "SELECT id, content, complete, retired, difficulty, date "
                + "FROM Daily WHERE id = ? AND username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, key);
            stmt.setString(2, user.getUsername());

            ResultSet res = stmt.executeQuery();

            found = new Daily(res.getInt("id"),
                    res.getString("content"),
                    res.getBoolean("complete"),
                    res.getBoolean("retired"),
                    res.getInt("difficulty"),
                    res.getString("date"),
                    this.user);

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        return found;
    }

    @Override
    public List<Daily> getAll() {
        List<Daily> dailies = new ArrayList<>();
        String sql = "SELECT id, content, complete, retired, difficulty, date "
                + "FROM Daily WHERE username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, user.getUsername());
            ResultSet res = stmt.executeQuery();

            //loop through res
            while (res.next()) {
                dailies.add(new Daily(res.getInt("id"), res.getString("content"),
                        res.getBoolean("complete"), res.getBoolean("retired"),
                        res.getInt("difficulty"), res.getString("date"),
                        this.user));
            }
        } catch (SQLException e) {
        }

        return dailies;
    }

    @Override
    public Daily create(Daily object) {
        String sql = "INSERT INTO "
                + "Daily(content, complete, retired, difficulty, date, username) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            //set values
            stmt.setString(1, object.getContent());
            stmt.setBoolean(2, object.isComplete());
            stmt.setBoolean(3, object.isRetired());
            stmt.setInt(4, object.getDifficulty());
            stmt.setString(5, object.getDate());
            stmt.setString(6, this.user.getUsername());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            object.setId(rs.getInt(1));
        } catch (SQLException e) {
            return null;
            //System.out.println(e.getMessage());
        }

        return object;
    }

    @Override
    public boolean delete(Integer key) {
        String sql = "DELETE FROM Daily WHERE username = ? AND id = ?";
        int deleted = 0;
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, this.user.getUsername());
            stmt.setInt(2, key);
            deleted = stmt.executeUpdate();
            
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        if (deleted == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean setDone(Integer key) {
        String sql = "UPDATE Daily SET complete = ? WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setBoolean(1, true);
            stmt.setString(2, user.getUsername());
            stmt.setInt(3, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    
    public boolean setUndone(Integer key) {
        String sql = "UPDATE Daily SET complete = ? WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setBoolean(1, false);
            stmt.setString(2, user.getUsername());
            stmt.setInt(3, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    
    public boolean setUntracked(Integer key) {
        String sql = "UPDATE Daily SET retired = ? WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setBoolean(1, true);
            stmt.setString(2, user.getUsername());
            stmt.setInt(3, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    
    public boolean updateDate(int key, String date) {
        String sql = "UPDATE Daily SET date = ? WHERE username = ? AND id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, date);
            stmt.setString(2, user.getUsername());
            stmt.setInt(3, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Daily (\n"
                + "id INTEGER PRIMARY KEY,\n"
                + "username TEXT,\n"
                + "content TEXT NOT NULL,\n"
                + "complete INTEGER NOT NULL,\n"
                + "retired INTEGER NOT NULL,\n"
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
