package com.app.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
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
    
    public static String sha256(String value) throws Exception {
        MessageDigest sha256 = MessageDigest.getInstance("sha-256");
        byte[] digest = sha256.digest(value.getBytes("UTF-8"));
        digest = Base64.getUrlEncoder().withoutPadding().encode(digest);
        return new String(digest);
    }
}
