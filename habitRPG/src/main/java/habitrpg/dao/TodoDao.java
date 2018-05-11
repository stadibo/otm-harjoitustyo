package habitrpg.dao;

import habitrpg.domain.Todo;
import habitrpg.domain.User;
import java.util.List;

/**
 * Data access object interface for Todo objects
 */
public interface TodoDao {
    public void setUser(User user);
    public Todo getOne(Integer key);
    public List<Todo> getAll();
    public Todo create(Todo object);
    public boolean delete(Integer key);
    public boolean setDone(Integer key);
}
