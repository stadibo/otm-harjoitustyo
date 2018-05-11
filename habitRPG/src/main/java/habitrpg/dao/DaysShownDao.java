package habitrpg.dao;

/**
 * Data access object interface for DaysShown list
 */
public interface DaysShownDao {
    public boolean create(boolean[] days, Integer key);
    public boolean[] getDays(Integer key);
    public boolean delete(int key);
}
