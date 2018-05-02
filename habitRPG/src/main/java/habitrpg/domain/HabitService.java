/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.HabitDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * A class for getting and manipulating "Habit" objects by interfacing with 
 * its corresponding DAO (Data access object), HabitDao.
 * 
 */
public class HabitService {

    private HabitDao habitDao;
    private List<Habit> habits;
    
    public HabitService(Database database) {
        this.habitDao = new HabitDao(database);
        this.habits = new ArrayList<>();
    }

    /**
     * Passes on a User object to the DAO (Data access object) HabitDao.
     * 
     * @param user  (logged in user from UserService)
     */
    public void updateUser(User user) {
        habitDao.setUser(user);
    }

    /**
     * Gets a list of "Habit" objects from HabitDao and filters
     * them by if they are to be tracked.
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
     * Creates a "Habit" object and passes it on to HabitDao to be
     * stored in the database.
     * 
     * @param content   (name of habit)
     * @param diff  (difficulty of habit)
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
     * @param key   (database id)
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
     * Interfaces with HabitDao to add to a habits streak.
     * 
     * @param key   (database id)
     * @return if addition was successful
     */
    public boolean addToStreakGui(Integer key) {
        boolean success = false;
        Habit toDelete;
        try {
            toDelete = habitDao.getOne(key);
            success = habitDao.addToStreak(toDelete.getId(), toDelete);
        } catch (Exception e) {
        }
        return success;
    }
    
    /**
     * Interfaces with HabitDao to remove a habit from the database.
     * 
     * @param key   (database id)
     * @return if deletion was successful
     */
    public boolean deleteHabitGui(Integer key) {
        boolean success = false;
        try {
            success = habitDao.delete(key);
        } catch (Exception e) {
        }
        return success;
    }
    
}
