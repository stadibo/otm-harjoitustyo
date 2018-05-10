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

    /**
     * Converts date as string to DateTime object.
     *
     * @param input (date in format yyyyMMdd)
     * @return
     */
    public DateTime stringToDateTime(String input) {
        return fmt.parseDateTime(input);
    }

    /**
     * Takes two dates as strings and calculates the length of the interval
     * between them as days.
     *
     * @param input1 (start date)
     * @param input2 (end date)
     * @return amount of days in the interval
     */
    public int getDurationDays(String input1, String input2) {
        DateTime start = stringToDateTime(input1);
        DateTime end = stringToDateTime(input2);
        Duration dur = new Duration(start, end);

        return (int) dur.getStandardDays();
    }

    /**
     * Gets the corresponding number associated with the weekday of the input
     * date. Week starts on Monday. Days are numbered from 1-7 (Mon - Sun).
     *
     * @param input (date as string)
     * @return number for day of input date
     */
    public int getDayOfWeek(String input) {
        DateTime dt = stringToDateTime(input);
        return dt.getDayOfWeek();
    }

    /**
     * Gets the corresponding number associated with the current weekday. Week
     * starts on Monday. Days are numbered from 1-7 (Mon - Sun).
     *
     * @return number for current day
     */
    public int getTodayWeekDay() {
        DateTime dt = new DateTime();
        return dt.getDayOfWeek();
    }

    /**
     * Gets todays date in the format yyyyMMdd
     *
     * @return current date as string
     */
    public String getDateNow() {
        DateTime dt = new DateTime();
        return dt.toString(this.fmt);
    }

}
