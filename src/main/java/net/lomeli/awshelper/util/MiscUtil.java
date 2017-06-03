package net.lomeli.awshelper.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MiscUtil {
    public static final Random rand = new Random(System.currentTimeMillis());
    public static final Map<String, Integer> strNums = new HashMap<>();

    // This is gonna be a PITA when it comes time to localize.
    static {
        strNums.put("zero", 0);
        strNums.put("oh", 0);
        strNums.put("one", 1);
        strNums.put("two", 2);
        strNums.put("three", 3);
        strNums.put("four", 4);
        strNums.put("five", 5);
        strNums.put("six", 6);
        strNums.put("seven", 7);
        strNums.put("eight", 8);
        strNums.put("nine", 9);
    }

    public static String randomizeString(String...choices) {
        return choices != null && choices.length > 0 ? choices[rand.nextInt(choices.length)] : null;
    }

    public static boolean isStringNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
