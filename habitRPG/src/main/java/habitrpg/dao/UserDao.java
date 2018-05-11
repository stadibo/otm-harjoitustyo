package habitrpg.dao;

import habitrpg.domain.User;

/**
 * Data access object interface for User objects
 */
public interface UserDao {
    public User getOne(String key);
    public User create(User object);
    public boolean updateUser(User object);
}
