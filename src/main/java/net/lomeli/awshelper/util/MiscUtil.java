package net.lomeli.awshelper.util;

import java.util.Random;

public class MiscUtil {
    public static final Random rand = new Random(System.currentTimeMillis());

    public static String randomizeString(String...choices) {
        return choices != null && choices.length > 0 ? choices[rand.nextInt(choices.length)] : null;
    }
}
