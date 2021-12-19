package fr.lernejo.navy_battle.server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {
    public static boolean useRegex(final String input) {
        // Compile regular expression
        final Pattern pattern = Pattern.compile("^http://localhost:[0-9]+$", Pattern.CASE_INSENSITIVE);
        // Match regex against input
        final Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher.matches();
    }
}
