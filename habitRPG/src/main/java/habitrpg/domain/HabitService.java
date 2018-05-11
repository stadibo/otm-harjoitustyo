package habitrpg.domain;

import habitrpg.dao.HabitDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class for getting and manipulating "Habit" objects by interfacing with its
 * corresponding DAO (Data access object), HabitDao.
 */
public class HabitService {

    private HabitDao habitDao;
    private List<Habit> habits;

    /**
     * Constructs a HabitService object which higher level classes can 
     * interface with to modify Habit objects
     * @param habitDao (DAO for Habit objects)
     */
    public HabitService(HabitDao habitDao) {
        this.habitDao = habitDao;
        this.habits = new ArrayList<>();
    }

    /**
     * Passes on a User object to the DAO (Data access object) HabitDao.
     *
     * @param user (logged in user from UserService)
     */
    public void updateUser(User user) {
        habitDao.setUser(user);
    }

    /**
     * Gets a list of "Habit" objects from HabitDao and filters them by if they
     * are to be tracked.
     *
     * @return A list of "Habit" objects
     */
    public List<Habit> getHabitsUpdate() {
        this.habits = habitDao.getAll()
                .stream()
                .filter(t -> !t.isRetired())
                .collect(Collectors.toList());
        return this.habits;
    }

    /**
     * Creates a "Habit" object and passes it on to HabitDao to be stored in the
     * database.
     *
     * @param content (name of habit)
     * @param diff (difficulty of habit)
     * @return if creation was successful
     */
    public boolean createHabit(String content, int diff) {
        Habit newHabit = habitDao.create(new Habit(content, diff));
        if (newHabit == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Interfaces with HabitDao to set Habits to no longer be tracked.
     *
     * @param key (database id)
     * @return if setting was successful
     */
    public boolean untrack(Integer key) {
        boolean success = false;
        try {
            success = habitDao.setDone(key);
        } catch (Exception e) {
        }
        return success;
    }

    /**
     * Interfaces with HabitDao to add to or decrease a habits streak.
     *
     * @param key (database id)
     * @param change (positive integer to increase, and negative to decrease, streak)
     * @return if addition was successful
     */
    public boolean addToOrRemoveFromStreak(Integer key, int change) {
        boolean success = false;
        Habit toDelete;
        try {
            toDelete = habitDao.getOne(key);
            success = habitDao.addToOrRemoveFromStreak(toDelete.getId(), toDelete, change);
        } catch (Exception e) {
        }
        return success;
    }

    /**
     * Interfaces with HabitDao to remove a habit from the database.
     *
     * @param key (database id)
     * @return if deletion was successful
     */
    public boolean deleteHabit(Integer key) {
        boolean success = false;
        try {
            success = habitDao.delete(key);
        } catch (Exception e) {
        }
        return success;
    }

}
