package habitrpg.dao;

import habitrpg.domain.Daily;
import habitrpg.domain.User;
import java.util.List;

/**
 * Data access object interface for Daily objects
 */
public interface DailyDao {
    public void setUser(User user);
    public Daily getOne(Integer key);
    public List<Daily> getAll();
    public Daily create(Daily object);
    public boolean delete(Integer key);
    public boolean setDone(Integer key);
    public boolean setUndone(Integer key);
    public boolean setUntracked(Integer key);
    public boolean updateDate(int key, String date);
}
