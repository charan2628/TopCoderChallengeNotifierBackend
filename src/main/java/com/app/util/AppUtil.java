package com.app.util;

import java.util.ArrayList;
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
