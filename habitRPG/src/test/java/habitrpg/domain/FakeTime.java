/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

/**
 *
 * @author peje
 */
public class FakeTime extends Time {

    private String time;
    private int dayOfWeek;
    
    public FakeTime() {
        time = "20180101";
        dayOfWeek = 1;
    }
    
    public void setFakeTime(String newTime) {
        this.time = newTime;
    }
    
    public void setFakeDayOfWeek(int day) {
        this.dayOfWeek = day;
    }
    
    @Override
    public int getDayOfWeek(String input) {
        return dayOfWeek;
    }
    
    @Override
    public int getTodayWeekDay() {
        return dayOfWeek;
    }
    
    public String getDateNow() {
        return time;
    }
    
}
