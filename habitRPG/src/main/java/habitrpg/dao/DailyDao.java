/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.dao;

import habitrpg.domain.Daily;
import habitrpg.domain.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author peje
 */
public class DailyDao implements Dao<Daily, Integer> {
    
    private Database database;
    private User user;
    
    public DailyDao(Database database, User user) {
        this.database = database;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Daily getOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Daily> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Daily create(Daily object) throws SQLException {
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
    
}
