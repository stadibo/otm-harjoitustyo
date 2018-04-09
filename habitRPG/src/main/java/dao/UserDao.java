/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author peje
 */
public class UserDao implements Dao<User, String> {

    private Database database;

    public UserDao(Database database) throws SQLException {
        this.database = database;
        createTable();
    }

    @Override
    public User getOne(String key) throws SQLException {
        User found = null;
        String sql = "SELECT username, name, motto "
                + "FROM User WHERE username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setString(1, key);

            ResultSet res = stmt.executeQuery();

            //create user
            found = new User(res.getString("username"),
                    res.getString("name"),
                    res.getString("motto"));

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        return found;
    }

    @Override
    public List<User> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User create(User object) throws SQLException {
        String sql = "INSERT INTO "
                + "User(username, name, motto) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setString(1, object.getUsername());
            stmt.setString(2, object.getName());
            stmt.setString(3, object.getMotto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }

        return object;
    }

    @Override
    public boolean delete(String key) throws SQLException {
        String sql = "DELETE FROM User WHERE username = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set value
            stmt.setString(1, key);
            stmt.executeUpdate();

        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean setDone(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS User (\n"
                + "username TEXT PRIMARY KEY,\n"
                + "name TEXT NOT NULL,\n"
                + "motto TEXT NOT NULL\n"
                + ");";

        try (Connection conn = database.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {

        }
    }

}
