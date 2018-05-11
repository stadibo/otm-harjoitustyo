package habitrpg.dao;

import habitrpg.domain.Habit;
import habitrpg.domain.User;
import java.util.List;

/**
 * Data access object interface for Habit objects
 */
public interface HabitDao {
    public void setUser(User user);
    public Habit getOne(Integer key);
    public List<Habit> getAll();
    public Habit create(Habit object);
    public boolean delete(Integer key);
    public boolean setUntracked(Integer key);
    public boolean addToOrRemoveFromStreak(Integer key, Habit object, int change);
}
