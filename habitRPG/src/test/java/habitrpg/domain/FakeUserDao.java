/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Dao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peje
 */
public class FakeUserDao implements Dao<User, String> {
    private List<User> users;

    public FakeUserDao() {
        this.users = new ArrayList<>();
        users.add(new User("tester", "elon musk", "going to mars"));
    }

    @Override
    public User getOne(String key) {
        User user = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(key)) {
                user = users.get(i);
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User create(User object) {
        users.add(object);
        return object;
    }

    @Override
    public boolean delete(String key) {
        int size = users.size();
        for (int i = 0; i < size; i++) {
            if (users.get(i).getUsername().equals(key)) {
                users.remove(i);
                return true;
            }
        }
        if (users.size() == size) {
            return false;
        }
        return false;
    }

    @Override
    public boolean setDone(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
