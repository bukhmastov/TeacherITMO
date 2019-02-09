package com.bukhmastov.teacheritmo.util;

public class StringUtils {

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static String emptyIfNull(String string) {
        return defaultIfNull(string, "");
    }

    public static String defaultIfNull(String string, String def) {
        return string == null ? def : string;
    }

    public static String defaultIfEmpty(String string, String def) {
        return isEmpty(string) ? def : string;
    }

    public static String defaultIfBlank(String string, String def) {
        return isBlank(string) ? def : string;
    }
}
