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
 * @author peje
 */
public class HabitService {

    private HabitDao habitDao;
    private Database database;
    private List<Habit> habits;

    public HabitService(Database database, User user) {
        this.habitDao = new HabitDao(database, user);
        this.database = database;
        this.habits = new ArrayList<>();
    }
    
    public HabitService(Database database) {
        this.habitDao = new HabitDao(database);
        this.database = database;
        this.habits = new ArrayList<>();
    }

    public void updateUser(User user) {
        habitDao.setUser(user);
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public List<Habit> getHabitsUpdate() {
        this.habits = habitDao.getAll()
                .stream()
                .filter(t -> !t.isRetired())
                .collect(Collectors.toList());
        return this.habits;
    }

    public boolean createHabit(String content, int diff) {
        Habit newTodo = habitDao.create(new Habit(content, diff));
        if (newTodo == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean setDone(Integer key) {
        boolean success = false;
        try {
            success = habitDao.setDone(habits.get(key - 1).getId());
        } catch (Exception e) {
        }
        return success;
    }
    
    public boolean untrack(Integer key) {
        boolean success = false;
        try {
            success = habitDao.setDone(key);
        } catch (Exception e) {
        }
        return success;
    }

    public boolean addToStreak(Integer key) {
        boolean success = false;
        Habit toDelete;
        try {
            toDelete = this.habits.get(key - 1);
            success = habitDao.addToStreak(toDelete.getId(), toDelete);
        } catch (Exception e) {
        }
        return success;
    }
    
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

    public boolean deleteHabit(Integer key) {
        boolean success = false;
        try {
            success = habitDao.delete(habits.get(key - 1).getId());
        } catch (Exception e) {
        }
        return success;
    }
    
    public boolean deleteHabitGui(Integer key) {
        boolean success = false;
        try {
            success = habitDao.delete(key);
        } catch (Exception e) {
        }
        return success;
    }
    
}
