package habitrpg.domain;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * This class uses the JodaTime library to get the system time, day of week and parse
 * dates as strings.
 */
public class Time {

    private DateTimeFormatter fmt;
    
    /**
     * Constructs Time object with dateTimeFormatter for the pattern yyyyMMdd
     */
    public Time() {
        this.fmt = DateTimeFormat.forPattern("yyyyMMdd");
    }

    /**
     * Converts date as string to DateTime object.
     *
     * @param input (date in format yyyyMMdd)
     * @return a JodaTime DateTime object with the date that was input
     */
    public DateTime stringToDateTime(String input) {
        return fmt.parseDateTime(input);
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
