/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class TimeTest {
    
    DateTime date1;
    DateTime date2;
    DateTime date3;
    
    public TimeTest() {
        this.date1 = new DateTime(2018, 1, 1, 0, 0, 0, 0);
        this.date2 = new DateTime(2018, 1, 2, 0, 0, 0, 0);
        this.date3 = new DateTime(2018, 1, 3, 0, 0, 0, 0);
    }
    
    @Test
    public void timeConstructorCreatesCorrectParser() {
        DateTimeFormatter form = DateTimeFormat.forPattern("yyyyMMdd");
        assertEquals("20180101", form.print(this.date1));
        
        Time time = new Time();
        assertEquals(this.date1, time.stringToDateTime("20180101"));
    }
    
    @Test
    public void givesRightDuration() {
        Time time = new Time();
        String sDate1 = "20180101";
        String sDate2 = "20180103";
        assertEquals(2, time.getDurationDays(sDate1, sDate2));
    }
    
    @Test
    public void givesNegativeDurationWhenFirstDateIsLaterThanSecond() {
        Time time = new Time();
        String sDate1 = "20180101";
        String sDate2 = "20180102";
        assertEquals(-1, time.getDurationDays(sDate2, sDate1));
    }
    
    @Test
    public void givesRightDayOfWeekMonday() {
        Time time = new Time();
        String s1 = "20180101";
        String s2 = "20180205";
        assertEquals(1, time.getDayOfWeek(s1));
        assertEquals(1, time.getDayOfWeek(s2));
    }
    
    @Test
    public void givesRightDayOfWeekFriday() {
        Time time = new Time();
        String s1 = "20180105";
        String s2 = "20180309";
        assertEquals(5, time.getDayOfWeek(s1));
        assertEquals(5, time.getDayOfWeek(s2));
    }
    
    @Test
    public void getTodaysDateCorrectly() {
        Time time = new Time();
        DateTime dt = new DateTime();
        DateTimeFormatter form = DateTimeFormat.forPattern("yyyyMMdd");
        assertEquals(form.print(dt), time.getDateNow());
    }
    
    @Test
    public void getTodaysWeekdayCorrectly() {
        Time time = new Time();
        DateTime dt = new DateTime();
        assertEquals(dt.getDayOfWeek(), time.getTodayWeekDay());
    }
    
}
