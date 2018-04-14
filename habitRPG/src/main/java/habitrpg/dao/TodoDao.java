/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import habitrpg.domain.Todo;
import habitrpg.domain.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author peje
 */
public class TodoDao implements Dao<Todo, Integer> {

    private Database database;
    private User user;

    public TodoDao(Database database, User user) {
        this.database = database;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Todo getOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Todo> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Todo create(Todo object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setDone(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createTable() throws SQLException {
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
