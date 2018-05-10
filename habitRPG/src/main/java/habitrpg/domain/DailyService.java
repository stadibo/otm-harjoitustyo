package habitrpg.domain;

import habitrpg.dao.DailyDao;
import habitrpg.dao.DaysShownDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * A class for getting and manipulating "Daily" objects by interfacing with its
 * corresponding DAO:s (Data access object), DailyDao and DaysShownDao.
 *
 */
public class DailyService {

    private DailyDao dailyDao;
    private DaysShownDao dsDao;
    private UserService userService;

    private List<Daily> dailies;
    private Time time;

    public DailyService(DailyDao dailyDao, DaysShownDao dsDao, Time uiTime, UserService us) {
        this.dailyDao = dailyDao;
        this.dsDao = dsDao;
        this.dailies = new ArrayList<>();
        this.time = uiTime;
        this.userService = us;
    }

    /**
     * Passes on a User object to the DAO (Data access object) DailyDao.
     *
     * @param user (logged in user from UserService)
     */
    public void updateUser(User user) {
        dailyDao.setUser(user);
    }

    /**
     * Gets a list of "Daily" objects from DailyDao and filters them by
     * tracking, completion and specified days to be shown.
     *
     * @return A list of "Daily" objects
     */
    public List<Daily> getDailiesUpdate() {
        this.dailies = dailyDao.getAll()
                .stream()
                .filter(t -> !t.isRetired())
                .collect(Collectors.toList());

        this.getDays();

        String dateToday = time.getDateNow();
        int weekdayToday = time.getTodayWeekDay();

        this.setUndone(dateToday);

        return this.dailies
                .stream()
                .filter(t -> t.isComplete() == false)
                .filter(d -> d.getDaysShown()[weekdayToday] == true)
                .collect(Collectors.toList());
    }

    /**
     * Interfaces with DailyDao to set Daily tasks to no longer be tracked.
     *
     * @param key (database id)
     *
     * @return if setting was successful
     */
    public boolean untrack(int key) {
        boolean success = false;
        try {
            success = dailyDao.setUntracked(key);
        } catch (Exception e) {
        }
        return success;
    }

    /**
     * Interfaces with DailyDao to remove Daily task from database.
     *
     * @param key (database id)
     * @return if deletion was successful
     */
    public boolean deleteDaily(int key) {
        boolean success = false;
        try {
            success = dailyDao.delete(key);
            success = dsDao.delete(key);
        } catch (Exception e) {
        }
        return success;
    }

    /**
     * Interfaces with DailyDao to set Daily tasks to be done for this date.
     *
     * @param key (database id)
     *
     * @return if setting was successful
     */
    public boolean setDone(int key) {
        boolean success = false;
        try {
            success = dailyDao.setDone(key);
        } catch (Exception e) {
        }
        return success;
    }

    /**
     * Creates a new Daily object, sets the time of creation and passes it to
     * the DAO (Data access object) to be stored in the database. Then it gets
     * the returned object from DailyDao and uses the database id, added in
     * DailyDao to pass onto DaysShownDao for storing which days of the week the
     * Daily is shown.
     *
     * @param content (name of task)
     * @param diff (difficulty of the task)
     * @param days (list with days of week to be tracked. index 1:Monday, etc)
     * @return if creation was successful
     */
    public boolean createDaily(String content, int diff, boolean[] days) {
        Daily newDaily = dailyDao.create(new Daily(content, diff, time.getDateNow()));
        
        if (newDaily != null) {
            this.setDays(days, newDaily.getId());
            return true;
        } else {
            return false;
        }
    }

    private void setUndone(String dateToday) {
        for (Daily d : this.dailies) {
            if (!d.getDate().equals(dateToday)) {
                dailyDao.setUndone(d.getId());
                if (!d.isComplete()) {
                    userService.experiencePenalty();
                }
                d.setComplete(false);
                dailyDao.updateDate(d.getId(), dateToday);
                d.setDate(dateToday);
            }
        }
    }

    private boolean setDays(boolean[] days, Integer key) {
        return dsDao.create(days, key);
    }

    private boolean getDays() {
        boolean[] days = null;
        for (Daily d : this.dailies) {
            days = dsDao.getDays(d.getId());
            if (days == null) {
                return false;
            } else {
                d.setDaysShown(days);
            }
        }
        return true;
    }

}
