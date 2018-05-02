/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.DailyDao;
import habitrpg.dao.Database;
import habitrpg.dao.DaysShownDao;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class DailyServiceTest {

    private DailyService dailyService;
    private DailyDao dailyDao;
    private DaysShownDao dsDao;
    private FakeTime time;
    boolean[] days;

    public DailyServiceTest() {
        days = new boolean[8];
        days[1] = true;
        days[3] = true;
        days[5] = true;
        days[7] = true;
    }

    @Before
    public void setUp() {
        Database db = new Database();
        db.createDatabase("test.db");

        time = new FakeTime();
        dailyService = new DailyService(db, time);
        dailyDao = new DailyDao(db);
        dsDao = new DaysShownDao(db);

        User user = new User("tester", "elon musk", "going to mars");
        dailyService.updateUser(user);
        dailyDao.setUser(user);
    }

    @After
    public void tearDown() {
        File file = new File("test.db");
        file.delete();
    }

    @Test
    public void canGetAllUndoneDailies() {
        Daily daily1 = new Daily("Jump", 2, "20180101");
        daily1.setDaysShown(days);
        daily1 = dailyDao.create(daily1);
        dsDao.create(days, daily1.getId());

        Daily daily2 = new Daily("Run", 1, "20180101");
        daily2.setDaysShown(days);
        daily2 = dailyDao.create(daily2);
        dsDao.create(days, daily2.getId());

        List<Daily> dailies = dailyService.getDailiesUpdate();
        assertEquals("Jump", dailies.get(0).getContent());
        assertEquals("Run", dailies.get(1).getContent());

        assertEquals(false, dailies.get(0).getDaysShown()[4]);
        assertEquals(true, dailies.get(1).getDaysShown()[3]);
    }

    @Test
    public void doesNotGetDoneDailiesForSameDay() {
        Daily daily1 = new Daily("Jump", 2, "20180101");
        daily1.setDaysShown(days);
        daily1 = dailyDao.create(daily1);
        dsDao.create(days, daily1.getId());

        List<Daily> dailies = dailyService.getDailiesUpdate();
        assertEquals("Jump", dailies.get(0).getContent());

        dailyDao.setDone(daily1.getId());
        dailies = dailyService.getDailiesUpdate();
        assertEquals(true, dailies.isEmpty());
    }

    @Test
    public void doesGetDailiesForPreviousDayAndSetsThemUndone() {
        time.setFakeTime("20171231");
        Daily daily1 = new Daily("Jump", 2, "20171231");
        daily1.setDaysShown(days);
        daily1 = dailyDao.create(daily1);
        dsDao.create(days, daily1.getId());

        List<Daily> dailies = dailyService.getDailiesUpdate();
        assertEquals("Jump", dailies.get(0).getContent());
        assertEquals(false, dailies.get(0).isComplete());

        dailyDao.setDone(daily1.getId());
        dailies = dailyService.getDailiesUpdate();
        assertEquals(true, dailies.isEmpty());

        time.setFakeTime("20180101");
        dailies = dailyService.getDailiesUpdate();

        assertEquals("Jump", dailies.get(0).getContent());
        assertEquals(false, dailies.get(0).isComplete());
    }

    @Test
    public void updatesDateIfNotSameDateAsTodayAndSetsUndoneIfPreviouslyDone() {
        time.setFakeTime("20171231");
        
        Daily daily1 = new Daily("Jump", 2, "20171231");
        daily1.setDaysShown(days);
        daily1 = dailyDao.create(daily1);
        dsDao.create(days, daily1.getId());
        dailyDao.setDone(1);

        List<Daily> dailies = dailyService.getDailiesUpdate();
        assertEquals(true, dailies.isEmpty());
        
        time.setFakeTime("20180101");
        time.setFakeDayOfWeek(7);
        dailies = dailyService.getDailiesUpdate();

        assertEquals("20180101", dailies.get(0).getDate());
        assertEquals(false, dailies.get(0).isComplete());
    }
    
    @Test
    public void canDeleteDaily() {
        Daily daily1 = new Daily("Jump", 2, "20180101");
        daily1.setDaysShown(days);
        daily1 = dailyDao.create(daily1);
        dsDao.create(days, daily1.getId());

        Daily daily2 = new Daily("Run", 1, "20180101");
        daily2.setDaysShown(days);
        daily2 = dailyDao.create(daily2);
        dsDao.create(days, daily2.getId());
        
        List<Daily> dailies = dailyService.getDailiesUpdate();
        assertEquals("Jump", dailies.get(0).getContent());
        
        dailyService.deleteDaily(1);
        dailies = dailyService.getDailiesUpdate();
        assertEquals("Run", dailies.get(0).getContent());
    }
    
    @Test
    public void untrackedDailiesAreNotShown() {
        Daily daily1 = new Daily("Jump", 2, "20180101");
        daily1.setDaysShown(days);
        daily1 = dailyDao.create(daily1);
        dsDao.create(days, daily1.getId());
        
        List<Daily> dailies = dailyService.getDailiesUpdate();
        assertEquals("Jump", dailies.get(0).getContent());
        assertFalse(dailyDao.getOne(1).isRetired());
        
        dailyService.untrack(1);
        assertTrue(dailyDao.getOne(1).isRetired());
        dailies = dailyService.getDailiesUpdate();
        assertEquals(true, dailies.isEmpty());
    }
    
    @Test
    public void canCreateNewDaily() {
        //time.setFakeTime("20180101");
        assertTrue(dailyService.createDaily("Jump", 2, days));
        
        assertEquals("Jump", dailyDao.getOne(1).getContent());
        assertEquals(2, dailyDao.getOne(1).getDifficulty());
        assertEquals(days[1], dsDao.getDays(1)[1]);
        assertEquals(false, dailyDao.getOne(1).isComplete());
        assertEquals("20180101", dailyDao.getOne(1).getDate());
    }
    
    @Test
    public void canSetADailyDoneForTheDay() {
        Daily daily1 = new Daily("Heav", 3, "20180101");
        daily1.setDaysShown(days);
        daily1 = dailyDao.create(daily1);
        dsDao.create(days, daily1.getId());
        
        assertEquals(false, daily1.isComplete());
        assertFalse(dailyService.getDailiesUpdate().isEmpty());
        dailyService.setDone(1);
        
        assertEquals(true, dailyDao.getOne(1).isComplete());
        assertTrue(dailyService.getDailiesUpdate().isEmpty());
    }
    
    @Test
    public void createDailyFail() {
        File file = new File("test.db");
        file.delete();
        assertFalse(dailyService.createDaily("Jump", 2, days));   
    }

}
