/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author peje
 */
public class Time {
    
    private DateTimeFormatter fmt;

    public Time() {
        this.fmt = DateTimeFormat.forPattern("yyyyMMdd");
    }
    
    private DateTime stringToDateTime(String input) {
        return fmt.parseDateTime(input);
    }
    
//    private String DateTimeToString(DateTime input) {
//        return input.toString("yyyyMMdd");
//    }
    
    public int getDurationDays(String input1, String input2) {
        DateTime start = stringToDateTime(input1);
        DateTime end = stringToDateTime(input2);
        Duration dur = new Duration(start, end);
        
        return (int) dur.getStandardDays();
    }
    
    public int getDayOfWeek(String input) {
        DateTime dt = stringToDateTime(input);
        return dt.getDayOfWeek();
    }
    
    public int getTodayWeekDay() {
        DateTime dt = new DateTime();
        return dt.getDayOfWeek();
    }
    
    public String getDateNow() {
        DateTime dt = new DateTime();
        return dt.toString("yyyyMMdd");
    }
    
}
