package com.app.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.app.model.Challenge;
import com.app.model.rss.Item;

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

    public static List<Challenge> itemsToChallenges(List<Item> items) {
        return items
                .stream()
                .map(item -> {
                    return new Challenge(item.getTitle(), item.getDescription(), item.getLink());
                })
                .collect(Collectors.toList());
    }

    public static List<Challenge> toChallenges(List<Item> items, List<String> tags) {
        if(tags.size() == 0) {
            return Collections.emptyList();
        }
        final Pattern pattern = createPattern(tags);
        items = items.stream()
                .filter((Item item) -> {
                    String description = item.getDescription();
                    if (pattern.matcher(description).find()) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        return itemsToChallenges(items);
    }

    /**
     * Helper method to create a regex pattern including
     * all the tags.
     *
     * <p>Example:
     * For tags (Node.Js, Java) it will create following regex pattern
     * (?:Node\.Js|Java)
     *
     * @param tags
     * @return compiled pattern from tags
     */
    private static Pattern createPattern(List<String> tags) {
        StringBuilder regex = new StringBuilder();
        regex.append("(?:");
        if (tags.size() > 0) {
            regex.append(tags.get(0));
        }
        for (int i = 1; i < tags.size(); i++) {
            regex.append(String.format("|%s", tags.get(i)));
        }
        regex.append(")");
        return Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
    }
}
