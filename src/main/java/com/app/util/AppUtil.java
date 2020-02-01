package com.app.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Application Utility class.
 *
 * @author charan2628
 *
 */
public class AppUtil {

    /**
     * Private constructor.
     */
    private AppUtil() { }

    /**
     * Helper method to hours and minutes to Date
     * with current day set today and zone id to server default.
     *
     * <p> Note: this method excepts hours and minutes according to the
     * server time zone, front end should should do the mapping.
     *
     * @param hours hours
     * @param minutes minutes
     * @return Date to schedule
     */
    public static Date format(final int hours, final int minutes) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.withHour(hours);
        localDateTime = localDateTime.withMinute(minutes);
        return Date.from(
                localDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * Removes duplicates in a list of strings.
     *
     * @param list
     *        list of strings
     * @return list with duplicates removed
     */
    public static List<String> removeDups(final List<String> list) {
        return new ArrayList<String>(new HashSet<String>(list));
    }
}
