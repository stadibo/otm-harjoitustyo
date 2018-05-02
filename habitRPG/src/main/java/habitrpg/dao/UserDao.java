/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import habitrpg.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author peje
 */
public class UserDao {

    private Database database;

    public UserDao(Database database) {
        this.database = database;
        createTable();
    }

    public User getOne(String key) {
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

    public User create(User object) {
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

//    public boolean delete(String key) {
//        String sql = "DELETE FROM User WHERE username = ?";
//
//        try (Connection conn = database.getConnection();
//                PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            //set value
//            stmt.setString(1, key);
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            //System.out.println(e.getMessage());
//            return false;
//        }
//
//        return true;
//    }

    private void createTable() {
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
