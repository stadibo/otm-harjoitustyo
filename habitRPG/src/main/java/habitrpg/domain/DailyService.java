/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.DailyDao;
import habitrpg.dao.Database;
import habitrpg.dao.DaysShownDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author peje
 */
public class DailyService {

    private DailyDao dailyDao;
    private DaysShownDao dsDao;

    private List<Daily> dailies;
    private Time time;

    public DailyService(Database database, Time uiTime) {
        this.dailyDao = new DailyDao(database);
        this.dsDao = new DaysShownDao(database);
        this.dailies = new ArrayList<>();
        this.time = uiTime;
    }

    public void updateUser(User user) {
        dailyDao.setUser(user);
    }

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

    public boolean untrack(int id) {
        boolean success = false;
        try {
            success = dailyDao.setUntracked(id);
        } catch (Exception e) {
        }
        return success;
    }

    public boolean deleteDaily(int id) {
        boolean success = false;
        try {
            success = dailyDao.delete(id);
            success = dsDao.delete(id);
        } catch (Exception e) {
        }
        return success;
    }

    public boolean setDone(int id) {
        boolean success = false;
        try {
            success = dailyDao.setDone(id);
        } catch (Exception e) {
        }
        return success;
    }

    public boolean createDaily(String content, int diff, boolean[] days) {

        Daily newDaily = dailyDao.create(new Daily(content, diff, time.getDateNow()));
        if (newDaily == null) {
            return false;
        } else {
            this.setDays(days, newDaily.getId());
            return true;
        }
    }

    private void setUndone(String dateToday) {

        for (Daily d : this.dailies) {
            if (!d.getDate().equals(dateToday)) {
                dailyDao.setUndone(d.getId());
                d.setComplete(false);
                // remove exp and health if "not complete" before update date
                dailyDao.updateDate(d.getId(), dateToday);
                d.setDate(dateToday);
            }
        }
    }

    private boolean setDays(boolean[] days, Integer id) {
        return dsDao.create(days, id);
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
